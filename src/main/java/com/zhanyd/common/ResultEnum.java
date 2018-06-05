package com.zhanyd.common;

public enum ResultEnum {

	SUCCESS(200, "success"),
	SERVER_ERROR(500, "server error");

    private int code;
    private String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
