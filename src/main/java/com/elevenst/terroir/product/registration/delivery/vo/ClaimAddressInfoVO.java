package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *  출고/교환/반품지 주소 정보관리(구 InOutAddressInfo bean)
 * */
@Data
public class ClaimAddressInfoVO implements Serializable {

    private long prdNo;
    private long memNo;
    private String concatAddr;
    //
    private long addrSeq;
    //01:출고지, 02:교환/반품지, 03:방문수령지, 04:판매자해외출고지, 05:판매자해외반품교환지
    private String prdAddrClfCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    //
    private String addrNm;
    //
    private String mbAddrLocation; 	//해외주소구분
    //
    private String addrCd; 			// 주소구분코드(01-배송지, 02-출고지, 03-반품,교환지)
    //
    private long itgAddrSeq;		// 통합출고지,반품교환지를 묶기 위한 출고지 순번
    //?
    private String freePickupYn;	// 무료픽업 대상 여부
    //?
    private String frCtrCd; 		//해외주문국가코드


    private String state;
    private String city;
    private String countryCd;
    private String position;

}
