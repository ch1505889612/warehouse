package com.ch.warehouse.service.Impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.mapper.GoodsMapper;
import com.ch.warehouse.vo.InportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.InportMapper;
import com.ch.warehouse.service.InportService;

import java.io.Serializable;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/26 10:56
*/
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Inport entity) {
        //根据商品编号查询商品
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber() + entity.getNumber());
        goodsMapper.updateById(goods);
        //保存进货信息
        return super.save(entity);
    }

    @Override
    public boolean updateById(Inport entity) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //库存算法,当前库存+进货修改之前的数量-修改之后的数量
        goods.setNumber(goods.getNumber() + inport.getNumber() - entity.getNumber());
        this.goodsMapper.updateById(goods);

        //更新进货单
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        //库存算法,当前库存-进货单数量
        goods.setNumber(goods.getNumber() - inport.getNumber());
        this.goodsMapper.updateById(goods);

        //更新进货单
        return super.removeById(id);
    }



    @Override
    public List<Inport> echartsInportSeven(Integer goodsId,String startTime, String endTime) {
        return this.getBaseMapper().echartsInportSeven(goodsId,startTime,endTime);
    }
}
