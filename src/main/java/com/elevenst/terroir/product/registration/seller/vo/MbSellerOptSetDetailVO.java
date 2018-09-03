package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MbSellerOptSetDetailVO implements Serializable {
    private static final long serialVersionUID = -8213085273383502650L;

    private long memNo; // 회원번호
    private String optSvcCd; // 옵션 서비스 명
    private String optCd; // 옵션 코드
    private String optVal; // 옵션 값
    private Date createDt;
    private Date updateDt;
    private String createNo;
    private String updateNo;
}
