package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.common.TreeNode;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.Role;
import com.ch.warehouse.service.PermissionService;
import com.ch.warehouse.service.RoleService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author procedure sail
 * @date 2021/7/23 15:31
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询所有数据,分页,模糊
     * @param roleVo
     * @return
     */
    @RequestMapping("/toRoleManager")
    public DataGridView toRoleManager(RoleVo roleVo){
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        this.roleService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }



    /**
     * 添加角色
     */
    @PostMapping("/addRole")
    public ServerResponseVO addRole(RoleVo roleVo){
        try {
            roleVo.setCreatetime(new Date());
            this.roleService.save(roleVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

    /**
     * 修改角色
     */
    @PostMapping("updateRole")
    public ServerResponseVO updateRole(RoleVo roleVo){
        try {
            roleService.updateById(roleVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }

    /**
     * 删除角色
     */
       @PostMapping("deleteRole")
       public ServerResponseVO deleteRole(Integer id){
        try {
            roleService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }


    }

    /**
     * 根据角色ID加载菜单和权限树的json串
     */
    @RequestMapping("initPermissionByRole")
    public DataGridView initPermissionByRole(Integer roleId){
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
         queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions = permissionService.list(queryWrapper);
        //根据角色ID查询当前角色拥有的权限和菜单ID
        List<Integer> currentRolePermissions=this.roleService.queryRolePermissionIdsByRid(roleId);
        List<Permission> carrentPermissions=null;
        //如果有ID就去查询
        if (currentRolePermissions.size()>0){
            queryWrapper.in(currentRolePermissions.size()>0,"id",currentRolePermissions);
            carrentPermissions=permissionService.list(queryWrapper);
        }else {
            carrentPermissions=new ArrayList<>();
        }
       //构造list<treeNode>
        List<TreeNode> nodes=new ArrayList<>();
        for (Permission p1 : allPermissions){
            String checkArr="0";
            for (Permission p2 : carrentPermissions){
                 if (p1.getId()==p2.getId()){
                     checkArr="1";
                     break;
                 }
            }
            Boolean spread=p1.getOpen()==1?true:false;
            nodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }
        System.out.println(nodes+"-----------");
       return new DataGridView(nodes);


    }

    /**
     *保存角色和菜单权限之间的关系
     */
    @PostMapping("/saveRolePermission")
    public ServerResponseVO saveRolePermission(Integer rid,Integer[] ids){
        try {
            this.roleService.saveRolePermission(rid,ids);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

}
