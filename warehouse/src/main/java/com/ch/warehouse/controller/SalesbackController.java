package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Sales;
import com.ch.warehouse.entity.Salesback;
import com.ch.warehouse.service.SalesbackService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.SalesVo;
import com.ch.warehouse.vo.SalesbackVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author procedure sail
 * @date 2021/7/30 9:18
 */
@RestController
@RequestMapping("salesback")
public class SalesbackController {

    @Autowired
    private SalesbackService salesbackService;


    @RequestMapping("toSalesbackManager")
    public DataGridView toSalesManager(SalesbackVo salesbackVo){
        IPage<Salesback> page = new Page<>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<Salesback> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(salesbackVo.getStartTime()!=null,"salestime",salesbackVo.getStartTime());
        queryWrapper.le(salesbackVo.getEndTime()!=null,"salestime",salesbackVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(salesbackVo.getOperateperson()),"operateperson",salesbackVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(salesbackVo.getRemark()),"remark",salesbackVo.getRemark());
        this.salesbackService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }



    @PostMapping("addSalesback")
    public ServerResponseVO serverResponseVO(Integer id,Integer number,String remark){
        try {
            this.salesbackService.addSalebackprice(id,number,remark);
            return new ServerResponseVO(ServerResponseEnum.OPERATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.OPERATE_ERROR);
        }

    }
}
