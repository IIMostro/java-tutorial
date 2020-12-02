package org.ilmostro.webflux.exception;

/**
 * @author li.bowei
 * @date 2020/12/2 16:33
 */
public class BizException extends RuntimeException{

    private Integer code;
    private String msg;

    public BizException() {
    }

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
