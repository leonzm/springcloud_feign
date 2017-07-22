package com.example.springcloud_feign.service;

import com.example.springcloud_feign.conf.DisableHystrixConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Leon
 * @CreateDate: 2017/7/22
 * @Description: 只关闭 Hystrix HelloService2 服务客户端
 * @Version: 1.0.0
 */
@FeignClient(value = "hello-service", configuration = DisableHystrixConfiguration.class)
public interface HelloService2 {

    @RequestMapping("/hello-hello")
    String hello();

}
