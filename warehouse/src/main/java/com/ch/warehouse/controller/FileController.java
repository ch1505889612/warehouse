package com.ch.warehouse.controller;

import cn.hutool.core.date.DateUtil;
import com.ch.warehouse.common.AppFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 * @author procedure sail
 * @date 2021/7/25 21:57
 */
@RequestMapping("file")
@RestController
public class FileController {


    /**
     * 文件上传
     */

    @RequestMapping("uploadFile")
    public Map<String,Object> uploadFile(MultipartFile mf){
        //1.得到文件名
        String oldname = mf.getOriginalFilename();
        //2.根据文件名生成新的文件名
        String newName=  AppFileUtils.createNewFileName(oldname);
        //3.得到当前日期的字符串
        String dirName= DateUtil.format(new Date(),"yyyy-MM-dd");
        //4.构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
        //5.判断当前文件夹是否存在
        if (!dirFile.exists()){
            //创建文件夹
            dirFile.mkdirs();
        }
        //6.构造文件对象
        File file = new File(dirFile, newName+"_temp");
        //7.把mf里面的图片信息写入file
        try {
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("path",dirName+"/"+newName+"_temp");
        return map;
    }


    @RequestMapping("uploadHeadImgFile")
    public Map<String,Object> uploadHeadImgFile(MultipartFile mf){
        //1.得到文件名
        String oldname = mf.getOriginalFilename();
        //2.根据文件名生成新的文件名
        String newName=  AppFileUtils.createNewFileName(oldname);
        //3.得到当前日期的字符串
        String dirName= DateUtil.format(new Date(),"yyyy-MM-dd");
        //4.构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH+"headimg/", dirName);
        //5.判断当前文件夹是否存在
        if (!dirFile.exists()){
            //创建文件夹
            dirFile.mkdirs();
        }
        //6.构造文件对象
        File file = new File(dirFile, newName+"_temp");
        //7.把mf里面的图片信息写入file
        try {
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("path","headimg/"+dirName+"/"+newName+"_temp");
        return map;
    }



    /**
     * 图片下载
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path){
        return AppFileUtils.createResponseEntity(path);
    }









}
