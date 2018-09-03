package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WmInsrCstVO extends WmsVO implements Serializable {
    private static final long serialVersionUID = -7904824423771623301L;

    /**
     * 적용시작 상품 가격
     */
    private long bgnPrdPrc;
    /**
     * 적용종료 상품 가격
     */
    private long endPrdPrc;
    /**
     * 배송비(원)
     */
    private long insrCst;
}
