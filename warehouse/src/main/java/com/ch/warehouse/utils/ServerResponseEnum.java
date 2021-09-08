package com.ch.warehouse.utils;

import lombok.*;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ServerResponseEnum  {


    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    DUPLICATE_ACCOUNT(12, "账号重复"),
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    INCORRECT_CREDENTIALS(14, "账号或密码错误"),
    CODE_ERROR(500,"验证码错误"),
    NOT_LOGIN_IN(15, "账号未登录"),
    UNAUTHORIZED(16, "没有权限"),
    DELETE_SUCCESS(200,"删除成功"),
    DELETE_ERROR(500,"删除失败"),
    ADD_SUCCESS(200,"添加成功"),
    ADD_ERROR(500,"添加失败"),
    UPDATE_SUCCESS(200,"修改成功"),
    UPDATE_ERROR(500,"修改失败"),
    OPERATE_SUCCESS(200,"操作成功"),
    OPERATE_ERROR(500,"操作失败");

    Integer code;
    String msg;
}