package org.ilmostro.user.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ilmostro.user.enums.basic.ExceptionEnums;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

    private Integer code;
    private String message;

    public BizException(ExceptionEnums exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
