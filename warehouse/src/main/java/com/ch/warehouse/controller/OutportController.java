package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.entity.Outport;
import com.ch.warehouse.entity.Provider;
import com.ch.warehouse.service.GoodsService;
import com.ch.warehouse.service.InportService;
import com.ch.warehouse.service.OutportService;
import com.ch.warehouse.service.ProviderService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.InportVo;
import com.ch.warehouse.vo.OutportVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/26 15:03
 */
@RequestMapping("outport")
@RestController
public class OutportController {

    @Autowired
    private OutportService outportService;
    

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;


    @GetMapping("/toOutportManager")
    public DataGridView toOutportManager(OutportVo outportVo){
        IPage<Outport> page = new Page<>(outportVo.getPage(), outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(outportVo.getProviderid()!=null && outportVo.getProviderid()!=0,"providerid",outportVo.getProviderid());
        queryWrapper.eq(outportVo.getGoodsid()!=null && outportVo.getGoodsid()!=0,"goodsid",outportVo.getGoodsid());
        queryWrapper.ge(outportVo.getStartTime()!=null,"outputtime",outportVo.getStartTime());
        queryWrapper.le(outportVo.getEndTime()!=null,"outputtime",outportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getOperateperson()),"operateperson",outportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getRemark()),"remark",outportVo.getRemark());
        queryWrapper.orderByDesc("outputtime");
        this.outportService.page(page, queryWrapper);
        List<Outport> records = page.getRecords();
        for (Outport outport:records){
            Provider provider = this.providerService.getById(outport.getProviderid());
            if (null!=provider){
                outport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(outport.getGoodsid());
            if (null!=goods){
                outport.setGoodsname(goods.getGoodsname());
                outport.setSize(goods.getSize());
            }

        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加退货信息
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping("addOutport")
    private ServerResponseVO addOutport(Integer id,Integer number,String remark){
         try {
             this.outportService.addOutport(id,number,remark);
             return new ServerResponseVO(ServerResponseEnum.OPERATE_SUCCESS);
         }catch (Exception e){
             e.printStackTrace();
             return new ServerResponseVO(ServerResponseEnum.OPERATE_ERROR);
         }
    }
}
