package com.elevenst.terroir.product.registration.mobile.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MobileFeeSearchParamVO implements Serializable{
    /** 검색 구분 */
    private String searchClsf;

    /** 검색 카테고리 */
    private String searchCategory;

    /** 생성자 ID */
    private String createId;

    /** 그룹명 */
    private String grpNm;

    /** 사용여부 */
    private String useYn;

    /** 휴대폰 요금제 번호 */
    private long mpFeeNo;

    /** 페이징 정보 */
    private int start, limit, end;
}
