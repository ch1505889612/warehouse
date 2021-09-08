package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.common.TreeNode;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.service.PermissionService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author procedure sail
 * @date 2021/7/23 13:04
 */
@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes= new ArrayList<>();
        for (Permission permission:list){
            Boolean spread=permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        System.out.println(treeNodes+"-------------------");
        return new DataGridView(treeNodes);
    }

    /**
     * 查询所有数据,分页,模糊
     * @param permissionVo
     * @return
     */
    @RequestMapping("/toPermissionManager")
    public DataGridView toPermissionManager(PermissionVo permissionVo){
        IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","permission");
        queryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        this.permissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    /**
     * 排序码设置值
     */
    @GetMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","permission");
        queryWrapper.orderByDesc("ordernum");
        List<Permission> permission = this.permissionService.list(queryWrapper);
        if (permission.size()>0){
            map.put("value",permission.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);

        }
        return map;
    }

    /**
     * 添加权限
     */
    @PostMapping("/addPermission")
    public ServerResponseVO addPermission(PermissionVo permissionVo){
        try {
            this.permissionService.save((permissionVo));
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

    /**
     * 修改权限
     */
    @PostMapping("updatePermission")
    public ServerResponseVO updatePermission(PermissionVo permissionVo){
        try {
            System.out.println(permissionVo);
            permissionService.updateById(permissionVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }

    /**
     * 删除权限
     */
    @PostMapping("deletePermission")
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
