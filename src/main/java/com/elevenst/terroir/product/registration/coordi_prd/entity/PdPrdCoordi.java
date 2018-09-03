package com.elevenst.terroir.product.registration.coordi_prd.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 소호 플랫폼 코디 상품 (PD_PRD_COORDI)
 */
@Data
public class PdPrdCoordi implements Serializable{

    private static final long serialVersionUID = 354672571708915162L;

    /**
     * 상품번호
      */
    private long prdNo;

    /**
     * 코디상품번호
     */
    private long coordiPrdNo;

    /**
     * 생성일시
     */
    private String createDt;

    /**
     * 생성자번호
     */
    private long createNo;



    public PdPrdCoordi() {
    }

    public PdPrdCoordi(long prdNo, long coordiPrdNo, long createNo) {
        this.prdNo = prdNo;
        this.coordiPrdNo = coordiPrdNo;
        this.createNo = createNo;
    }
}
