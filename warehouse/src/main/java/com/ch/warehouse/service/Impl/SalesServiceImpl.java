package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Sales;
import com.ch.warehouse.mapper.GoodsMapper;
import com.ch.warehouse.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.SalesMapper;
import com.ch.warehouse.service.SalesService;
/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService{

     @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public boolean save(Sales entity) {
       //根据商品id查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //根据销售的数量修改商品库存
        goods.setNumber(goods.getNumber()-entity.getNumber());
        goodsMapper.updateById(goods);
        return super.save(entity);
    }


    @Override
    public boolean updateById(Sales entity) {
        //根据销售商品ID查询修改之前的销售数量
        Sales sales = this.getBaseMapper().selectById(entity.getId());
        //根据商品id查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //修改商品库存
        goods.setNumber(goods.getNumber()+sales.getNumber()-entity.getNumber());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }

    @Override
    public int countSalesNumber() {
        return this.getBaseMapper().countSalesNumber();
    }
}
