package com.kachiingapp.kachiing.api.scheduler;

import com.kachiingapp.kachiing.api.controller.pushData2FanWei.DataInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Scheduler
 * @Date: 2023/7/10 21:01
 * @Description:
 */

@Component
public class DataScheduler {
    private static final Logger logger = LogManager.getLogger(DataScheduler.class);

    /**
     * 自定义线程工厂名称
     */
    private static final String THREAD_NAME = "AutoSyncData";

    /**
     * 核心线程数
     */
    private static final int THREAD_CORE_SIZE = 4;

    /**
     * 执行周期
     */
    private static final int EXC_PERIOD = 10 * 60 * 1000;

    @Autowired
    private DataInfoService dataInfoService;

    @PostConstruct
    public void init() {
        logger.info("【启动数据同步，执行周期: " + EXC_PERIOD + "ms" + "】");
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_CORE_SIZE, new MyThreadFactory(THREAD_NAME));
        executor.scheduleAtFixedRate(dataInfoService, 0, EXC_PERIOD, TimeUnit.MILLISECONDS);
    }
}
