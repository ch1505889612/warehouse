package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.AppFileUtils;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Provider;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.GoodsService;
import com.ch.warehouse.service.ProviderService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/25 15:37
 */
@RequestMapping("goods")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProviderService providerService;


    @GetMapping("/toGoodsManager")
    public DataGridView toGoodsManager(GoodsVo goodsVo){
        IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVo.getProviderid()!=null && goodsVo.getProviderid()!=0,"providerid",goodsVo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()),"goodsname",goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()),"productcode",goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()),"promitcode",goodsVo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()),"description",goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()),"size",goodsVo.getSize());
        this.goodsService.page(page, queryWrapper);
        List<Goods> records = page.getRecords();
        for (Goods goods:records){
            Provider provider = this.providerService.getById(goods.getProviderid());
            if (null!=provider){
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/addGoods")
    public ServerResponseVO addGoods(GoodsVo goodsVo){
      try {
           if (goodsVo.getGoodsimg()!=null&&goodsVo.getGoodsimg().endsWith("_temp")){
              String newName= AppFileUtils.renameFile(goodsVo.getGoodsimg());
              goodsVo.setGoodsimg(newName);
           }
            this.goodsService.save(goodsVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
          return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
      }

    }

    @RequestMapping(value = "updateGoods",method = RequestMethod.POST)
    public ServerResponseVO updateGoods(GoodsVo goodsVo){
        try {
            //说明不是默认图片
            if (!(goodsVo.getGoodsimg()!=null&&goodsVo.getGoodsimg().equals(Constast.SHOOPING_IMG_PATH+"2021-07-26/upload.jpg"))){
                if (goodsVo.getGoodsimg().endsWith("_temp")){
                    String newName=AppFileUtils.renameFile(goodsVo.getGoodsimg());
                    goodsVo.setGoodsimg(newName);
                    //删除原先图片
                    String oldPath = this.goodsService.getById(goodsVo.getId()).getGoodsimg();
                    AppFileUtils.removeFileByPath(oldPath);

                }
            }
            goodsService.updateById(goodsVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }


    }

    @PostMapping("deleteGoods")
    public ServerResponseVO deleteGoods(Integer id,String goodsimg) {

        try {
            AppFileUtils.removeFileByPath(goodsimg);
            this.goodsService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

        
    }
    @PostMapping("batchdeleteGoods")
    public ServerResponseVO batchdeleteGoods(Integer[]  Ids){
        Collection<Serializable> goodsList = new ArrayList<>();
        for (Integer ids:Ids){
            goodsList.add(ids);
        }
        try {
            this.goodsService.removeByIds(goodsList);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }



    }

    /**
     * 加载所有可用的供应商
     */
    @RequestMapping("loadAllGoodsSelect")
    public DataGridView loadAllProviderForSelect(){
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Goods> list = this.goodsService.list(queryWrapper);
//        for (Goods goods:list){
//            Provider provider = providerService.getById(goods.getProviderid());
//            if (null!=provider){
//                goods.setProvidername(provider.getProvidername());
//            }
//        }
        return new DataGridView(list);
    }


    /**
     * 根据供应商id返回商品信息
     */
    @RequestMapping("loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerid){
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq(providerid!=null,"providerid", providerid);
        List<Goods> list = this.goodsService.list(queryWrapper);
        for (Goods goods:list){
            Provider provider = providerService.getById(goods.getProviderid());
            if (null!=provider){
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }

}
