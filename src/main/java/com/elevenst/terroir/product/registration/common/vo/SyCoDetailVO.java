package com.elevenst.terroir.product.registration.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SyCoDetailVO implements Serializable{

    private String grpCd;
    private String dtlsCd;
    private String dtlsComNm;
    private String cdVal1;
    private String cdVal2;
    private String cdVal3;
    private long prrtRnk;
    private String useYn;
    private String dtlsCdDesc;
    private String parentCd;
    private String dtlsComEngNm;
    private String cdVal1Eng;
}
