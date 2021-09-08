package com.ch.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Outport;

/**
* @author procedure sail
* @date 2021/7/26 15:00
*/
public interface OutportService extends IService<Outport> {


    void addOutport(Integer id, Integer number, String remark);
}
