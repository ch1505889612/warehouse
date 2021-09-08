package com.ch.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.User;
import org.apache.ibatis.annotations.Param;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名和密码查询用户
     * @param username
     * @return
     */
    User findByAccount(@Param("username") String username);
    /**
     * 修改用户名或者头像
     * @param
     */
    void updateByNameImg(@Param("loginname") String loginname,@Param("id") Integer id,@Param("imgpath") String imgpath);
}