package org.ilmostro.basic.stax.adapter;

import org.ilmostro.basic.stax.xml.OtherProjectRecord;
import org.ilmostro.basic.stax.entity.CustomOtherProjectRecord;

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
