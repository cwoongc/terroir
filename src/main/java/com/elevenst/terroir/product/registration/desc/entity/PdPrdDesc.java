package com.elevenst.terroir.product.registration.desc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdPrdDesc implements Serializable {
    private static final long serialVersionUID = -244795331417493880L;

    /**
     * 상품설명 번호(시퀀스)
     */
    private long prdDescNo;

    /**
     * 상품번호
     */
    private long prdNo;

    /**
     * 상품설명유형코드 (PD022)
     */
    private String prdDescTypCd;

    /**
     * 상품설명내용 VARCHAR2
     */
    private String prdDescContVc;

    /**
     * 상품설명내용 CLOB
     */
    private String prdDescContClob;

    /**
     * CLOB 타입여부
     */
    private String clobTypYn;

    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private long splitedCnt;
    private String splitedUrl;

    /**
     * 상품상세 구분 코드 (PD160)
     *
     */
    private String prdDtlTypCd;

    private String mode;


    public PdPrdDesc() {}

    public PdPrdDesc(long prdDescNo, long prdNo, String prdDescTypCd, String prdDescContVc, String prdDescContClob, String clobTypYn, String createDt, long createNo, String updateDt, long updateNo, long splitedCnt, String splitedUrl, String prdDtlTypCd, String mode) {
        this.prdDescNo = prdDescNo;
        this.prdNo = prdNo;
        this.prdDescTypCd = prdDescTypCd;
        this.prdDescContVc = prdDescContVc;
        this.prdDescContClob = prdDescContClob;
        this.clobTypYn = clobTypYn;
        this.createDt =createDt;
        this.createNo =createNo;
        this.updateDt = updateDt;
        this.updateNo = updateNo;
        this.splitedCnt = splitedCnt;
        this.splitedUrl = splitedUrl;
        this.prdDtlTypCd = prdDtlTypCd;
        this.mode = mode;
    }
}
