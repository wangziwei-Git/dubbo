package com.wzw.pojo;

import java.io.Serializable;

/**
 * pojo类都要实现序列化接口
 *      否则会报错
 * @Author: wzw
 * @Date: 2020/11/6 17:40
 * @version: 1.8
 */
public class User implements Serializable {
    private Integer id;
    private String username;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
