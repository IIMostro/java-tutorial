package org.ilmostro.webflux.exception;

import org.ilmostro.webflux.result.BasicConst;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author li.bowei
 * @date 2020/12/2 16:33
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Throwable error = getError(request);
        if (error instanceof BizException) {
            errorAttributes.put(BasicConst.Result.CODE, ((BizException) error).getCode());
            errorAttributes.put(BasicConst.Result.MSG, ((BizException) error).getMsg());
        } else {
            errorAttributes.put(BasicConst.Result.CODE, HttpStatus.INTERNAL_SERVER_ERROR);
            errorAttributes.put(BasicConst.Result.MSG, "INTERNAL SERVER ERROR");
        }
        return errorAttributes;
    }
}
