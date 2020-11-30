package org.ilmostro.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.user.dto.BasicResponse;
import org.ilmostro.user.stream.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageService service;

    @Autowired
    public GlobalExceptionHandler(MessageService service) {
        this.service = service;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public BasicResponse bizExceptionHandler(BizException ex){
        log.error("业务异常！{}", ex.getMessage());
        service.error(ex);
        return new BasicResponse(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasicResponse runtimeException(RuntimeException ex){
        log.error("系统错误！{}", ex.getMessage());
        service.error(ex);
        BasicResponse<Object> response = new BasicResponse<>();
        response.setData(ex);
        response.setCode(500);
        response.setMessage(ex.getMessage());
        return response;
    }
}
