package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.LoginfoMapper;
import com.ch.warehouse.service.LoginfoService;

import java.util.List;

/**
* @author procedure sail
* @date 2021/7/17 19:12
*/
@Service
public class LoginfoServiceImpl implements LoginfoService{

    @Resource
    private LoginfoMapper loginfoMapper;


}
