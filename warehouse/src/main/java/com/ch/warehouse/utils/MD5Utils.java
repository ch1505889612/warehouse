package com.ch.warehouse.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * @author procedure sail
 * @date 2021/7/16 10:30
 */
public class MD5Utils {
    @Test
    public void test(){
        String source="123456";
        Md5Hash hash = new Md5Hash(source,"3D5F956E053C4E85B7D2681386E235D2",15);
        System.out.println(hash.toString());
    }
}
