package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.UserMapper;
import com.ch.warehouse.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findByAccount(String username) {
        return userMapper.findByAccount(username);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        this.roleMapper.deleteRoleUserByRid(uid);
        if (null!=ids && ids.length>0){
            for (Integer rid:ids){
                roleMapper.insertUserRole(uid,rid);
            }
        }
    }




    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间的数据
        roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }
}
