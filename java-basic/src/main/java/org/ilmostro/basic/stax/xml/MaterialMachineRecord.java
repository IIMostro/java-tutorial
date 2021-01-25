package org.ilmostro.basic.stax.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class MaterialMachineRecord {

    @XmlAttribute(name = "工料机ID")
    private String materialMachineId;
    @XmlAttribute(name = "招标材料ID")
    private String biddingMaterialId;
    @XmlAttribute(name = "编号")
    private String number;
    @XmlAttribute(name = "名称")
    private String name;
    @XmlAttribute(name = "规格型号")
    private String specificationsAndModels;
    @XmlAttribute(name = "品牌产地")
    private String brandOrigin;
    @XmlAttribute(name = "单位")
    private String unit;
    @XmlAttribute(name = "数量")
    private BigDecimal count;
    @XmlAttribute(name = "单价")
    private BigDecimal price;
    @XmlAttribute(name = "单价拓展")
    private BigDecimal priceExpend;
    @XmlAttribute(name = "工料机类型")
    private Integer materialMachineType;
    @XmlAttribute(name = "是否主要工料机")
    private Boolean isMainMaterialMachine;
    @XmlAttribute(name = "是否未计价材料")
    private Boolean isNotDenominatedMaterial;
    @XmlAttribute(name = "备注")
    private String remark;

}
