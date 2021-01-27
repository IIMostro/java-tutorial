package org.ilmostro.security.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author shuang.kou
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
