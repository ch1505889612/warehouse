package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Provider;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.ProviderMapper;
import com.ch.warehouse.service.ProviderService;

import java.io.Serializable;
import java.util.Collection;

/**
* @author procedure sail
* @date 2021/7/25 17:24
*/
@Service
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService{


    @Override
    public Provider getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Provider entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean save(Provider entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }
}
