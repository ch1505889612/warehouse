package com.ch.warehouse.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/21 14:02
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_dept")
public class Dept {
    private Integer id;

    private Integer pid;

    private String title;

    private Integer open;

    private String remark;

    private String address;

    /**
    * 状态【0不可用1可用】
    */
    private Integer available;

    /**
    * 排序码【为了调事显示顺序】
    */
    private Integer ordernum;

    private Date createtime;
}