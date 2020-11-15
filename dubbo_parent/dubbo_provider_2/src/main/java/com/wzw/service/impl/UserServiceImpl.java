package com.wzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wzw.pojo.User;
import com.wzw.service.UserService;
import com.wzw.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务接口UserService的实现类
 * @Author: wzw
 * @Date: 2020/11/6 18:55
 * @version: 1.8
 */
@Service
public class UserServiceImpl implements UserService {
    //注释注入Dao接口对象
    @Autowired
    private UserDao userDao;

    /**
     * 实现查询一条数据的功能
     * @param id 条件
     * @return 查询的数据
     */
    @Override
    public User findById(Integer id) {
        System.out.println("dubbo_provider_2....................");
//        System.out.println("开始睡眠。。。。。。");
//        try {
//            //因为睡了,说明没执行完
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return userDao.findById(id);
    }
}
