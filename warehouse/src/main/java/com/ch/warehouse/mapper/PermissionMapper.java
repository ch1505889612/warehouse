package com.ch.warehouse.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Permission;
import org.apache.ibatis.annotations.Param;


import java.io.Serializable;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
public interface PermissionMapper extends BaseMapper<Permission>{
    List<String> findByRoleId(List<Integer> roleIds);
    /**
     * 根据权限或菜单id删除权限表和角色的关系表里面的数据
     */
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}