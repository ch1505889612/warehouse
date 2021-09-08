package com.ch.warehouse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.User;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface UserService extends IService<User> {
        User findByAccount(String username);

        void saveUserRole(Integer uid, Integer[] ids);


}
