package com.ch.warehouse.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Dept;
import com.ch.warehouse.entity.Role;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.mapper.UserMapper;
import com.ch.warehouse.service.DeptService;
import com.ch.warehouse.service.RoleService;
import com.ch.warehouse.service.UserService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.UserVo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.dc.pr.PRError;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author procedure sail
 * @date 2021/7/24 15:24
 */
@RequestMapping("user")
@RestController
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;


    /**
     * 修改密码
     */
    @PostMapping("updatePwd")
    public ServerResponseVO updatePwd(String password,String newpassword){
        try {
            User user = (User) WebUtils.getSession().getAttribute("user");
            User users = userService.getById(user.getId());
            System.out.println(password+"---------"+newpassword+"--------");
            //获取盐值
            Md5Hash hash = new Md5Hash(password, user.getSalt(), 15);
            if (hash.toString().equals(users.getPwd())){
                //设置盐
                String salt = IdUtil.simpleUUID().toUpperCase();
                users.setPwd(new Md5Hash(newpassword,salt,15).toString());
                users.setSalt(salt);
                userService.updateById(users);
            }
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }


    /**
     * 用户全查询
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        IPage<User> page=new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()),"loginname",userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
       // queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        this.userService.page(page,queryWrapper);
        List<User> list = page.getRecords();
      for (User user:list){
          Integer deptid = user.getDeptid();
          if (deptid!=null){
              Dept one = deptService.getById(deptid);
              user.setDeptname(one.getTitle());
          }
          Integer mgr=user.getMgr();
          if(mgr!=null){
              User one = userService.getById(mgr);
              user.setLeadername(one.getName());
          }

      }
        return new DataGridView(page.getTotal(),list);
    }


    /**
     * 排序码设置值
     */
    @GetMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<User> depts = this.userService.list(queryWrapper);
        if (depts.size()>0){
            map.put("value",depts.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);

        }
        return map;
    }

    /**
     * 根据部门id查询用户
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }


//    @GetMapping("loginnameIfExist")
//    public DataGridView loginnameIfExist(String loginname){
//
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq(loginname!=null,"loginname",loginname);
//        List<User> list = this.userService.list(queryWrapper);
//        if (list!=null){
//            return new DataGridView("已存在");
//        }
//            return new DataGridView("");
//
//    }

    @PostMapping("addUser")
    public ServerResponseVO loadUserMaxOrderNum(UserVo userVo){
        try {
            userVo.setType(Constast.USER_TYPE_NORMAL);
            userVo.setHiredate(new Date());
            //设置盐
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,15).toString());
            this.userService.save(userVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }


    /**
     * 根据用户id查询一个用户
     */
    @GetMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 修改
     */
    @PostMapping("updateUser")
    public ServerResponseVO updateUser(UserVo userVo){
        try {
            userService.updateById(userVo);
            return  new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }


    /**
     * 删除
     */
    @PostMapping("deleteUser")
    public ServerResponseVO deleteUser(Integer id){
      try {
          this.userService.removeById(id);
          return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
      }catch (Exception e){
          return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
      }
    }

    /**
     * 重置用户密码
     */
    @PostMapping("resetPwd")
    public ServerResponseVO resetPwd(Integer id){
        try {
            User user = new User();
            user.setId(id);
            String salt=IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,15).toString());
            this.userService.updateById(user);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }

    /**
     * 根据用户ID查询角色并选中已拥有的角色
     */
    @RequestMapping("initRoleTable")
    public DataGridView initRoleTable(Integer id){
        //1.查询所有可用的角色
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);
        //2.查询当前用户拥有的角色ID集合
       List<Integer> currentUserRoleIds=  this.roleService.queryUserRoleUdsByUid(id);
       for (Map<String,Object> map:listMaps){
           Boolean LAY_CHECKED=false;
           Integer roleId = (Integer) map.get("id");
            for (Integer rid:currentUserRoleIds){
                if (rid==roleId){
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);

       }
       return new DataGridView(Long.valueOf(listMaps.size()),listMaps);

    }

    //保存用户和角色的关系
    @RequestMapping("saveUserRole")
    public ServerResponseVO saveUserRole(Integer uid,Integer[] ids){
        try {
            this.userService.saveUserRole(uid,ids);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

















}
