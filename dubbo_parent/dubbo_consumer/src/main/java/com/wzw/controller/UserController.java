package com.wzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wzw.pojo.User;
import com.wzw.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者Controller
 * @Author: wzw
 * @Date: 2020/11/6 20:02
 * @version: 1.8
 */
@RestController //@RestController = @Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {
    //注入业务接口
    //@Autowired
    @Reference(loadbalance = "roundrobin")//注入(均衡负载="轮询")
    private UserService userService;

    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }
}
