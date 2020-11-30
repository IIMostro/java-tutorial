package org.ilmostro.user.enums.basic;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public enum  SexEnums {

    /**
     * 男女的枚举
     */
    MAN("男"),
    FEMALE("女");

    private String value;

    SexEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
