package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.AppFileUtils;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.entity.Provider;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.GoodsService;
import com.ch.warehouse.service.InportService;
import com.ch.warehouse.service.ProviderService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.InportVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/25 15:37
 */
@RequestMapping("inport")
@RestController
public class InportController {

    @Autowired
    private InportService inportService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;


    @GetMapping("/toInportManager")
    public DataGridView toInportManager(InportVo inportVo){
        IPage<Inport> page = new Page<>(inportVo.getPage(), inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(inportVo.getProviderid()!=null && inportVo.getProviderid()!=0,"providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null && inportVo.getGoodsid()!=0,"goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null,"inporttime",inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null,"inporttime",inportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()),"operateperson",inportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()),"remark",inportVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        this.inportService.page(page, queryWrapper);
        List<Inport> records = page.getRecords();
        for (Inport inport:records){
            Provider provider = this.providerService.getById(inport.getProviderid());
            if (null!=provider){
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(inport.getGoodsid());
            if (null!=goods){
                inport.setGoodsname(goods.getGoodsname());
                inport.setSize(goods.getSize());
            }

        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/addInport")
    public ServerResponseVO addInport(InportVo inportVo){
        inportVo.setInporttime(new Date());
        User user = (User) WebUtils.getSession().getAttribute("user");
          inportVo.setOperateperson(user.getName());
        try {
            this.inportService.save(inportVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
          return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
      }

    }

    @RequestMapping(value = "updateInport",method = RequestMethod.POST)
    public ServerResponseVO updateInport(InportVo inportVo){
        try {
            inportService.updateById(inportVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }


    }

    @PostMapping("deleteInport")
    public ServerResponseVO deleteInport(Integer id) {
        try {
            this.inportService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

        
    }










}
