package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Goods;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.GoodsMapper;
import com.ch.warehouse.service.GoodsService;

import java.io.Serializable;

/**
* @author procedure sail
* @date 2021/7/25 19:11
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{

    @Override
    public boolean save(Goods entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Goods entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Goods getOne(Wrapper<Goods> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public int countGoodsNumber() {
        return this.getBaseMapper().countGoodsNumber();
    }
}
