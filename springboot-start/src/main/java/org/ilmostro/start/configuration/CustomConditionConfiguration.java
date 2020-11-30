package org.ilmostro.start.configuration;

import org.ilmostro.start.service.CustomCondition;
import org.ilmostro.start.service.CustomConditionService;
import org.ilmostro.start.service.CustomConditionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 * @date 2020/10/9 14:03
 */
@Configuration
public class CustomConditionConfiguration {

    @Bean
    @Conditional(CustomCondition.class)
    public CustomConditionService getCustomConditionService(){
        return new CustomConditionServiceImpl();
    }
}
