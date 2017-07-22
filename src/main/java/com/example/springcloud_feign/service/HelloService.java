package com.example.springcloud_feign.service;

import com.example.springcloud_feign.fallback.HelloServiceFallback;
import com.example.springcloud_feign.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Leon
 * @CreateDate: 2017/7/22
 * @Description: Feign 声明式服务封装 <br/>
 * 注意：在定义各参数绑定时，@RequestParam、@RequestHeader 等可以指定参数名称的注解，它们的 value 不能少。<br/>
 * 在 Spring MVC 中，这些注解根据参数名来做默认值，但是在 Feign 中绑定参数必须通过 value 属性来指明具体的参数名，<br/>
 * 不然会抛出 IllegalStateException 异常，value 属性不能为空
 * @Version: 1.0.0
 */
@FeignClient(value = "hello-service", fallback = HelloServiceFallback.class)
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    String hello(@RequestBody User user);

}
