package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "其他费用")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherCostRecord {

    @XmlAttribute(name = "费用ID")
    private String id;
    @XmlAttribute(name = "编号")
    private String number;
    @XmlAttribute(name = "名称")
    private String name;
    @XmlAttribute(name = "金额")
    private BigDecimal money;
    @XmlAttribute(name = "费用类型")
    private Integer type;
    @XmlAttribute(name = "费率")
    private BigDecimal fee;
}
