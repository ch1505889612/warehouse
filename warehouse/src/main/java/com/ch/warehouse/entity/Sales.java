package com.ch.warehouse.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bus_sales")
public class Sales {
    private Integer id;

    private Integer customerid;

    private String paytype;

    private Date salestime;

    private String operateperson;

    private Integer number;

    private String remark;

    private Double saleprice;

    private Integer goodsid;
}