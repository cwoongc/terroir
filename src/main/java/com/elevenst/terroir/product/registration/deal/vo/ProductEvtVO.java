package com.elevenst.terroir.product.registration.deal.vo;

import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductEvtVO implements Serializable {

    private static final long serialVersionUID = -6804613035501383376L;
    private long aplNo;
    private String memId;
    private String createDt;
    private String createId;
    private String aplBgnDy;
    private String aplEndDy;
    private long soCostDfrmRt;
    private long moCostDfrmRt;
    private long ptnrDvRt;
    private String allPrdAplYn;
    private long memNo;
    private long createNo;
    private long updateNo;
    private long prdNo;
    private String prdNm;
    private List<Long> prdNoList;
    private String fnPdDscInfo;
    private long selPrc;
    private String ctgrPntPreRt;
    private long finalDscPrc;
    private String soMaxCupn;
    private String moMaxCupn;
    private String dateType;
    private long eventPrmtNo;
    private String eventPrmtNm;
    private String eventGbNm;
    private long prmtNo; // AD_DEAL_PRMT_MSTR.PRMT_NO 광고 프로모션 번호

    private String dlvCst;  //이벤트 배송비
    private String eventStat;  //배송비
    private String eventStatNm;  //상태명
    private String mdNo;  //담당MD
    private String eventSeq; // 순번
    private String[] eventSeqArr; // 이벤트순번
    private long prdDlvCst;  //기존 상품 배송비
    private String prdDlvInfo;	// 기존 상품 정보

    private long totalCount;
    private int start = 1;
    private int end = 11;
    private int limit = 10;

    private long dispCtgrNo;
    private long dispCtgr1No;
    private long dispCtgr2No;
    private String dispCtgrNm;
    private String dispCtgrPath;
    private String dispCtgrEngNm;
    private String eventRt = "0";
    private String eventSaveRt = "0";
    private long selMnbdNo;
    private String prdAplCd;
    private String eventGb;
    private String appType;
    private String eventDscAmt;
    private String eventDscRt;
    private String cupnDscMthdCd;
    private String dlvCstCd;
    private String aprvDesc; //반려사유

    private String mdNm;  //md 정보
    private String prmtEventEndDt; //prmt의 이벤트종료일
    private String selMthdCd; // 중고상품유무

    private String updateDt;
    private String updateNm;

    private String postSelStatCd; // 종료 후 판매상태
    private String aprvStatCd; //신청상태
    private String prdAddStatCd; // 쇼킹딜상품상태코드(PD136)
    private long autoAplNo; //자동참여번호
    private Integer reqCnt; //연장요청 횟수

    private long lowPrcEnuri; 	//에누리 최저가
    private long lowPrcDanawa; 	//다나와 최저가
    private long lowPrcNaver;	//네이버 최저가
    private long lowPrcAbout;	//어바웃 최저가
    private long lowPrcDaum;	//다아음 최저가

    private String lpYnEnuri;	//에누리가 최저가인지
    private String lpYnDanawa;	//다나와가 최저가인지
    private String lpYnNaver;	//네이버가 최저가인지
    private String lpYnAbout;	//어바웃이 최저가인지
    private String lpYnDaum;	//다아음이 최저가인지

    private double basicFee;		// 기준수수료율
    private double ShockingDealFee;	// 쇼킹딜수수료

    private String evlCnt;		//구매후기건수
    private long eventNo;		//이벤트번호
    private long eventGroupNo;  //이벤트그룹번호
    private int chnlBitNo;		//채널비트번호
    private String drctVisitYn;	//바로가기여부
    private String prmtExistYn; //프로모션 존재여부

    private long rqstQty; // 판매가능수량
    private String remark;// 비고

    //private NetSalesBO reqNetSalesBO;//요청 순매출 정보.
    //private NetSalesBO netSalesBO;   //순매출 정보.
    private long netSales; 			// 순매출액
    private double netSalesRt; 		// 순매출율
    private long reqNetSales; 		// (요청)순매출액
    private double reqNetSalesRt; 	// (요청)순매출율

    private int orderCancelQty; // 주문 취소/반품 된 상품건수
    private int orderQty; 		// 주문된 상품건수
    private double expextApprFee; // 예상수수료율
    private double expextAprvFee; // 예상수수료율

    private String eventBgnDt; // 프로모션 시작
    private String eventEndDt; // 프로모션 종료

    private double stdFeeSkDeal; //기준수수료쇼킹딜

    private String currFee;	//	현재 수수료

    private String reqYn;			//	쇼킹딜 신청 가능여부
    private String payAgreeYn; 	// 쇼킹딜 결제동의여부
    private String poSellerYn;	 	// PO 판매자 여부
    private String rwrdSpplYn; 	// 리워드 지급여부
    private long exptObjCnt;		// 쇼킹딜 진행비 제외대상 수
    private boolean isFeeApprPass; // 대형제휴사 쇼킹딜 수수료승인 패스

    //고정서비스비용
    private String fnSelFee;
    private String fixedSelFee;

    private EventPricePegVO eventPricePegVO;
    private ProductVO productVO;
    private EventPromotionVO eventPromotionVO;
    private EventRqstPrdInfoVO eventRqstPrdInfoVO;	// 쇼킹딜 상품정보
    private EventOrgPrdInfoVO eventOrgPrdInfoVO;	// 쇼킹딜 종료 후 상품정보

    private long shockDealFee;
    private String eventPrmtTyp;	//쇼킹딜 여부
    private int todaySoldOutCnt;	//쇼킹딜 오늘 품절상품수
    private int oneHourBgnPre; 		//쇼킹딜 시작 1시간전

    private String prdTypCd;

    private String officeGubun;

    private String movieStatCd;

    private String ptnrPrmtYn = "N";	// 제휴사 자체 프로모션 여부

    private double mywayEventRt = 0.0d;	// 내 맘대로 프로모션 할인율

    private String talkServiceYn;

    private String ptnrPrmtSellerYN;
    private String incrPayYn;
    private String apiYn;
    private String bssCont1;
    private String bssCont2;
    private String bssCont3;
    private String bssCont4;
    private String bssCont5;


    private String prolAfMntnYn;
    private String dlvClf;
    private String stlTypCd;

    private String cancelDate;
    private String shockDealPrdYn;
}
