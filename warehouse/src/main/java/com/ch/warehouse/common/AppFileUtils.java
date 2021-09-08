package com.ch.warehouse.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.utils.WebUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppFileUtils {

    /**
     * 默认值
     */
    public static  String UPLOAD_PATH="C:/upload/";



    static {
        //读取配置文件的存储地址
        InputStream stream = FileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();

                try {
                    properties.load(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        String property = properties.getProperty("filepath");
        if (null!=property){
            UPLOAD_PATH=property;
        }

    }

    /**
     * 根据文件老名字得到文件新名字
     * @param oldname
     * @return
     */
    public static String createNewFileName(String oldname) {
       String stuff=  oldname.substring(oldname.lastIndexOf("."),oldname.length());
       return IdUtil.simpleUUID().toUpperCase()+stuff;
    }

    /**
     * 文件下载
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1.构造文件对象
        File file = new File(UPLOAD_PATH, path);
        if (file.exists()){
            //将下载的文件,封装btye[]
            byte[] bytes=null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //创建封装响应信息的对象
            HttpHeaders httpHeaders = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM响应的内容不受限制)
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
             //设置下载的文件的名称
           // httpHeaders.setContentDispositionFormData("attachment","123.jpg");

            //创建
            ResponseEntity<Object> entity = new ResponseEntity<Object>(bytes,httpHeaders, HttpStatus.CREATED);
            return entity;

        }
        return null;
    }

    /**
     * 根据路径改名字,去掉_temp
     * @param goodsimg
     * @return
     */
    public static String renameFile(String goodsimg) {

        File file = new File(UPLOAD_PATH, goodsimg);
        String replace = goodsimg.replace("_temp", "");
        if (file.exists()){
            file.renameTo(new File(UPLOAD_PATH,replace));
        }
        return replace;
    }

    /**
     * 根据老路径删除原来图片
     * @param oldPath
     */
    public static void removeFileByPath(String oldPath) {
        File file = new File(UPLOAD_PATH, oldPath);
        if (!oldPath.equals(Constast.SHOOPING_IMG_PATH)){
            if (file.exists()){
                file.delete();
            }
        }

    }


    /**
     * 根据老路径删除原来图片
     * @param oldPath
     */
    public static void removeFileByHeadImgPath(String oldPath) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        File file = new File(UPLOAD_PATH, oldPath);
        if (oldPath.equals(Constast.IMGPATH+user.getImgpath())){
            if (file.exists()){
                file.delete();
            }
        }

    }
}