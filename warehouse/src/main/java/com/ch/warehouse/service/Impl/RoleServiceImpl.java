package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.RoleMapper;
import com.ch.warehouse.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByUserId(Integer id) {
        return roleMapper.findRoleByUserId(id);
    }




    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除sys_role_permission
        this.getBaseMapper().deleRolepermissionByRid(id);
        //根据角色ID删除sys_role_user
        this.getBaseMapper().deleteRoleUserByRid(id);
        return false;
    }

    /**
     * 根据角色ID查询当前拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存角色和菜单权限之间的关系
     * @param rid
     * @param ids
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper baseMapper = this.getBaseMapper();
        //根据rid删除sys_role_permission
        roleMapper.deleRolepermissionByRid(rid);
        if (ids!=null&&ids.length>0){
            for (Integer pid:ids){
                roleMapper.saveRolePermission(rid,pid);
            }
        }

    }

    @Override
    public List<Integer> queryUserRoleUdsByUid(Integer id) {
        return roleMapper.queryUserRoleUdsByUid(id);
    }

    @Override
    public void insertUserRole(Integer uid, Integer rid) {
       roleMapper.insertUserRole(uid,rid);
    }


}
