package com.ch.warehouse.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Inport;
import com.ch.warehouse.vo.InportVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author procedure sail
* @date 2021/7/26 10:56
*/
public interface InportMapper  extends BaseMapper<Inport> {

    List<Inport> echartsInportSeven(@Param("goodsId")Integer goodsId,@Param("startTime") String startTime, @Param("endTime")String endTime);
}