package org.ilmostro.basic.stax;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "其他项目")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherProject {

    @XmlElement(name = "其他项目标题")
    List<OtherProjectTitle> titles;
    @XmlElement(name = "其他项目记录")
    @XmlJavaTypeAdapter(OtherProjectRecordAdapter.class)
    CustomOtherProjectRecord record;
}
