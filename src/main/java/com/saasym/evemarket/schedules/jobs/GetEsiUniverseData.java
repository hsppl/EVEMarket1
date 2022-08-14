package com.saasym.evemarket.schedules.jobs;

import com.saasym.evemarket.schedules.QuartzServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
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
public class GetEsiUniverseData extends QuartzJobBean {
    private final QuartzServiceImpl quartzService;

    @Autowired
    private Scheduler scheduler;

    public GetEsiUniverseData(QuartzServiceImpl quartzService) {
        this.quartzService = quartzService;
    }

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("ThreadID:[{}],Schedule [GetEsiUniverseData] start...",Thread.currentThread().getId());

            JobDetail jobDetail = JobBuilder.newJob(GetEsiUniverseDataTypes.class).withIdentity("GetEsiUniverseDataTypes", "ESIService").usingJobData("pages",50).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("GetEsiUniverseDataTypes", "ESIService").build();
            scheduler.scheduleJob(jobDetail, trigger);

        }catch (Exception e) {
            log.error("ThreadID:[{}],Schedule [GetEsiUniverseData] has A fatal error occurred!!!",Thread.currentThread().getId(), e);
        }
    }

}