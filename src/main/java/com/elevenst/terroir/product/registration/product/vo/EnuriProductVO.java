package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnuriProductVO implements Serializable {
    private static final long serialVersionUID = 7716581288295872418L;

    private long modelNo = 0;
    private String modelNm;
    private String factory;
    private String spec;
    private String imgUrl;

    private long price1 = 0;
    private long price2 = 0;
    private long price3 = 0;

    private String st11MinYn;
    private long st11PrdNo = 0;

    private long matchPrdCnt = 0;
    private String brandNm;

    // 검색 조건 & 검색 결과
    private long totalCount = 0;
    private long rnum = 0;
}
