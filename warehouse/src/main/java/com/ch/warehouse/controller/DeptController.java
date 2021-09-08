package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.common.TreeNode;
import com.ch.warehouse.entity.Dept;
import com.ch.warehouse.service.DeptService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author procedure sail
 * @date 2021/7/21 16:52
 */
@RequestMapping("/dept")
@RestController
public class DeptController {


    @Autowired
    private DeptService deptService;

    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(){
        List<Dept> list = this.deptService.list();
        List<TreeNode> treeNodes= new ArrayList<>();
       for (Dept dept:list){
           Boolean spread=dept.getOpen()==1?true:false;
           TreeNode treeNode = new TreeNode(dept.getId(), dept.getPid(), dept.getTitle(), spread);
           System.out.println(treeNode+"------------");
           treeNodes.add(treeNode);
       }
        System.out.println(treeNodes+"-------------");
       return new DataGridView(treeNodes);
    }

    /**
     * 查询所有数据,分页,模糊
     * @param DeptVo
     * @return
     */
    @RequestMapping("/toDeptManager")
    public DataGridView toDeptManager(DeptVo DeptVo){
        IPage<Dept> page = new Page<>(DeptVo.getPage(), DeptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DeptVo.getId()!=null,"id",DeptVo.getId()).or().eq(DeptVo.getId()!=null,"pid",DeptVo.getId());
        queryWrapper.like(StringUtils.isNotBlank(DeptVo.getAddress()),"address",DeptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(DeptVo.getTitle()),"title",DeptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(DeptVo.getRemark()),"remark",DeptVo.getRemark());

        this.deptService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    /**
     * 排序码设置值
     */
    @GetMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> depts = this.deptService.list(queryWrapper);
        if (depts.size()>0){
           map.put("value",depts.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);

        }
        return map;
    }

    /**
     * 添加部门
     */
    @PostMapping("/addDept")
    public ServerResponseVO addDept(DeptVo deptVo){
        try {
             deptVo.setCreatetime(new Date());
             this.deptService.save(deptVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

    /**
     * 修改部门
     */
    @PostMapping("updateDept")
    public ServerResponseVO updateDept(DeptVo deptVo){
        try {
            deptService.updateById(deptVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }

    /**
     * 删除部门
     */
    @PostMapping("deleteDept")
    public ServerResponseVO deleteDept(Integer id){
        try {
            deptService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }
    }
}
