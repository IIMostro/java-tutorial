package org.ilmostro.basic.stax.xml;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherCost {

    @XmlElement(name = "其他费用记录")
    private List<OtherCostRecord> records;
}
