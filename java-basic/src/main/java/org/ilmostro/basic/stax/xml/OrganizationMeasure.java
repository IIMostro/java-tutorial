package org.ilmostro.basic.stax.xml;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OrganizationMeasure {

    @XmlAttribute(name = "组措ID")
    private String id;
    @XmlElement(name = "组措记录")
    private List<OrganizationMeasureRecord> records;
    @XmlElement(name = "组措标题")
    private OrganizationMeasureTitle title;
}
