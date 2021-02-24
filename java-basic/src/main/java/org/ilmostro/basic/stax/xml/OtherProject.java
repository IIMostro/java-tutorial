package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ilmostro.basic.stax.adapter.OtherProjectRecordAdapter;
import org.ilmostro.basic.stax.entity.CustomOtherProjectRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class OtherProject {

    @XmlElement(name = "其他项目标题")
    @XmlJavaTypeAdapter(OtherProjectTitleAdapter.class)
    List<CustomOtherProjectTitle> titles;
    @XmlElement(name = "其他项目记录")
    @XmlJavaTypeAdapter(OtherProjectRecordAdapter.class)
    CustomOtherProjectRecord record;
}
