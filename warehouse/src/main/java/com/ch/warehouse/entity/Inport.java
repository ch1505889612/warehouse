package com.ch.warehouse.entity;

import java.util.*;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/26 10:56
 *
 * JsonInclude:如果某个字段为空,则不返回该字段
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bus_inport")
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class Inport {
    private Integer id;

    private String paytype;

    private Date inporttime;

    private String operateperson;

    private Integer number;

    private String remark;

    private Double inportprice;

    private Integer providerid;

    private Integer goodsid;

    @TableField(exist = false)
    private String providername;
    @TableField(exist = false)
    private String goodsname;
    @TableField(exist = false)
    private String size;

    @TableField(exist = false)
    private List<Integer> data=new ArrayList<>();
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String type;
    @TableField(exist = false)
    private String stack;
}