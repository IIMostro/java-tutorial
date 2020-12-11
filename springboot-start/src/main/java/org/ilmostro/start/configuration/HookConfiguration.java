package org.ilmostro.start.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author li.bowei
 * @date 2020/12/11 11:07
 */
@Configuration
@Slf4j
public class HookConfiguration {

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<ServletContextListener> srb =
                new ServletListenerRegistrationBean<>();
        srb.setListener(new ExampleServletContextListener());
        return srb;
    }

    public static class ExampleServletContextListener implements ServletContextListener {

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            log.info("this application shutdown hook is start!");
        }
    }
}
