package com.wzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wzw.service.HelloService;

/**
 * @Author: wzw
 * @Date: 2020/11/6 23:01
 * @version: 1.8
 */
//@Service(protocol = "rmi")
public class HelloServiceImpl implements HelloService{
    @Override
    public void hello(String name) {

    }
}
