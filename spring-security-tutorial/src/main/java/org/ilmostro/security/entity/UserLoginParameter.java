package org.ilmostro.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author li.bowei
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginParameter {

    @NotNull(message = "用户名称不能为空！")
    private String username;
    @NotNull(message = "密码不能为空！")
    private String password;
    private Boolean remember = false;
}
