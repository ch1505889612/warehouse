package com.ch.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/25 17:24
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bus_provider")
public class Provider {
    private Integer id;

    private String providername;

    private String zip;

    private String address;

    private String telephone;

    private String connectionperson;

    private String phone;

    private String bank;

    private String account;

    private String email;

    private String fax;

    private Integer available;
}