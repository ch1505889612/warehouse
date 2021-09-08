package com.ch.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.warehouse.entity.Goods;

/**
* @author procedure sail
* @date 2021/7/25 19:11
*/
public interface GoodsService extends IService<Goods> {

        /**
         *统计商品库存数量
         */
        int countGoodsNumber();
}
