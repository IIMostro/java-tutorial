package org.ilmostro.start.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author li.bowei
 * @date 2020/11/7 15:18
 */
@ControllerAdvice
@Slf4j
public class BasicControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String basic(Exception exception){
        log.info("into basic exception controller!");
        return exception.getMessage();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public String running(RuntimeException exception){
        log.info("into running exception controller!");
        return exception.getMessage();
    }
}
