package org.ilmostro.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ilmostro.security.domain.UserEntity;

import javax.validation.constraints.NotNull;

/**
 * @author li.bowei
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResult {

    private UserEntity user;
    private String token;
}
