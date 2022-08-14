package com.saasym.evemarket.schedules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzRunners implements CommandLineRunner {
    private final QuartzServiceImpl quartzService;

    public QuartzRunners(QuartzServiceImpl quartzService) {
        this.quartzService = quartzService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("init Quartz Jobs scheduler Queue...");
//        quartzService.addJob(DelExpireLoginTokenJob.class,"DelExpireLoginTokenJob","ImovkaSys","0 */1 * * * ?",null);
//        quartzService.addJob(ValidityCheckJob.class,"ValidityCheckJob","ImovkaSys","0 0 */1 * * ?",null);
//        quartzService.addJob(ImportUploadMemberExcelJob.class,"ImportUploadMemberExcelJob","ImovkaSys","0 */1 * * * ?",null);
        //quartzService.addJob(WeChatAccessTokenJob.class,"WeChatAccessTokenJob","ImovkaSys","0 */1 * * * ?",null);
        //quartzService.addJob(WeChatAccessTokenJob.class,"WeChatAccessTokenJob","ImovkaSys",7000,-1,null);
        //quartzService.addJob(DelExpireLoginTokenJob.class,"DelExpireLoginTokenJob","ImovkaSys",60,-1,null);
        quartzService.startScheduler();
    }
}