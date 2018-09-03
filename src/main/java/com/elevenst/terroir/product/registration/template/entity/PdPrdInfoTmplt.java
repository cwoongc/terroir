package com.elevenst.terroir.product.registration.template.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdInfoTmplt implements Serializable{

    private long    prdInfoTmpltNo;
    private long    memNo;
    private String  prdInfoTmpltNm;
    private String  prdInfoTmpltClfCd;
    private String  createNo;
    private String  updateNo;
}
