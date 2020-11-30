package org.ilmostro.user.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ilmostro.user.domain.BasicEntity;
import org.ilmostro.user.domain.department.Department;
import org.ilmostro.user.enums.basic.SexEnums;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "t_student")
public class User extends BasicEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long uid;
    private String name;
    private Integer age;
    @Enumerated(EnumType.ORDINAL)
    private SexEnums sex;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;
//    @Version
//    private Long version;
//    private Long modified = System.currentTimeMillis();
}
