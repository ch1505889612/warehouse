package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Loginfo;
import com.ch.warehouse.mapper.LoginfoMapper;
import com.ch.warehouse.service.LoginfoService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.vo.LogInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author procedure sail
 * @date 2021/7/17 19:16
 */
@RestController
@RequestMapping("loginfo")
public class LogInfoController {

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private LoginfoMapper mapper;
    /**
     * 全查询
     */

    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LogInfoVo logInfoVo){
        IPage<Loginfo> page = new Page<>(logInfoVo.getPage(), logInfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginname()),"loginname",logInfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginip()),"loginip",logInfoVo.getLoginip());
        queryWrapper.ge(logInfoVo.getStartTime()!=null,"logintime",logInfoVo.getStartTime());
        queryWrapper.le(logInfoVo.getEndTime()!=null,"logintime",logInfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        this.mapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/deleteLoginfo")
    public ServerResponseVO deleteLoginfo(Integer id){
        try {
            mapper.deleteById(id);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }
    }
    /**
     * 批量删除
     */
    @PostMapping("/batchdeleteLoginfo")
    public ServerResponseVO batchdeleteLoginfo(Integer[] Ids){
        Collection<Serializable> idList = new ArrayList<>();
        try {
            for (Integer id:Ids){
                idList.add(id);
            }
            mapper.deleteBatchIds(idList);
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
           return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }
    }


}
