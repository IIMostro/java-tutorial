package org.ilmostro.start.configuration;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Ureport2Configuration {

    /**
     * 进行注册Servlet
     * 配置 UReport2 需要使用到的servlet
     */
    @Bean
    public ServletRegistrationBean buildUReportServlet() {
        /*
         * @param  servlet
         * @param  urlMappings 值为“/ureport/*”的 urlMappings 是一定不能变的，否则系统将无法运行。
         */
        return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");

    }
}
