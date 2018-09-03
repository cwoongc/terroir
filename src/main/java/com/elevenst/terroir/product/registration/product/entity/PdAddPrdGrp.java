package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PdAddPrdGrp {

    long mainPrdNo;
    long addPrdGrpNo;
    String addPrdGrpNm;
    long dispPrrtRnk;
    String useYn;
    Date createDt;
    long createNo;
    Date updateDt;
    long updateNo;
}
