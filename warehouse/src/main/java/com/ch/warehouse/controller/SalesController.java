package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Sales;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.SalesService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.SalesVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author procedure sail
 * @date 2021/7/29 20:50
 */
@RequestMapping("sales")
@RestController
public class SalesController {


    @Autowired
    private SalesService salesService;

    @RequestMapping("toSalesManager")
    public DataGridView toSalesManager(SalesVo salesVo){
        IPage<Sales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());
        QueryWrapper<Sales> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(salesVo.getStartTime()!=null,"salestime",salesVo.getStartTime());
        queryWrapper.le(salesVo.getEndTime()!=null,"salestime",salesVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(salesVo.getOperateperson()),"operateperson",salesVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(salesVo.getRemark()),"remark",salesVo.getRemark());
         this.salesService.page(page,queryWrapper);
         return new DataGridView(page.getTotal(),page.getRecords());
    }


    @PostMapping("addSales")
    public ServerResponseVO addSales(SalesVo salesVo){
        try {
            User user = (User) WebUtils.getSession().getAttribute("user");
            salesVo.setOperateperson(user.getRemark());
            salesVo.setSalestime(new Date());
            this.salesService.save(salesVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
        }
    }

    @PostMapping("updateSales")
    public ServerResponseVO updateSales(SalesVo salesVo){
        try{
            this.salesService.updateById(salesVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }
    }


    @PostMapping("deleteSales")
    public ServerResponseVO deleteSales(Integer id){
        try {
           this.salesService.removeById(id);
           return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }
    }
}
