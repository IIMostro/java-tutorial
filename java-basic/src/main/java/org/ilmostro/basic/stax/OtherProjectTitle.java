package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement(name = "其他项目标题")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherProjectTitle {
    
    @XmlElement(name = "其他项目标题")
    private List<OtherProjectTitle> titles;
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
    private Integer pay;
    @XmlAttribute(name = "工作服务内容")
    private String content;
    @XmlAttribute(name = "备注")
    private String remark;
}
