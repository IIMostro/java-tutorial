package org.ilmostro.user.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ilmostro.user.domain.BasicEntity;

import javax.persistence.*;

/**
 * @author IlMostro
 * @date 2019-11-05 1:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_user_contact")
public class Contact extends BasicEntity {

//    @Id
//    @GeneratedValue
//    private Long id;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 通讯地址
     */
    private String address;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * email
     */
    private String email;
    /**
     * 校园上网mac
     */
    private String mac;
}
