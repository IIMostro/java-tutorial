package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherProjectTitle {

    @XmlElement(name = "其他项目标题")
    @XmlJavaTypeAdapter(OtherProjectTitleAdapter.class)
    private List<CustomOtherProjectTitle> titles;
    @XmlElement(name = "其他项目记录")
    private List<OtherProjectRecord> records;
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
