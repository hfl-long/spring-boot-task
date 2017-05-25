package com.hfl.task;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:黄飞龙
 * @Date: Created in 22:29 2017/5/19
 */
@EnableScheduling
@RestController
public class DynamicTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 1、定义一个方法：startTask-启动定时任务；
     * 2、定义一个方法：stopTask 停止定时任务
     * 3、定义一个方法：hangeCron   -修改定时任务时间
     * ThreadPoolTaskScheduler:线程池任务调度类，能够开启线程池进行任务调度类，能够开启线程池进行任务调度
     * ThreadPoolTaskScheduler.schedule（）方法会创建一个定时计划ScheduledFuture，在这个方法需要添加两个参数
     * Runnable（线程接口类）和CronTrigger（定时任务触发器）
     * 在ScheduleFuture中有一个cancel可以停止定时任务
     *
     */

    @RequestMapping("/startTask")
    public String startTask(){
        //指定：秒，分钟，小时，日期，月份，星期，年（可选）
        //每5秒执行一次定时任务
        future = threadPoolTaskScheduler.schedule(new MyRunnable(),new CronTrigger("0/5 * * * * *"));
        System.out.println();
        return "startTask";
    }

    @RequestMapping("/stopTask")
    public String stopTask(){
        if (future!=null){
            future.cancel(true);
        }
        System.out.println("stop task");
        return "stopTask";
    }

    //3、定义一个方法：changeCron 修改定时任务时间
    @RequestMapping("/changeCron")
    public String changeCron(){
        //（1）先停止定时器，（2）在启动定时器
        stopTask();
        //每10秒钟执行一次定时任务
        future = threadPoolTaskScheduler.schedule(new MyRunnable(),new CronTrigger("0/10 * * * * *"));
        System.out.println("change Cron");
        return "changeCron";
    }
    private class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("MyRunnable.run" + new Date());
        }
    }
}
