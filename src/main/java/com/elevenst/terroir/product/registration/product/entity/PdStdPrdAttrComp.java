package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdStdPrdAttrComp implements Serializable{

    private long prdNo = 0;
    private long stdAttrCompNo = 0;
    private String stdAttrValueNm = "";
    private String attrClfCd = "";
    private long ctlgAttrNo = 0;
    private long ctlgAttrValueNo = 0;
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
}
