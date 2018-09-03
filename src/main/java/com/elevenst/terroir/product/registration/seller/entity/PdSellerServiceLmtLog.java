package com.elevenst.terroir.product.registration.seller.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdSellerServiceLmtLog implements Serializable{

    private static final long serialVersionUID = 8382366395165704176L;

    //생성일
    private String createDt = "";
    //회원번호
    private long memNo = 0;
    //서비스타입(PD100)
    private String service = "";
    //제한횟수
    private long accessCnt = 0;
}
