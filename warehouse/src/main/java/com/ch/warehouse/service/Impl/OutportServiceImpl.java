package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.entity.Outport;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.mapper.GoodsMapper;
import com.ch.warehouse.mapper.InportMapper;
import com.ch.warehouse.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.OutportMapper;
import com.ch.warehouse.service.OutportService;

import java.util.Date;

/**
* @author procedure sail
* @date 2021/7/26 15:00
*/
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService{


    @Autowired
    private InportMapper inportMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addOutport(Integer id, Integer number, String remark) {
        //1.根据进货单ID查询进货信息
        Inport inport = this.inportMapper.selectById(id);
        //2.根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3.添加退货单信息
        Outport outport = new Outport();
        outport.setGoodsid(inport.getGoodsid());
        outport.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        outport.setOperateperson(user.getName());
        outport.setOutportprice(inport.getInportprice());
        outport.setOutputtime(new Date());
        outport.setPaytype(inport.getPaytype());
        outport.setProviderid(inport.getProviderid());
        outport.setRemark(remark);
        this.getBaseMapper().insert(outport);
    }
}
