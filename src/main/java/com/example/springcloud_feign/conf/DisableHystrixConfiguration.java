package com.example.springcloud_feign.conf;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author: Leon
 * @CreateDate: 2017/7/22
 * @Description: 关闭的 Hystrix 的配置类
 * @Version: 1.0.0
 */
@Configuration
public class DisableHystrixConfiguration {

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}
