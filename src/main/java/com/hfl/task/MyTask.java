package com.hfl.task;

import java.util.Date;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author:黄飞龙
 * @Date: Created in 20:38 2017/5/19
 * 分析

要实现计划任务，首先通过在配置类注解@EnableScheduling来开启对计划任务的支持，然后在要执行计划任务的方法上注解@Scheduled，声明这是一个计划任务。

Spring通过@Scheduled支持多种类型的计划任务，包含cron、fixDelay、fixRate等。

在本示例中：
使用cron属性可按照指定时间执行，本例写的是每天20点07分执行；


 @Service
 public class ScheduledTaskService {
 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

 @Scheduled(fixedRate = 5000) //通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间执行
 public void reportCurrentTime(){
 System.out.println("每隔5秒执行一次 "+dateFormat.format(new Date()));
 }

 @Scheduled(cron = "0 07 20 ? * *" ) //使用cron属性可按照指定时间执行，本例指的是每天20点07分执行；
 //cron是UNIX和类UNIX(Linux)系统下的定时任务
 public void fixTimeExecution(){
 System.out.println("在指定时间 "+dateFormat.format(new Date())+" 执行");
 }
 }
 */

@Configuration
@EnableScheduling
public class MyTask {
    /**
     * 我们希望这个方法每10秒打印一次
     * cron:定时任务表达式
     * 指定：秒，分钟，小时，日期，月份，星期，年（可选）
     * *：任意
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void test1(){
        System.out.println("MyTask,test1()"+new Date());
    }

    /**
     * 我们希望这个方法每1分钟打印一次
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void test2(){
        System.out.println("MyTask.test2" + new Date());
    }

    /**
     * MyTask,test1()
     MyTask,test1()
     MyTask,test1()
     MyTask,test1()
     MyTask.test2
     MyTask,test1()
     MyTask,test1()
     test1:每10秒打印1次
     test2:每1分钟打印1次
     -----------------------------------------
     1分钟是60秒 = 没打印6次之后才能够打印1次test2
     --------------------------------------------
     spring task 在计算时间的时候，是根据当前服务器的系统时间进行计算。
     如果是每10秒执行一次的话，那么它是从系统时间的0、10、20秒进行计算的
     如果是每1分钟执行一次的话，那么它是从系统时间的1分钟，2分钟进行计算的
     如果是这样的话，那么我会碰到这样的情况，当我们刚刚启动程序的时候，我们的时间
     刚好是9：39：55秒，那么就会出现5秒之后会执行1次test1和1次test2
     */
}
