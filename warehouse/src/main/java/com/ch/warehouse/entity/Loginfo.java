package com.ch.warehouse.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.experimental.theories.FromDataPoints;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author procedure sail
* @date 2021/7/17 19:12
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_loginfo")
public class Loginfo  implements Serializable {
    private Integer id;
    private String loginname;
    private String loginip;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logintime;
}