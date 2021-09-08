package com.ch.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.common.TreeNode;
import com.ch.warehouse.common.TreeNodeBuilder;

import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.mapper.PermissionMapper;
import com.ch.warehouse.service.PermissionService;
import com.ch.warehouse.service.RoleService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;

import com.ch.warehouse.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author procedure sail
 * @date 2021/7/17 9:38
 * 菜单管理前端控制器
 */
@RestController
@RequestMapping("/menu")
public class MenuController {



    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
       //查询所有菜单
       QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        //查询所有子菜单
        queryWrapper.eq("type", Constast.TYPE_MENU);
        //是否可用
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list =null;
        if (user.getType()==Constast.USER_TYPE_SUPER){
            list=permissionService.list(queryWrapper);
        }else {
            //根据用户ID+角色+权限去查询
            Integer userId=user.getId();
            //根据用户id查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleUdsByUid(userId);
            //根据角色ID取到权限和菜单ID
            HashSet<Integer> pids = new HashSet<>();
            for (Integer rid:currentUserRoleIds){
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            //根据角色ID查询权限
            if (pids.size()>0){
                queryWrapper.in("id",pids);
                list=permissionService.list(queryWrapper);
            }else {
                list=new ArrayList<>();
            }

        }
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission p:list) {
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            String icon=p.getIcon();
            String href=p.getHref();
            Boolean spread=p.getOpen()==Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
        }
         //构造层级关系
        List<TreeNode> builder = TreeNodeBuilder.build(treeNodes, 1);
        System.out.println(builder+"-----------------");
        return new DataGridView(builder);
    }
    
    /*************菜单管理开始*************/
    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes= new ArrayList<>();
        for (Permission Permission:list){
            Boolean spread=Permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(Permission.getId(),Permission.getPid(),Permission.getTitle(),spread));
        }
        System.out.println(treeNodes+"-------------");
        return new DataGridView(treeNodes);
    }

    /**
     * 查询所有数据,分页,模糊
     * @param PermissionVo
     * @return
     */
    @RequestMapping("/toMenuManager")
    public DataGridView toMenuManager(PermissionVo PermissionVo){
        IPage<Permission> page = new Page<>(PermissionVo.getPage(), PermissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        queryWrapper.eq(PermissionVo.getId()!=null,"pid",PermissionVo.getId());
        queryWrapper.like(StringUtils.isNotBlank(PermissionVo.getTitle()),"title",PermissionVo.getTitle());
          this.permissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    /**
     * 排序码设置值
     */
    @GetMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        queryWrapper.orderByDesc("ordernum");
        List<Permission> Permissions = this.permissionService.list(queryWrapper);
        if (Permissions.size()>0){
            map.put("value",Permissions.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);

        }
        return map;
    }

    /**
     * 添加菜单
     */
    @PostMapping("/addMenu")
    public ServerResponseVO addPermission(PermissionVo PermissionVo){
        try {
            this.permissionService.save((PermissionVo));
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

    /**
     * 修改菜单
     */
    @PostMapping("updateMenu")
    public ServerResponseVO updatePermission(PermissionVo PermissionVo){
        try {
            permissionService.updateById(PermissionVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }

    /**
     * 删除菜单
     */
    @PostMapping("deleteMenu")
    public ServerResponseVO deletePermission(Integer id){
        try {
            permissionService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }
    }
}
