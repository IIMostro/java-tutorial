package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class TechnicalMeasures {

    @XmlAttribute(name = "技措ID")
    private String id;
}
