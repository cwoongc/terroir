package com.elevenst.terroir.product.registration.mobile.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class PdPrdMobileFee implements Serializable {
    /** 상품 휴대폰 요금제 번호 */
    private long prdMpFeeNo;

    /** 상품번호 */
    private long prdNo;

    /** 휴대폰 요금제 번호 */
    private long mpFeeNo;

    /** 약정 기간 코드 */
    private String contractTermCd;

    /** 단말가 */
    private long maktPrc;

    /** 대표 요금제 여부 */
    private String repFeeYn;

    /** 정열 순서 */
    private int prrtRnk;

    /** 생성자 번호 */
    private long createNo;

    /** 수정자 번호 */
    private long updateNo;

    /** 그룹명 */
    private String grpNm;

    /** 이력 시작일 */
    private String histAplBgnDt;

}
