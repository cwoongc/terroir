package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TrGlobalProductAverageDeliveryVO {

    private String summaryDt;	// 집계일자
    private long prdNo;			// 상품번호
    private long avgDeliDays;	// 주문상품별 평균배송일
    private String createDt;	// 생성일
    private String updateDt;	// 수정일

}
