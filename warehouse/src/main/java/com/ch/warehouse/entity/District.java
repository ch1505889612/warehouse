package com.ch.warehouse.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/27 13:52
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {
    /**
    * 区划信息id
    */
    private Integer id;

    /**
    * 父级挂接id
    */
    private Integer pid;

    /**
    * 区划编码
    */
    private String code;

    /**
    * 区划名称
    */
    private String name;

    /**
    * 备注
    */
    private String remark;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 状态 0 正常 -2 删除 -1 停用
    */
    private Integer status;

    /**
    * 级次id 0:省/自治区/直辖市 1:市级 2:县级
    */
    private Integer level;

}