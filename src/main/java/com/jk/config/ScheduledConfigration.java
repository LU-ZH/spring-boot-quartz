package com.jk.config;

import com.jk.task.ScheduledTasks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by dell on 2017/5/29.
 */
@Configuration
public class ScheduledConfigration {

    /**
     * 定义JobDetail
     * @param scheduledTasks
     * @return
     */
    @Bean
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduledTasks scheduledTasks){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        //设置对应的对象
        jobDetail.setTargetObject(scheduledTasks);
        //设置对应的方法名
        jobDetail.setTargetMethod("excute");
        return jobDetail;
    }

    /**
     * 定义Trigger
     * @param jobDetail
     * @return
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean jobDetail){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail.getObject());
        trigger.setCronExpression("0/5 * * ? * *");//每5秒执行一次
        return trigger;
    }

    /**
     * 定义scheduler
     * @param trigger
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean trigger){
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(trigger.getObject());
        return scheduler;
    }
}
