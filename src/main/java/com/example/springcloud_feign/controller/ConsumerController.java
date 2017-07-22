package com.example.springcloud_feign.controller;

import com.example.springcloud_feign.model.User;
import com.example.springcloud_feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Leon
 * @CreateDate: 2017/7/22
 * @Description:
 * @Version: 1.0.0
 */
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String helloConsumer2() {
        StringBuffer sb = new StringBuffer();
        sb.append(helloService.hello()).append("<br/>");
        sb.append(helloService.hello("zhangsan")).append("<br/>");
        sb.append(helloService.hello("lisi", 25)).append("<br/>");
        sb.append(helloService.hello(new User("wangwu", 26))).append("<br/>");
        return sb.toString();
    }

}
