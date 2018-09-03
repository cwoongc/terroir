package com.elevenst.terroir.product.registration.pln_prd.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPlnPrd implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * 상품번호
     */
    private long prdNo;

    /**
     * 입고예정일자
     */
    private String wrhsPlnDy;

    /**
     * 입고일자
     */
    private String wrhsDy;

    /**
     * 등록일시
     */
    private String createDt;

    /**
     * 등록자번호
     */
    private long createNo;

    /**
     * 수정일시
     */
    private String updateDt;

    /**
     * 수정자번호
     */
    private long updateNo;


    public PdPlnPrd(long prdNo, String wrhsPlnDy, String updateDt, long updateNo) {
        this.prdNo = prdNo;
        this.wrhsPlnDy = wrhsPlnDy;
        this.updateDt = updateDt;
        this.updateNo = updateNo;
    }

    public PdPlnPrd(long prdNo, String wrhsPlnDy, String wrhsDy, long createNo, long updateNo) {
        this.prdNo = prdNo;
        this.wrhsPlnDy = wrhsPlnDy;
        this.wrhsDy = wrhsDy;
        this.createNo = createNo;
        this.updateNo = updateNo;
    }

    public PdPlnPrd() {}
}
