package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberSprtVO implements Serializable {
    private static final long serialVersionUID = 7100048308662197265L;

    private long memNO;            // 회원 번호
    private String memID;           // 회원 ID
    private String memNM;           // 회원명 or 사업체명
    private String mbEngNm;          // 회원 영문명
    private String memScrtNO;       // 비밀번호
    private String gnrlTlphnNO;     // 전화번호
    private String prtblTlphnNO;    // 휴대폰번호
    private String email;           // 이메일
    private String emailAddrCD;     // 이메일 코드
    private String drctMailAddr;    // 직접입력 이메일 도메인
    private String concatEmail;     // 이메일 주소
    private Date bgnEntDY;          // 회원 가입일
    private Date intUpdateDT;    	 // 통합서비스번호 업데이트 일시
    private Date realNMCrtfDY;      // 실명인증 일시
    private String realNMCrtfTyp;   // 실명인증 수단
    private String intSvcNO;       // 통합서비스번호
}
