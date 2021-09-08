package com.ch.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Sales;

/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
public interface SalesService extends IService<Sales> {

    int countSalesNumber();
}
