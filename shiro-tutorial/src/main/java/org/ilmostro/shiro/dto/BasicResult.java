package org.ilmostro.shiro.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author li.bowei
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BasicResult<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> BasicResult<T> success(T data){
        return new BasicResult<T>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static BasicResult<Void> failure(Integer code, String msg){
        return new BasicResult<>(code, msg, null);
    }
}
