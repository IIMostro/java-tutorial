package org.ilmostro.basic.stax;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OtherProjectRecordAdapter extends XmlAdapter<OtherProjectRecord, CustomOtherProjectRecord> {
    @Override
    public CustomOtherProjectRecord unmarshal(OtherProjectRecord v) throws Exception {
        CustomOtherProjectRecord result = new CustomOtherProjectRecord();
        result.setId(v.getId());
        result.setContent(v.toString());
        return result;
    }

    @Override
    public OtherProjectRecord marshal(CustomOtherProjectRecord v) throws Exception {

        return null;
    }
}
