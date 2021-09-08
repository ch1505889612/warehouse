package com.ch.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Sales;
import org.apache.ibatis.annotations.Select;

/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
public interface SalesMapper extends BaseMapper<Sales> {

    @Select("select sum(number) from warehouse.bus_sales")
    int countSalesNumber();
}