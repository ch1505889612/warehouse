package com.ch.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface RoleService extends IService<Role> {

    List<Role> findRoleByUserId(Integer id);

    List<Integer> queryRolePermissionIdsByRid(Integer roleId);


    List<Integer> queryUserRoleUdsByUid(Integer id);

    void saveRolePermission(Integer rid, Integer[] ids);

    void insertUserRole(Integer uid, Integer rid);
}
