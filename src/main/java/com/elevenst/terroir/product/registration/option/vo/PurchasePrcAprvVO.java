package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PurchasePrcAprvVO implements Serializable {

    private static final long serialVersionUID = -2973205334926860208L;
    private long prdPrcAprvNo;		// 가격승인대상번호
    private long prdNo;				// 상품번호
    private String prdNm;			// 상품명
    private String bsnDealClf;		// 거래유형
    private double basicMarginRate;  // 기준 마진율
    private double mrgnRt;			// 마진율
    private long mrgnAmt;			// 마진금액
    private long puchPrc;			// 공급가(매입가)
    private long selPrc;			// 판매가
    private long maktPrc;			// 시중가
    private double chgMrgnRt;		// 변경마진율
    private long chgMrgnAmt;		// 변경마진금액
    private long chgPuchPrc;		// 변경공급가(매입가)
    private long chgSelPrc;			// 변경판매가
    private String aplyBgnDy;		// 적용시작일
    private String aprvStatCd;		// 가격승인상태
    private String aprvStatRsn;		// 상태에 대한 사유내용
    private String cupnAplyYn;		// 쿠폰적용여부
    private String cupnDscMthdCd;	// 쿠폰할인방식코드
    private double cupnDscVal;		// 쿠폰할인금액또는율
    private String cupnIssMnbdClfCd;// 쿠폰발급주체구분코드
    private String cupnCostDfrmMnbdClfCd;	// 쿠폰비용부담주체구분코드
    private long moCostDfrmRt;		// MO비용부담비율
    private String issCnBgnDt;		// 발급가능시작일시
    private String issCnEndDt;		// 발급가능종료일시
    private String requestMnbdClfCd;// 가격승인요청주체구분코드
    private long requestNo;			// 요청자NO
    private long approveNo;			// 승인자NO
    private long updateNo;			// 수정자NO
    private String requestDt;		// 승인요청일
    private String requestDtHhmi;		// 승인요청일
    private String updateDt;		// 수정일
    private String approveDt;		// 승인일
    private String dscRt;			// 할인율 (시중가대비판매가)
    private String aprvStatNm;		// 승인상태명
    private String requestNm;		// 요청자
    private String approveNm;		// 승인자
    private String updateNm;		// 수정자
    private double netSales;		// 순매출율
    private String supplyCmId;		// 공급업체ID
    private String supplyCmNm;		// 공급업체명
    private long supplyCmNo;		// 공급업체번호
    private long totalCount;		// 전체 Row수
    private String[] chkPrdNo;
    private String[] chkPrdPrcAprvNo;
    private List<Long> prdNoList = new ArrayList<Long>();	// 상품번호 리스트
    private List<String> aplyBgnDyList = new ArrayList<String>();	// 적용일 리스트
    private List<String> requestDtList = null;		// 승인요청일 리스트
    private List<Long> prdPrcAprvNoList = new ArrayList<Long>();	// 가격승인대상번호 리스트
    private String svcUsePolicy;	// 마진정책
    private String fnPdPurDscInfo;	// 함수 변수
    private String soMaxCupn;		// SO즉시할인
    private String moMaxCupn;		// MO즉시할인
    private long finalDscPrc;		// 할인모음가
    private String selFee;			// 서비스이용료
    private String orgSales;		// 순매출
    private String ctgrPntPreRt;	// 선할인율
    private String OcbSaveRt;		// OK 캐쉬백적립율
    private String CtgrPntRt;		// 카테고리포인트적립율
    private String mobileYn;		//핸드폰카테고리 여부 (핸드폰카테고리의 경우 판매수량제한 없음)
    private long dscAmt;			//판매자할인금액(SO 즉시할인금액)
    private long mallPntAmt;		//판매자제공 포인트금액
    private long spplChipAmt;		//판매자제공 칩금액
    private String memFeeUpdateYn;  // 회원 수수료체계 동의 여부
    private String selMthdCd;		// 판매방식
    private String selStatCd;		// 판매상태
    private String selPrdClfCd;		// 판매기간
    private String dispCtgrPath;	// 카테고리
    private long dispCtgrNo;		// 세카
    private long dispCtgrNo1;		// 대카
    private long dispCtgrNo2;		// 중카
    private long dispCtgrNo3;		// 소카
    private String selBgnDy;		// 판매시작일
    private String selEndDy;		// 판매종료일
    private String createDt;		// 시작일
    private String createDtTo;		// 종료일
    private String dateType = "";	// 기간타입
    private String conditionType;	// 조건타입
    private int start = 1;			// row start
    private int end = 11;			// row end
    private int limit = 10;			// row limit
    private long selMnbdNo;			// 판매자 번호
    private double frSelPrc;		// 현지판매가
    private double chgFrSelPrc;		// 현지변경판매가
    private int skDealCnt;			// 쇼킹딜
    private String usePeriod;			// 사용기간
    private long shopNo;				// 상점번호
    private String shopNm;				// 상점명
    private long stockNo;			// 재고번호
    private String aprvObjCd;		// 가격승인대상(01:판매가, 02:옵션가)
    private String firstRqstYn;		// 최초신청여부
    private long addPrc;			//추가금액
    private String mixOptDtlsNm;//옵션값
    private List<Long> stockNoList = new ArrayList<Long>();	// 재고번호 리스트
    private String[] chkStockNo;
    private String changeType;
    private String aprvPrcStatCd;
    private long avgUntprc;			//일평균단가
    private long acctUntprc;		//월평균단가
}
