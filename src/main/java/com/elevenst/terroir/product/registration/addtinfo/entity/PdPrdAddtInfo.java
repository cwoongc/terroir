package com.elevenst.terroir.product.registration.addtinfo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 상품부가정보
 */
@Data
public class PdPrdAddtInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 상품번호
     */
    private long prdNo;

    /**
     * 부가정보구분코드(PD248)
     */
    private String addtInfoClfCd;

    /**
     * 부가정보내용
     */
    private String addtInfoCont;

    /**
     * 생성일시
     */
    private String createDt;


    /**
     * 생성자
     */
    private long createNo;


    /**
     * 수정일시
     */
    private String updateDt;


    /**
     * 수정자
     */
    private long updateNo;


    public PdPrdAddtInfo() {
    }

    public PdPrdAddtInfo(long prdNo, String addtInfoClfCd) {
        this.prdNo = prdNo;
        this.addtInfoClfCd = addtInfoClfCd;
    }

    public PdPrdAddtInfo(long prdNo, String addtInfoClfCd, String addtInfoCont, long createNo, long updateNo) {
        this.prdNo = prdNo;
        this.addtInfoClfCd = addtInfoClfCd;
        this.addtInfoCont = addtInfoCont;
        this.createNo = createNo;
        this.updateNo = updateNo;
    }

    public PdPrdAddtInfo(long prdNo, String addtInfoClfCd, String addtInfoCont, String createDt, long createNo, String updateDt, long updateNo) {
        this.prdNo = prdNo;
        this.addtInfoClfCd = addtInfoClfCd;
        this.addtInfoCont = addtInfoCont;
        this.createDt = createDt;
        this.createNo = createNo;
        this.updateDt = updateDt;
        this.updateNo = updateNo;
    }
}
