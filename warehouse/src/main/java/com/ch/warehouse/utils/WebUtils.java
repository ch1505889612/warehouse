package com.ch.warehouse.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author procedure sail
 * @date 2021/7/15 19:10
 */
public class WebUtils {

    /**
     * 得到request
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 得到session
     */
    public static HttpSession getSession(){
        HttpSession session = getRequest().getSession();
        session.setMaxInactiveInterval(60*60*24);
        return session;
    }
}
