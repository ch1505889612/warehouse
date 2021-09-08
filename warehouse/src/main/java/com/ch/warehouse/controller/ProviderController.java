package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Provider;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.service.ProviderService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.ProviderVo;
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
@RequestMapping("provider")
@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;


    @GetMapping("/toProviderManager")
    public DataGridView toProviderManager(ProviderVo providerVo){
        IPage<Provider> page = new Page<>(providerVo.getPage(), providerVo.getLimit());
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getTelephone()),"telephone",providerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        this.providerService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/addProvider")
    public ServerResponseVO addProvider(ProviderVo providerVo){
        User user = (User) WebUtils.getSession().getAttribute("user");
      try {
          this.providerService.save(providerVo);
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
          return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
      }

    }

    @RequestMapping(value = "updateProvider",method = RequestMethod.POST)
    public ServerResponseVO updateProvider(ProviderVo providerVo){
        try {
            providerService.updateById(providerVo);
            return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
        }


    }

    @PostMapping("deleteProvider")
    public ServerResponseVO deleteProvider(Integer id) {

        try {
            this.providerService.removeById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

        
    }


    @PostMapping("batchdeleteProvider")
    public ServerResponseVO batchdeleteProvider(Integer[]  Ids){
        Collection<Serializable> providerList = new ArrayList<>();
        for (Integer ids:Ids){
            providerList.add(ids);
        }
        try {
            this.providerService.removeByIds(providerList);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }





    }

/**
 * 加载所有可用的供应商
 */
 @RequestMapping("loadAllProviderForSelect")
 public DataGridView loadAllProviderForSelect(){
    QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
    queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
    List<Provider> list = this.providerService.list(queryWrapper);
    return new DataGridView(list);
}


}
