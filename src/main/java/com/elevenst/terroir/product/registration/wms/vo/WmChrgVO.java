package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WmChrgVO extends WmsVO implements Serializable {
    private static final long serialVersionUID = 6651253068861016814L;

    private double hcr;		//취급수수료율(Handling Charge Rate)
    private double csc;		//통관서비스수수료(Customs Service Charge)
    private long cscSt;		//통관서비스수수료 기준금액
    private long cstSt;		//관부과세 적용 기준금액
    private long wmsAddDlvCst; //물류비
}
