package com.ch.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.warehouse.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author procedure sail
* @date 2021/7/19 13:45
*/
public interface NoticeMapper extends BaseMapper<Notice> {
//    /**
//     *
//     * @param current
//     * @param size
//     * @return
//     */
//  List<Notice> pageQuery(@Param("current") Integer current,@Param("size") Integer size);
//
//    /**
//     * 总条数
//     */
//    Integer noticeCount(@Param("opername") String opername,
//                        @Param("title") String title,
//                        @Param("startTime") Date startTime,
//                        @Param("endTime")Date endTime);
//
//    /**
//     * 条件查询
//     */
//  List<Notice> batchQuerys(
//          @Param("opername") String opername,
//          @Param("title") String title,
//          @Param("startTime") Date startTime,
//          @Param("endTime")Date endTime,
//          @Param("current") Integer current,
//          @Param("size") Integer size
//  );


}