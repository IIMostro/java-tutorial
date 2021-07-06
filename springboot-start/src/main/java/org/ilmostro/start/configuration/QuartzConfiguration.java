package org.ilmostro.start.configuration;

import org.ilmostro.start.service.quartz.SimpleQuartzJobService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author li.bowei
 * @date 2020/12/11 13:36
 */
@Configuration
@Profile(value = {"all", "mysql"})
public class QuartzConfiguration {

    private static final String LIKE_TASK_IDENTITY = "LikeTaskQuartz";

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(SimpleQuartzJobService.class)
                .withIdentity(LIKE_TASK_IDENTITY)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)  //设置时间周期单位秒
//                .withIntervalInHours(2)  //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(jobDetail())
                .withIdentity(LIKE_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
