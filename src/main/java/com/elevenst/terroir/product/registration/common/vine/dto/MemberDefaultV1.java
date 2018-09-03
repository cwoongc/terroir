package com.elevenst.terroir.product.registration.common.vine.dto;

import lombok.Data;

@Data
public class MemberDefaultV1 {

    //회원번호
    private long memberNo;
    //회원아이디
    private String memberId;
    //회원구분코드(MB013)
    private String memberClassification;
    //회원유형코드(MB014)
    private String memberType;
    //회원상태코드(MB015)
    private String memberStatus;
    //회원상태상세(MB016)
    private String memberStatusDetail;
    //회원가입사이트구분코드(MB117)
    private String siteLanguageType;
}
