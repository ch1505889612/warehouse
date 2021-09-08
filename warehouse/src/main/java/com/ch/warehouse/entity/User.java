package com.ch.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    private Date hiredate;

    private Integer mgr;

    private Integer available;

    private Integer ordernum;

    /**
    * 用户类型[0超级管理员1，管理员，2普通用户]
    */
    private Integer type;

    /**
    * 头像地址
    */
    private String imgpath;

    private String salt;

    /**
     * 领导名称
     */
    @TableField(exist = false)
    private String leadername;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptname;
}