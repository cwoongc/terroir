package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductSearchVO implements Serializable {
    private long    prdNo;
    private String  prdNm;
    private String  selStatCd;
    private String  stdPrdYn;
    private long    totalCount;
    private String  searchName;
    private long    selMnbdNo;
    private String  bsnDealClf;
    private long    start;
    private long    end;
    private long    limit;
    private String  searchType;
    private String  prdTypCd;
    private Date    createDt;
    private Date  updateDt;

}