package org.ilmostro.basic.stax.xml;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author li.bowei
 **/
@Slf4j
public class OtherProjectTitleAdapter extends XmlAdapter<OtherProjectTitle, CustomOtherProjectTitle> {

    @Override
    public CustomOtherProjectTitle unmarshal(OtherProjectTitle v) throws Exception {
        log.info("v:{}", v);
        return new CustomOtherProjectTitle(v.getContent());
    }

    @Override
    public OtherProjectTitle marshal(CustomOtherProjectTitle v) throws Exception {
        return null;
    }

//    @Override
//    public List<CustomOtherProjectTitle> unmarshal(AdaptedProperties v) throws Exception {
//        List<OtherProjectTitle> property = v.title;
//        for(OtherProjectTitle var1: property){
//            log.info("unmarshal:{}", var1);
//        }
//
//        return null;
//    }
//
//    @Override
//    public AdaptedProperties marshal(List<CustomOtherProjectTitle> v) throws Exception {
//        return null;
//    }
//
//    public static class AdaptedProperties {
//        @XmlElement(name = "其他项目标题")
//        public List<OtherProjectTitle> title = new ArrayList<>();
//    }
}
