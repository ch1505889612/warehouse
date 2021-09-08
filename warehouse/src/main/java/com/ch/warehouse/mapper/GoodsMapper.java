package com.ch.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author procedure sail
* @date 2021/7/25 19:11
*/
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 所有商品库存
     */
    @Select("select sum(number) from warehouse.bus_goods")
    int countGoodsNumber();
}