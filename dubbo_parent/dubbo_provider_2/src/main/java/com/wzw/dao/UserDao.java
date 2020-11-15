package com.wzw.dao;

import com.wzw.pojo.User;

/**
 * Dao层映射接口
 */
public interface UserDao {
    User findById(Integer id);
}
