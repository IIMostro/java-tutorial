package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "组措记录")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OrganizationMeasureRecord {

    @XmlAttribute(name = "组措ID")
    private String id;
    @XmlAttribute(name = "编号")
    private String number;
    @XmlAttribute(name = "名称")
    private String name;
    @XmlAttribute(name = "单位")
    private String unit;
    @XmlAttribute(name = "数量")
    private BigDecimal quantity;
    @XmlAttribute(name = "单价")
    private BigDecimal price;
    @XmlAttribute(name = "合价")
    private BigDecimal amount;
    @XmlAttribute(name = "备注")
    private String remark;
    @XmlAttribute(name = "组措类型")
    private Integer type;
    @XmlAttribute(name = "费率标志")
    private Integer flag;
    @XmlAttribute(name = "费率上限")
    private BigDecimal maximum;
    @XmlAttribute(name = "费率下限")
    private BigDecimal minimum;
}
