package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressVO implements Serializable{

    private long prdNo;
    private long memNo;
    private long addrSeq;
    private long itgAddrSeq;		// 통합출고지,반품교환지를 묶기 위한 출고지 순번
    private String addrCd; 			// 주소구분코드(01-배송지, 02-출고지, 03-반품,교환지)
    private String prdAddrClfCd;
    private String addrNm;
    private String mbAddrLocation; 	//해외주소구분
    private String freePickupYn;	// 무료픽업 대상 여부
    private String frCtrCd; 		//해외주문국가코드
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
}
