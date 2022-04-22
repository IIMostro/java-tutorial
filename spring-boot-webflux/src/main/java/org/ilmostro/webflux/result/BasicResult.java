package org.ilmostro.webflux.result;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.NumberUtils;

/**
 * @author li.bowei
 * @date 2020/12/2 16:49
 */
public class BasicResult<T> {

    private Integer code;
    private String msg;
    private T data;

    public static BasicResult<Object> getInstance(Map<String, Object> properties) {
        BasicResult<Object> result = new BasicResult<>();
        Optional.of(BasicConst.Result.CODE).map(properties::get).map(Object::toString).map(Integer::valueOf).ifPresent(result::setCode);
        Optional.of(BasicConst.Result.MSG).map(properties::get).map(Objects::toString).ifPresent(result::setMsg);
        Optional.of(BasicConst.Result.DATA).map(properties::get).ifPresent(result::setData);
        return result;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
