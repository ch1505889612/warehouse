package com.ch.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author procedure sail
 * @date 2021/7/15 15:01
 */
@Controller
@RequestMapping("bus")
public class BusController {


    @RequestMapping("toCustomerManager")
    public String toCustomerManager(){
        return "bus/customer/toCustomerManager";
    }

    @RequestMapping("toProviderManager")
    public String toProviderManager(){
        return "bus/provider/toProviderManager";
    }

    @RequestMapping("toGoodsManager")
    public String toGoodsManager(){
        return "bus/goods/toGoodsManager";
    }

    @RequestMapping("toInportManager")
    public String toInportManager(){
        return "bus/inport/toInportManager";
    }

    @RequestMapping("toOutportManager")
    public String toOutportManager(){
        return "bus/outport/toOutportManager";
    }

    /**
     * 销售管理
     */
    @GetMapping("/toSalesManager")
    public String toSalesManager(){
        return "bus/sales/toSalesManager";
    }

    /**
     * 销售退货
     */
    @GetMapping("/toSalesbackManager")
    public String toSalesbackManager(){
        return "bus/salesback/toSalesbackManager";
    }
}
