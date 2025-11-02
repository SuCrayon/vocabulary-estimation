package com.crayon.ve.constant.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnum {
    SUCCESS("20000", "操作成功"),

    /**
     * 参数不合法的错误
     */
    PARAM_ERROR("P0000", "参数不合法"),
    USER_ACCOUNT_DUPLICATE("P0001", "用户账号重复"),
    NOT_A_STUDENT_ID("P0002", "这不是一个正确的学生id"),
    BUILDING_NOT_FOUND("P0003", "找不到对应的楼栋"),


    SERVER_ERROR("S0000", "服务器错误"),

    FAIL("00000", "操作失败");


    private String code;
    private String description;

    StatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
