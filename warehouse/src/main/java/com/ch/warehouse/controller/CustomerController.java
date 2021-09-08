package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Customer;
import com.ch.warehouse.entity.Customer;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.CustomerService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.CustomerVo;
import com.ch.warehouse.vo.CustomerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author procedure sail
 * @date 2021/7/25 15:37
 */
@RequestMapping("customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/toCustomerManager")
    public DataGridView toCustomerManager(CustomerVo customerVo){
        IPage<Customer> page = new Page<>(customerVo.getPage(), customerVo.getLimit());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getTelephone()),"telephone",customerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        this.customerService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/addCustomer")
    public ServerResponseVO addCustomer(CustomerVo customerVo){
        User user = (User) WebUtils.getSession().getAttribute("user");
      try {
          this.customerService.save(customerVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
          return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
      }

    }

    @RequestMapping(value = "updateCustomer",method = RequestMethod.POST)
    public ServerResponseVO updateCustomer(CustomerVo customerVo){
        try {
            customerService.updateById(customerVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }


    }

    @PostMapping("deleteCustomer")
    public ServerResponseVO deleteCustomer(Integer id) {

        try {
            this.customerService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

        
    }
    @PostMapping("batchdeleteCustomer")
    public ServerResponseVO batchdeleteCustomer(Integer[]  Ids){
        Collection<Serializable> customerList = new ArrayList<>();
        for (Integer ids:Ids){
            customerList.add(ids);
        }
        try {
            this.customerService.removeByIds(customerList);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }





    }



}
