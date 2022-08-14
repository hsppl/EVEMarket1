package com.saasym.evemarket.controller;

import com.saasym.evemarket.model.ResponseTemplate;
import com.saasym.evemarket.schedules.QuartzServiceImpl;
import com.saasym.evemarket.schedules.jobs.GetEsiUniverseData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/quartz/")
public class QuartzController {
    private final QuartzServiceImpl quartzService;

    @Autowired
    private Scheduler scheduler;

    public QuartzController(QuartzServiceImpl qServiceImpl) {
        this.quartzService = qServiceImpl;
    }

    /**
     * 添加任务
     * @param jobId 1:GetEsiUniverseData
     * @return {@link ResponseTemplate} ResponseTemplate实体类
     */
    @GetMapping(value = "addJobs",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate addJobs(@RequestParam int jobId) {
        try {
            if (jobId == 1) {
                JobDetail jobDetail = JobBuilder.newJob(GetEsiUniverseData.class).withIdentity("GetEsiUniverseData", "ESIService").build();
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity("GetEsiUniverseData", "ESIService").build();
                scheduler.scheduleJob(jobDetail, trigger);

                return ResponseTemplate.builder().code(0).success(true).message("ok").build();
            }
            return ResponseTemplate.builder().code(0).success(false).message("JobID不正确!").build();
        } catch (Exception e) {
            return ResponseTemplate.builder().code(-2).success(false).message(e.getLocalizedMessage()).build();
        }
    }

    @GetMapping(value = "getAllJobs",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTemplate getAllJobs() {
        try {
            return ResponseTemplate.builder().code(0).message("ok").success(true).data(quartzService.queryAllJob()).build();
        } catch (Exception e) {
            return ResponseTemplate.builder().code(-2).success(false).message(e.getLocalizedMessage()).build();
        }
    }
}