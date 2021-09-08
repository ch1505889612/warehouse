package com.ch.warehouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Customer;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.CustomerMapper;
import com.ch.warehouse.service.CustomerService;

import java.io.Serializable;
import java.util.Collection;

/**
* @author procedure sail
* @date 2021/7/25 15:29
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService{

    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean save(Customer entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Customer entity) {
        return super.updateById(entity);
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
