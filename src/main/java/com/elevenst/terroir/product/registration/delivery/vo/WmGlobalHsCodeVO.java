package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WmGlobalHsCodeVO {

    private long gblHsCodeNo;
    private String gblHsCode;
    private String korNm;
    private String engNm;
    private Date aplBgnDt;
    private Date aplEndDt;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
