package org.ilmostro.webflux.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author li.bowei
 * @date 2020/8/13 15:19
 */
@Getter
@Setter
@Document(collection = "tx_users")
public class User {

    @Id
    private String id;
    private String name;
    private Integer sex;
    private Date birthday;

}
