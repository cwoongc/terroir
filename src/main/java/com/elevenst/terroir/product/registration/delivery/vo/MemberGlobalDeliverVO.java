package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberGlobalDeliverVO implements Serializable {
    private static final long serialVersionUID = 7341197128106112982L;

    private long memNo;					// 판매자 번호
    private Date histDt;						// 변경일시
    private String gblDlvYn;				// 전세계 배송 가능여부 Y / N
    private float feeRt; 					// 수수료
    private String feeApplyDt; 			// 수수료 반영일시
    private Date createDt;					// 등록일시
    private long createNo;					// 등록자 번호
    private Date updateDt;				// 수정일시
    private long updateNo;				// 수정자 번호
    private String memId;					// 수정자 ID
    private String memNm;					// 수정자 이름
    private String fromBO;					// 수출대행 수수료 변경 가능 여부(BO 에서만 변경 가능)
    private String originConvertYn;		// 기존 마이그레이션 동의여부
    private String convertYn;				// 마이그레이션 동의여부
    private Date convertAgreeDt;		// 마이그레이션 동의일시
    private Date convertDt;				// 마이그레이션 실행일시
    private Date convertMailAgreeDt;	// 마이그레이션 메일 동의일시
    private Date feeApplyEndDt;			// 수수료 반영 종료일
    private String cnSelAgreeYn;			// 중문판매동의여부
    private Date cnSelAgreeChgDt;		// 중문판매동의여부 변경일시
    private Date gblDlvYnChgDt;			// 전세계배송 동의 변경 신청 일시
}
