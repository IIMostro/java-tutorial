package org.ilmostro.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author IlMostro
 * @date 2019-11-05 23:32
 */
@MappedSuperclass
@Data
public class BasicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @JsonIgnore
    private Integer version;
    @JsonIgnore
    private Long modified = System.currentTimeMillis();
}
