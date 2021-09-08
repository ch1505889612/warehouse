package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.Loginfo;
import com.ch.warehouse.entity.Notice;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.mapper.NoticeMapper;
import com.ch.warehouse.service.NoticeService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import com.ch.warehouse.vo.NoticeVo;
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
 * @date 2021/7/19 17:08
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping ("/toNoticeManager")
    public DataGridView toNoticeManager(NoticeVo noticeVo){
        IPage<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        this.noticeMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @PostMapping("/addNotice")
    public ServerResponseVO addNotice(NoticeVo noticeVo){
        User user = (User) WebUtils.getSession().getAttribute("user");
        noticeVo.setOpername(user.getRemark());
        noticeVo.setCreatetime(new Date());
        int insert = this.noticeMapper.insert(noticeVo);
        if (insert>0){
            return new ServerResponseVO(ServerResponseEnum.ADD_SUCCESS);
        }
        return new ServerResponseVO(ServerResponseEnum.ADD_ERROR);
    }

    @RequestMapping(value = "updateNotice",method = RequestMethod.POST)
    public ServerResponseVO updateNotice(NoticeVo noticeVo){
        int i = this.noticeMapper.updateById(noticeVo);
            if (i>0){
                return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);
            }else {
                return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
            }
    }

    @PostMapping("deleteNotice")
    public ServerResponseVO deleteNotice(Integer id) {
        int i = this.noticeMapper.deleteById(id);
        if (i > 0) {
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        } else {
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

    }
    @PostMapping("batchdeleteNotice")
    public ServerResponseVO batchdeleteNotice(Integer[]  Ids){
        Collection<Serializable> noticeList = new ArrayList<>();
        for (Integer ids:Ids){
            noticeList.add(ids);
        }
        int i = this.noticeMapper.deleteBatchIds(noticeList);
        if (i>0){
            return new ServerResponseVO(ServerResponseEnum.DELETE_SUCCESS);
        }else {
            return new ServerResponseVO(ServerResponseEnum.DELETE_ERROR);
        }

    }






}
