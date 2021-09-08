package com.ch.warehouse.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.warehouse.entity.Permission;
import com.ch.warehouse.entity.Role;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.PermissionService;
import com.ch.warehouse.service.RoleService;
import com.ch.warehouse.service.UserService;
import com.ch.warehouse.utils.WebUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 负责认证用户身份和对用户进行授权
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy  //只有使用的时候才会被加载
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    // 用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if (user.getType()==Constast.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*:*");
        }else {
           if (permissions!=null&&permissions.size()>0){
               authorizationInfo.addStringPermissions(permissions);
           }
        }
        return authorizationInfo;
    }

    // 用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",authToken.getPrincipal());
        User user = userService.getOne(queryWrapper);
        if (user!=null){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);
            //根据用户id查询percode
            //查询所有菜单
        QueryWrapper<Permission> qw=new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", Constast.TYPE_MENU);
        //是否可用
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
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
            List<Permission>list=null;
            //根据角色ID查询权限
            if (pids.size()>0){
                qw.in("id",pids);
                list=permissionService.list(qw);
            }
            List<String> percodes=new ArrayList<>();
            for (Permission permission:list){
                percodes.add(permission.getPercode());
            }
            activerUser.setPermissions(percodes);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPwd(), ByteSource.Util.bytes(user.getSalt()), user.getLoginname());
            return  info;
        }
         return null;


    }


}