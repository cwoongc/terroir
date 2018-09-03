package com.elevenst.terroir.product.registration.coordi_prd.vo;

import lombok.Data;

@Data
public class CoordiProductVO {

    private static final long serialVersionUID = 354672571708915165L;

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

    private String prdNm;
    private String dtlBasicExtNm;
    private String selStatNm;

    public CoordiProductVO() {
    }

    public CoordiProductVO(long prdNo, long coordiPrdNo, long createNo) {
        this.prdNo = prdNo;
        this.coordiPrdNo = coordiPrdNo;
        this.createNo = createNo;
    }
}
