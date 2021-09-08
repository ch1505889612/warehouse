package com.ch.warehouse.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.service.GoodsService;
import com.ch.warehouse.service.InportService;
import com.ch.warehouse.service.SalesService;
import com.ch.warehouse.service.UserinfoService;

import com.ch.warehouse.utils.ECharts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * @author procedure sail
 * @date 2021/8/16 21:43
 */
@RequestMapping("/main")
@RestController
public class MainController {
    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private InportService inportService;

    /**
     * 统计用户总数
     */
    @GetMapping("countUser")
    public DataGridView countUser() {
        try {
            int count = userinfoService.count();
            return new DataGridView(count);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataGridView();
        }
    }

    /**
     * 商品所有信息统计
     */
    @GetMapping("countGoods")
    public DataGridView countGoods() {
        try {
            int count = goodsService.count();
            return new DataGridView(count);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataGridView();
        }
    }

    /**
     * 商品库存所有信息统计
     */
    @GetMapping("countGoodsNumber")
    public DataGridView countGoodsNumber() {
        try {
            int count = goodsService.countGoodsNumber();
            return new DataGridView(count);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataGridView();
        }
    }

    /**
     * 商品销售订单所有信息统计
     */
    @GetMapping("countSalesNumber")
    public DataGridView countSalesNumber() {
        try {
            int count = salesService.countSalesNumber();
            return new DataGridView(count);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataGridView();
        }
    }


    /**
     * 七天各商品进货数量
     */
    @GetMapping("/echartsInportSeven")
    public synchronized DataGridView echartsInportSeven() {

        //时间数据
        List<String> listDays = new ArrayList<>();
        Integer days=0;
        for (int i = 0; i <7 ; i++) {
            Date dateTime = DateUtil.offsetDay(new Date(), days);
            String formatDate = DateUtil.formatDate(dateTime);
            listDays.add(formatDate);
            days--;
        }
        //倒序
        Collections.sort(listDays);


        //存所有的商品七天的进货信息
        List<ECharts> list = new ArrayList<>();

        //存每个商品每天进货数据
        List<Integer> dataList = new ArrayList<>();


        //获取所有商品信息
        List<Goods> goodsNameList = goodsService.list();

        ECharts eCharts=null;
        for (int m=0;m<goodsNameList.size();m++) {
            Integer day=0;

            for (int i = 0; i <7 ; i++) {
                if (goodsNameList.get(m).getId() != null) {
                    Date dateTime = DateUtil.offsetDay(new Date(), day);
                    //一天开始时间
                    Date beginOfDay = DateUtil.beginOfDay(dateTime);
                    //一天结束时间
                    Date endOfDay = DateUtil.endOfDay(dateTime);
                    day--;
                    Integer count=0;
                     List<Inport> inports = inportService.echartsInportSeven(goodsNameList.get(m).getId(), String.valueOf(beginOfDay), String.valueOf(endOfDay));
                     if (inports != null) {
                           for (int j = 0; j < inports.size(); j++) {
                               //一天中该商品有多个订单就进入这个方法判断
                              if (j==0){
                                  count= inports.get(j).getNumber();
                              }else {
                                  count=count+inports.get(j).getNumber();
                              }
                           }
                    }
                         dataList.add(count);

                }
            }

            eCharts = new ECharts(goodsNameList.get(m).getGoodsname(), "line", "总量",dataList);
             //当前这个商品循环完七天之后,清空当前商品七天的进货数据
            list.add(eCharts);
        }
        List<Integer> subList;
        List<ECharts> datalists=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {

            subList = list.get(i).getData().subList(7 * i, 7 * (i + 1));
            System.out.println(subList);
            ECharts eCharts1 = new ECharts(list.get(i).getName(), "line", "总量", subList);
            datalists.add(eCharts1);
        }
        return new DataGridView(datalists) ;
    }





}
