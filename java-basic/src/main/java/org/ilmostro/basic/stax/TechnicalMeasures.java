package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "技术措施")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class TechnicalMeasures {

    @XmlAttribute(name = "技措ID")
    private String id;
}
