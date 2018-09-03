package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

/**
 * 주문수량기준배송비
 *  구 OrderQunatityBasisDeliveryCostBO
 * */
@Data
public class PrdOrderCountBaseDeliveryVO {

    private long 	dlvCstInstNo;	//배송비설정번호
    private long 	prdNo;			//상품번호
    private long 	ordBgnQty;		//주문시작수량
    private long 	ordEndQty;		//주문종료수량
    private long 	dlvCst;			//배송비
    private String 	createDt;		//등록일시
    private long 	createNo;		//등록자번호
    private String 	updateDt;		//수정일시
    private long 	updateNo;  		//수정자번호
    private String histAplEndDt;    //히스토리 수정일

}

