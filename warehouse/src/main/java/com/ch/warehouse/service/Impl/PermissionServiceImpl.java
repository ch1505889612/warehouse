package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.PermissionMapper;
import com.ch.warehouse.service.PermissionService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> findByRoleId(List<Integer> roleIds) {
        return permissionMapper.findByRoleId(roleIds);
    }

    @Override
    public boolean removeById(Serializable id) {
        PermissionMapper permissionMapper=this.getBaseMapper();
        //根据权限或菜单ID删除权限表和角色表里面的数据
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }
}
