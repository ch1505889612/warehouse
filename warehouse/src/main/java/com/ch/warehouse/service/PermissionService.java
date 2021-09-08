package com.ch.warehouse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Permission;

import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface PermissionService extends IService<Permission> {

    List<String> findByRoleId(List<Integer> roleIds);

}
