package com.esms.config;

import org.springframework.context.annotation.Configuration;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class ZKConfig {

    @Bean
    public ServletRegistrationBean<DHtmlLayoutServlet> zkLoader() {
        ServletRegistrationBean<DHtmlLayoutServlet> bean =
                new ServletRegistrationBean<>(new DHtmlLayoutServlet(), "*.zul");
        bean.setLoadOnStartup(1);
        return bean;
    }
}