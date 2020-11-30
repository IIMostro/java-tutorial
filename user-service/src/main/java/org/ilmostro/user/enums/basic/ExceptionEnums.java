package org.ilmostro.user.enums.basic;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public enum ExceptionEnums {

    SUCCESS(200, "SUCCESS"),
    INNER_ERROR(500, "系统内部错误！")

    ;
    private Integer code;
    private String message;

    ExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
