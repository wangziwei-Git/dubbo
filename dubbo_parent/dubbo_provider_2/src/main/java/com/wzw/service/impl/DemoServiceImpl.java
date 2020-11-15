package com.wzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wzw.service.DemoService;

/**
 * @Author: wzw
 * @Date: 2020/11/6 23:02
 * @version: 1.8
 */
@Service(protocol = "dubbo")
public class DemoServiceImpl implements DemoService {
    @Override
    public void demo(String name) {

    }
}
