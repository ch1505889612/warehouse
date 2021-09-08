package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Sales;
import com.ch.warehouse.entity.Salesback;
import com.ch.warehouse.mapper.GoodsMapper;
import com.ch.warehouse.mapper.SalesMapper;
import com.ch.warehouse.service.GoodsService;
import com.ch.warehouse.vo.SalesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.SalesbackMapper;
import com.ch.warehouse.service.SalesbackService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
* @author procedure sail
* @date 2021/7/29 20:45
*/
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService{

    @Resource
    private SalesMapper salesMapper;

    @Autowired
    private GoodsMapper goodsMapper;





    @Override
    public void addSalebackprice(Integer id, Integer number, String remark) {
        //1.根据id查询销售订单信息
        Sales sales = salesMapper.selectById(id);
        //2.根据商品id查询商品信息
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        //修改商品的库存
        goods.setNumber(goods.getNumber()+number);
        goodsMapper.updateById(goods);
        //修改销售的库存
        sales.setNumber(sales.getNumber()-number);
        salesMapper.updateById(sales);

        //添加退货订单
        Salesback salesback=new Salesback();
        salesback.setNumber(number);
        salesback.setCustomerid(sales.getId());
        salesback.setGoodsid(goods.getId());
        salesback.setRemark(remark);
        salesback.setSalesbacktime(new Date());
        salesback.setPaytype(sales.getPaytype());
        salesback.setOperateperson(sales.getOperateperson());
        salesback.setSalebackprice(sales.getSaleprice());
        this.getBaseMapper().insert(salesback);
    }
}
