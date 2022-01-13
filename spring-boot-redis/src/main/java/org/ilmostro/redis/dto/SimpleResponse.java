package org.ilmostro.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author li.bowei
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> SimpleResponse<T> success(T data){
        return new SimpleResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }
}
