package com.kachiingapp.kachiing.api.scheduler;

import com.kachiingapp.kachiing.api.controller.pullDataFromKingDee.PullDataFromEAS;
import com.kachiingapp.kachiing.data.dao.oracle.OracleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SendMsgNoticeServiceTimer
 * @Auther: liujunhan
 * @Date: 2024/1/17 09:16
 * @Description:
 */
@Service
@Component
@EnableScheduling
public class EasDataScheduler {
    private static final Logger logger = LoggerFactory.getLogger(EasDataScheduler.class);

    @Autowired
    private PullDataFromEAS pullDataFromEAS;

    //创建定时任务线程池
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(10);
//        return taskScheduler;
//    }
//
//    @Scheduled(cron = "0 30 14 * * ?")
//    public void sendPaymentNotice() {
//        logger.info("***-同步回款认领情况--定时任务每天15点执行一次---***");
//        pullDataFromEAS.collectTransDetails();
//    }
//
//    @Scheduled(cron = "0 00 17 * * ?")
//    public void sendLateRemind() {
//        logger.info("***--同步回款认领情况---定时任务每天17点执行一次---***");
//        pullDataFromEAS.collectTransDetails();
//    }
}
