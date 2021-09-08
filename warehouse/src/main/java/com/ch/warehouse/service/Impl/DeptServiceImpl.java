package com.ch.warehouse.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.warehouse.entity.Dept;
import org.springframework.stereotype.Service;
import com.ch.warehouse.mapper.DeptMapper;
import com.ch.warehouse.service.DeptService;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;


/**
* @author procedure sail
* @date 2021/7/21 14:02
*/
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper,Dept> implements DeptService{


    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Dept entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean save(Dept entity) {
        return super.save(entity);
    }
}
