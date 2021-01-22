package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "项目管理")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class Manager {

    @XmlAttribute(name = "整体工程ID")
    private String id;
    @XmlAttribute(name = "名称")
    private String name;
    @XmlAttribute(name = "是否包含分部分项")
    private Boolean includeComponent;
    @XmlAttribute(name = "是否包含措施项目")
    private Boolean includeMeasures;
    @XmlAttribute(name = "是否包含其他项目")
    private Boolean includeOtherProject;
    @XmlElement(name = "技术措施")
    private TechnicalMeasures technicalMeasures;
    @XmlElement(name = "组织措施")
    private OrganizationMeasure organizationMeasure;
    @XmlElement(name = "其他项目")
    private OtherProject otherProject;
}
