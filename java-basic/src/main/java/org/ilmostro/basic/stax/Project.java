package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "项目")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class Project {

    @XmlAttribute(name = "文件类型")
    private Integer type;
    @XmlAttribute(name = "计价规则")
    private Integer rule;
    @XmlAttribute(name = "结构版本")
    private Integer version;
    @XmlAttribute(name = "地区号")
    private Integer area;
    @XmlAttribute(name = "项目ID")
    private String id;
    @XmlAttribute(name = "项目编号")
    private Long number;
    @XmlAttribute(name = "项目名称")
    private String name;
    @XmlAttribute(name = "招标版本")
    private String biddingVersion;
    @XmlAttribute(name = "招标人")
    private String person;
    @XmlAttribute(name = "招标法人")
    private String legalPerson;
    @XmlAttribute(name = "招标代理人法人")
    private String proxyPerson;
    @XmlAttribute(name = "工期要求")
    private String durationRequirements;
    @XmlAttribute(name = "质量要求")
    private String qualityRequirements;
    @XmlAttribute(name = "编制说明")
    private String description;
    @XmlElement(name = "项目管理")
    private Manager manager;

}
