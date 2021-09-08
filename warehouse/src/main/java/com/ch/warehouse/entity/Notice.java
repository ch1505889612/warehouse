package com.ch.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/19 13:45
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_notice")
public class Notice implements Serializable {
    private Integer id;

    private String title;

    private String content;

    private Date createtime;

    private String opername;
}