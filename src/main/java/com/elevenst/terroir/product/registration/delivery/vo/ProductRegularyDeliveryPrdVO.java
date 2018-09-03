package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProductRegularyDeliveryPrdVO {

    private long prdNo;
    private String reglDlvTypCd;
    private String reglDlvMthdCd;
    private String imdtDlvYn;
    private String reglDlvDy;
    private String reglDlvCnWkdy;
    private String useYn;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;

}
