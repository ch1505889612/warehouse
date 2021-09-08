package com.ch.warehouse.common;

/**
 * 常量类
 * @author procedure sail
 * @date 2021/7/17 9:51
 */
public interface Constast {

    /**
     * 菜单权限
     */
    public static final String TYPE_MENU="menu";
    public static final String TYPE_PERMISSION="permission";

    /**
     * 可用状态
     */
     static final Object AVAILABLE_TRUE =1;
     static final Object AVAILABLE_FALSE =0 ;
    /**
     * 用户类型
     */
    static final Integer USER_TYPE_SUPER=1;
    static final Integer USER_TYPE_NORMAL=0;
    Integer OPEN_TRUE= 1;
    Integer OPEN_FALSE= 0;
    String USER_DEFAULT_PWD = "123456";

    /**
     * 商品默认图片路径
     */
    static final String SHOOPING_IMG_PATH="/file/showImageByPath?path=";


    /**
     * 头像路径
     */
    static final String IMGPATH="/file/showImageByPath?path=";
}
