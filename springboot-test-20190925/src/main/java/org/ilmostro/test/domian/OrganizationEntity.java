package org.ilmostro.test.domian;

import lombok.Data;

import java.util.Collection;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Data
public class OrganizationEntity {

    private Collection<UserEntity> users;
}
