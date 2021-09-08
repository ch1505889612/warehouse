package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.entity.Userinfo;
import com.ch.warehouse.mapper.UserMapper;
import com.ch.warehouse.service.UserService;
import com.ch.warehouse.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.UserinfoMapper;
import com.ch.warehouse.service.UserinfoService;
/**
* @author procedure sail
* @date 2021/7/27 9:52
*/
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements UserinfoService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean updateById(Userinfo entity) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (user.getId()==entity.getId()&&(user.getLoginname()!=entity.getUsername()||user.getImgpath()!=entity.getHeadimg())){
            userMapper.updateByNameImg(entity.getUsername(),entity.getId(),entity.getHeadimg());
            user.setImgpath(entity.getHeadimg());
            WebUtils.getSession().setAttribute("user",user);
        }

        return super.updateById(entity);
    }
}
