package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCombPrdVO implements Serializable {

    private static final long serialVersionUID = 6954594568531231510L;
    private long mstrObjNo;
    private String mstrObjType;
    private String combTypCd;
    private String useYn = "Y";
    private int dispPrrt;
    private String mstrYn;	// 대표여부

    private Long prdNo;
    private String prdNm;
    private long selPrc;
    private long finalDscPrc;
    private String imgUrl;
    private String thumImgUrl;

    private Long prdStckNo;
    private long totalCount;
    private String matchablePrdYn;

    private long updateNo;

    private ProductVO productVO;
}
