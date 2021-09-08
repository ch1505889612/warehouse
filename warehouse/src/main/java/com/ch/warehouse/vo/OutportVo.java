package com.ch.warehouse.vo;

import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.entity.Outport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author procedure sail
 * @date 2021/7/17 9:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutportVo extends Outport {

    private static final long serialVersionUID=1L;

    private Integer page=1;
    private Integer limit=10;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
