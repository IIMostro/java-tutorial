package org.ilmostro.basic.stax.xml;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "组织措施")
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
