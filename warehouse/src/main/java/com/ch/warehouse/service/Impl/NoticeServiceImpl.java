package com.ch.warehouse.service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ch.warehouse.mapper.NoticeMapper;
import com.ch.warehouse.service.NoticeService;

import java.util.Date;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/19 13:45
*/
@Service
public class NoticeServiceImpl implements NoticeService{

    @Resource
    private NoticeMapper noticeMapper;



}
