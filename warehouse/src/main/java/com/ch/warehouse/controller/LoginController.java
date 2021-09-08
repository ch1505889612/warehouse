package com.ch.warehouse.controller;

import com.ch.warehouse.entity.User;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author procedure sail
 * @date 2021/7/15 16:09
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("login")
    public ServerResponseVO login(String username,String password,String captcha,Boolean rememberMe){

        //校验验证码
        //session中的验证码
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaController.KEY_CAPTCHA);
        if (null == captcha || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            return ServerResponseVO.error(ServerResponseEnum.CODE_ERROR);
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
               subject.login(token);
               User user = (User) subject.getPrincipal();
               WebUtils.getSession().setAttribute("user",user);
            return ServerResponseVO.success();
         }catch (Exception e){
             return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
         }
    }


}
