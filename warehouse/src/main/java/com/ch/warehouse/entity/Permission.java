package com.ch.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author procedure sail
* @date 2021/7/15 14:46
 *
 * @TableName("sys_permission")---对应数据库的表名
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permission")
public class Permission implements Serializable {
    private Integer id;

    private Integer pid;

    /**
    * 权限类型[menu/permission]
    */
    private String type;

    private String title;

    /**
    * 权限编码[只有type= permission才有  user:view]
    */
    private String percode;

    private String icon;

    private String href;

    private String target;

    private Integer open;

    private Integer ordernum;

    /**
    * 状态【0不可用1可用】
    */
    private Integer available;
}