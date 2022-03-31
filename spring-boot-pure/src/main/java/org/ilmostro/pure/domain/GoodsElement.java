package org.ilmostro.pure.domain;

import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.Id;

/**
 * @author li.bowei
 */
@Data
//@Entity
public class GoodsElement {

//    @Id
    private Integer id;
    private String name;
    private String description;

    public void copy(GoodsElement other){
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
    }
}
