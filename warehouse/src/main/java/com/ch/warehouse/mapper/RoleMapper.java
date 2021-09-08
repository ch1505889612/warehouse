package com.ch.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findRoleByUserId(@Param("id") Integer id);

    /**
     * /根据角色ID删除sys_role_permission
     * @param id
     */
    void deleRolepermissionByRid(@Param("rid") Serializable id);

    /**
     * //根据角色ID删除sys_role_user
     * @param id
     */
    void deleteRoleUserByRid(@Param("rid") Serializable id);

    /**
     * 根据角色ID查询当前拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(@Param("rid") Integer roleId);

    /**
     * 保存角色和菜单权限之间的关系
     * @param rid
     * @param pid
     */
    void saveRolePermission(@Param("rid")Integer rid,@Param("pid") Integer pid);
    //根据用户ID删除用户角色中间的数据
    void deleteRoleUserByUid(@Param("uid") Serializable id);

    List<Integer> queryUserRoleUdsByUid(@Param("uid")Integer id);

    void insertUserRole(@Param("uid")Integer uid,@Param("rid") Integer rid);

}