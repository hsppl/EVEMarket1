package com.saasym.evemarket.schedules.jobs;

import com.saasym.evemarket.config.GlobVars;
import com.saasym.evemarket.model.zkillboard.ZkillboardData;
import com.saasym.evemarket.service.KBKMService;
import com.saasym.evemarket.service.MiraiApiHttpService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class GetCorpKBKMData extends QuartzJobBean {
    private final KBKMService kbkmService;
    private final MiraiApiHttpService miraiApiHttpService;

    public GetCorpKBKMData(KBKMService kbkmService, MiraiApiHttpService miraiApiHttpService) {
        this.kbkmService = kbkmService;
        this.miraiApiHttpService = miraiApiHttpService;
    }

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) throws JobExecutionException {
        try{
            log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] start...",Thread.currentThread().getId());

            ZkillboardData corpKM = kbkmService.GetCorpLastKM();
            ZkillboardData corpKB = kbkmService.GetCorpLastKB();

            if (corpKB!=null){
                if (corpKB.getKillmail_id() > GlobVars.lastKBId){
                    GlobVars.lastKBId = corpKB.getKillmail_id();
                    String corpKBUrl = "https://zkillboard.com/kill/"+GlobVars.lastKBId+"/";

                    String[] cmd ={
                            "/qqbot/phantomjs/phantomjs",
                            "/qqbot/phantomjs/1.js",
                            corpKBUrl,
                            "/qqbot/phantomjs/images/lastKB.png"
                    };
                    Process pjs = Runtime.getRuntime().exec(cmd,null,new File("/qqbot/phantomjs/"));
                    pjs.waitFor();

                    if (miraiApiHttpService.SendCorpKBMessage()){
                        log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] KB Send Finish.",Thread.currentThread().getId());
                    }else {
                        log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] KB Send ERROR!!!",Thread.currentThread().getId());
                    }
                }
            }

            if (corpKM!=null){
                if (corpKM.getKillmail_id() > GlobVars.lastKMId){
                    GlobVars.lastKMId = corpKM.getKillmail_id();
                    String corpKBUrl = "https://zkillboard.com/kill/"+GlobVars.lastKMId+"/";

                    String[] cmd ={
                            "/qqbot/phantomjs/phantomjs",
                            "/qqbot/phantomjs/1.js",
                            corpKBUrl,
                            "/qqbot/phantomjs/images/lastKM.png"
                    };
                    Process pjs = Runtime.getRuntime().exec(cmd,null,new File("/qqbot/phantomjs/"));
                    pjs.waitFor();

                    if (miraiApiHttpService.SendCorpKMMessage()){
                        log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] KM Send Finish.",Thread.currentThread().getId());
                    }else {
                        log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] KM Send ERROR!!!",Thread.currentThread().getId());
                    }
                }
            }

            log.info("ThreadID:[{}],Schedule [GetCorpKBKMData] Finish.",Thread.currentThread().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
