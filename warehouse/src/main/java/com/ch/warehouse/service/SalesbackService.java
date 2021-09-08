package com.ch.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Salesback;

/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
public interface SalesbackService extends IService<Salesback> {


    void addSalebackprice(Integer id, Integer number, String remark);
}
