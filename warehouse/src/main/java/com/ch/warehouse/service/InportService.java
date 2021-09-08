package com.ch.warehouse.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.vo.InportVo;

import java.util.List;

/**
* @author procedure sail
* @date 2021/7/26 10:56
*/
public interface InportService extends IService<Inport> {

    List<Inport> echartsInportSeven(Integer goodsId,String startTime, String endTime);
}
