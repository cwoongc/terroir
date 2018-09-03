package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

@Data
public class RentalVO {
    private String cstTermCd;                   // 비용기간코드(PD212)
    private String freeInstYn = "";             // 무료설치여부
    private String coCardBnft = "";             // 제휴카드혜택
    private String instCstDesc = "";            // 설치비용설명
    private String frgftDesc = "";              // 사은품정보
    private String etcInfo = "";                // 기타정보
    private String useSpecialRentalYn = "";     // 스페셜렌탈사용여부

    /*스페셜 렌탈 상품 추가 정보*/
    private String rntlPrdYn;

    private Long prdNo;
    private long rntlCst;
    private String rntlTermStr;
    private String rntlCstUntStr;
    private String rntlTermValue;
    private String useYn;	// 렌탈 스폐셜 정보 사용여부
    private long minRntlCst;
    private long maxRntlCst;
}
