package com.ch.warehouse.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author procedure sail
* @date 2021/7/15 14:46
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role  implements Serializable {
    private Integer id;

    private String name;

    private String remark;

    private Integer available;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
}