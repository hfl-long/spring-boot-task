package com.hfl.task;

import java.util.Date;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 思路：
 * 1、新建一个task class
 * 2、在class上添加注解@EnableScheduling
 * 3、让我们地class实现接口SchedulingConfigurer;
 * 4、实现SchedulingConfigurer中地方法
 *
 * @Author:黄飞龙
 * @Date: Created in 21:16 2017/5/19
 */
@RestController
@EnableScheduling
public class TaskCronChange implements SchedulingConfigurer{

    private String expression = "0/5 * * * * *";//每5秒执行一下定时任务


    @RequestMapping("/changeExpression")
    public String changeExpression(){
        expression = "0/10 * * * * *";//每10秒执行一下定时任务
        return "changeExpression";
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("configurerTasks.run" + new Date());
            }
        };
        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(expression);
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        };
        taskRegistrar.addTriggerTask(task,trigger);
    }
}
