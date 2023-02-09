package com.saasym.evemarket.schedules.jobs;

import com.saasym.evemarket.service.UniverseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class GetEsiUniverseDataTypeDetails extends QuartzJobBean {
    private final UniverseService universeService;

    public GetEsiUniverseDataTypeDetails(UniverseService universeService) {
        this.universeService = universeService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try{
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            log.debug("ThreadID:[{}],Schedule [GetEsiUniverseDataTypeDetails] geting page [{}] index [{}]...", Thread.currentThread().getId(), dataMap.getInt("pageIndex") + 1, dataMap.getInt("itemID"));
            universeService.getSimpleItemInfo(dataMap.getInt("itemID"));
        }catch (Exception e){
            log.error("ThreadID:[{}],Schedule [GetEsiUniverseDataTypeDetails] has A fatal error occurred!!!",Thread.currentThread().getId(), e);
        }
    }
}
