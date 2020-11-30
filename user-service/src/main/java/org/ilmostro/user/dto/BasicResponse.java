package org.ilmostro.user.dto;

import lombok.Data;
import org.ilmostro.user.enums.basic.ExceptionEnums;
import org.ilmostro.user.exception.BizException;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@Data
public class BasicResponse<T> {

    private Integer code;
    private String message;
    private Long timestamp;
    private T data;

    public BasicResponse(T data) {
        this.code = ExceptionEnums.SUCCESS.getCode();
        this.message = ExceptionEnums.SUCCESS.getMessage();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public BasicResponse(BizException ex){
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

    public BasicResponse() {
    }
}
