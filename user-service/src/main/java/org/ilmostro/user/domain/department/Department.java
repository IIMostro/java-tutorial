package org.ilmostro.user.domain.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ilmostro.user.domain.BasicEntity;
import org.ilmostro.user.domain.user.User;
import org.ilmostro.user.enums.basic.StatusEnums;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_department")
@ApiModel(value = "部门信息")
public class Department extends BasicEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @ApiModelProperty("部门id")
//    private Long id;

    @ApiModelProperty(value = "部门名称",required = true)
    @NotNull
    private String name;

    @ApiModelProperty("部门描述")
    private String description;

    @ApiModelProperty("是否禁用")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnums status;

    @ApiModelProperty("上級部門")
    private Long parent;

//    @Version
//    private Long version;
//    private Long modified = System.currentTimeMillis();
}
