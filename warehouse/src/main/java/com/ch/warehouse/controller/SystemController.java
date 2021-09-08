package com.ch.warehouse.controller;

import com.ch.warehouse.entity.User;
import com.ch.warehouse.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author procedure sail
 * @date 2021/7/15 15:01
 */
@Controller
@RequestMapping("sys")
public class SystemController {

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/index/login";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("index")
    public String index(){
        return "system/index/index";
    }

    /**
     * 跳转到主页面
     */
    @RequestMapping("main")
    public String main(){
        return "system/index/main";
    }


    /**
     * 跳转到登录日志页面
     */
    @GetMapping("/toLogininfo")
    public String toLogininfo(){
        return "system/loginfo/loginfoManager";
    }

    @GetMapping("/toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/toNoticeManager";
    }


    /**
     * 跳到部门管理页面
     * @return
     */
    @GetMapping("/toDeptManager")
    public String toDeptManager(){
        return "system/dept/toDeptManager";
    }

    /**
     * 跳到菜单管理页面
     * @return
     */
    @GetMapping("/toMenuManager")
    public String toMenuManager(){
        return "system/menu/toMenuManager";
    }

    /**
     * 跳到菜单管理页面
     * @return
     */
    @GetMapping("/toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/toPermissionManager";
    }
    /**
     * 跳到菜单管理页面
     * @return
     */
    @GetMapping("/toRoleManager")
    public String toRoleManager(){
        return "system/role/toRoleManager";
    }

    /**
     * 跳到用户管理页面
     * @return
     */
    @GetMapping("/toUserManager")
    public String toUserManager(){
        return "system/user/toUserManager";
    }

    @GetMapping("/toUserInfo")
    public String toUserInfo(){
        return "system/userinfo/userInfo";
    }

    @GetMapping("/changePwd")
    public String changePwd(){
        return "system/updatepwd/changePwd";
    }


    @GetMapping("loginout")
    public String loginout(){
       WebUtils.getSession().removeAttribute("user");
       return "redirect:/sys/toLogin";
}


}
