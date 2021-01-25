package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherProjectRecord {
    @XmlAttribute(name = "其他项目ID")
    private String id;
    @XmlAttribute(name = "编号")
    private String number;
    @XmlAttribute(name = "名称")
    private String name;
    @XmlAttribute(name = "单位")
    private String unit;
    @XmlAttribute(name = "数量")
    private BigDecimal count;
    @XmlAttribute(name = "合价")
    private BigDecimal amount;
    @XmlAttribute(name = "其他项目类型")
    private String type;
    @XmlAttribute(name = "付费标志")
    private Pay pay;
    @XmlAttribute(name = "工作服务内容")
    private String content;
    @XmlAttribute(name = "备注")
    private String remark;

    @XmlEnum(Integer.class)
    protected enum Pay{
        @XmlEnumValue("1")
        ALREADY(3),
        @XmlEnumValue("2")
        NOT(4)

        ;
        private final Integer value;

        Pay(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
