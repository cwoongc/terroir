package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProductCombMstrVO implements Serializable {

    private static final long serialVersionUID = 2744282603538229994L;
    private Long mstrObjNo;
    private String mstrObjType;
    private String combTypCd;
    private String mstrObjNm;
    private Date expBgnDt;
    private Date expEndDt;
    private Date createDt;
    private Long createNo;
    private Date updateDt;
    private Long updateNo;
    private String updaterInfo;
    private int cnt;
    private int liveCnt;
    private long totalCount;

    private String useYn;
    private String empNm;
    private String selStatNm;

    private List<ProductCombPrdVO> productCombPrdVOList;

    private ProductCombMstrVO preInfo;
}
