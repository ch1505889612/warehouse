package com.ch.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author procedure sail
* @date 2021/7/27 9:52
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("userinfo")
public class Userinfo {
    private Integer id;

    private String username;

    private String phone;

    private String trueName;

    private String address;

    private String masterSkill;

    private String email;

    private String selfAssessment;

    private Integer sex;

    private String headimg;

    private String birthday;
}