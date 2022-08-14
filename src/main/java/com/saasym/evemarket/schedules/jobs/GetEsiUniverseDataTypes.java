package com.saasym.evemarket.schedules.jobs;

import com.saasym.evemarket.model.esi.UniverseTypes;
import com.saasym.evemarket.schedules.QuartzServiceImpl;
import com.saasym.evemarket.service.UniverseService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class GetEsiUniverseDataTypes extends QuartzJobBean {
    private final UniverseService universeService;
    private final QuartzServiceImpl quartzService;

    @Autowired
    private Scheduler scheduler;

    public GetEsiUniverseDataTypes(UniverseService universeService, QuartzServiceImpl quartzService) {
        this.universeService = universeService;
        this.quartzService = quartzService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
       try {
           JobDataMap dataMap = context.getJobDetail().getJobDataMap();

           for (int i = 0; i < dataMap.getInt("pages"); i++) {
               log.info("ThreadID:[{}],Schedule [GetEsiUniverseDataTypes] geting page [{}]...",Thread.currentThread().getId(),i+1);
               UniverseTypes universeTypes = universeService.getEsiUniverseTypes(i+1);

               for (Integer num : universeTypes.getId()) {
                   JobDetail jobDetail = JobBuilder.newJob(GetEsiUniverseDataTypeDetails.class).withIdentity("GetEsiUniverseDataTypeDetails_"+num, "ESIService").usingJobData("pageIndex",i + 1).usingJobData("itemID",num).build();
                   Trigger trigger = TriggerBuilder.newTrigger().withIdentity("GetEsiUniverseDataTypeDetails_"+num, "ESIService").build();
                   scheduler.scheduleJob(jobDetail, trigger);
               }
           }
       }catch (Exception e){
           log.error("ThreadID:[{}],Schedule [GetEsiUniverseDataTypes] has A fatal error occurred!!!",Thread.currentThread().getId(), e);
       }
    }
}
