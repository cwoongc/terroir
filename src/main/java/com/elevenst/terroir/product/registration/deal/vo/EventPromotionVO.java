package com.elevenst.terroir.product.registration.deal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventPromotionVO implements Serializable {

    private static final long serialVersionUID = -5901076158499018748L;
    private long eventPrmtNo;
    private String eventGb;
    private String eventGbNm;
    private String eventPrmtNm;
    private String eventPrmtDesc;
    private String eventPrmtUrl;
    private String recvBgnDt;
    private String recvEndDt;
    private String eventBgnDt;
    private String eventEndDt;
    private String premiumAplYn;
    private String soRngCd;
    private String soRngNm;
    private long soCostDfrmRt;
    private long moCostDfrmRt;
    private long ptnrDvRt;
    private long cupnNo;
    private String eventStatCd;
    private String eventStatNm;
    private String useYn;
    private String createDt;
    private long createNo;
    private String createNm;
    private String createId;
    private String updateDt;
    private long updateNo;
    private long memNo;
    private String sellerAplYn;
    private String eventSchemeYn;
    private String prmtApl;
    private long dispCtgr1No;

    private long totalCount;
    private int start = 1;
    private int end = 11;
    private int limit = 10;

    // 검색시 사용되는 파라미터 변수
    private String selectFromDt;
    private String selectToDt;
    private String progressStat;
    private String dateType;
    private String bgnDt;
    private String endDt;
    private long num;
    private String data;

    //프로모션연장신청 관련 추가
    private String prdAplCd;
    private String prdAplCdNm;
    private String remCnt;	//남은 횟수
    private String prdAplYn; //SO특정상품 신청가능여부
    private long soSpcPrdCostDfrmRt;	//SO신청시 특정상품 판매자분담률
    private long adjSoCostDfrmRt;		// 판매자분담 프로모션 조정 분담율(so)
    private long aplNo;

    private String sellerId;
    private String sellerNm;
    private String acptDt;
    private long prdNo;
    private String prdNm;
    private int prdCnt;
    private long mdNo;
    private String mdNm;
    private String mdMail;
    private long plNo;
    private String plNm;

    private String plMail;
    private String dispYn;

    private String aprvStatCd;
    private String aprvStatNm;
    private String aplDt;;
    private String aplBgnDy;
    private String aplEndDy;
    private String prdSoCostDfrmRt;
    private String plnDispNo;
    private String eventGbExt;
    private String eventAutoIncrApl;
    private String cancelDt;

    private String rngYn;

    private int chnlBitNo = 0;
    private String drctVisitYn = null;
    private long eventGroupNo = 0;

    //특정셀러선택 수정시 Y:추가,N:삭제
    private String addDelSellerYn;


    //동일유형 상시운영 프로모션 유무 확인용 추가
    private String nextPrmtNm;
    private String prevPrmtNm;
    private long nextPrmtNo;
    private long prevPrmtNo;
    private String refererYn;

    private long nextEventPrmtNo;
    private String nextEventPrmtNm;
    private String nextAprvStatCd;
    private String nextPrmtStrtMonth;
    private long nextAplNo;
    private String nextPrdAplCd;
    private String nextPrdAplNm;
    private long availSpecialPrmtCount;
    private String rglrPrmtYn; // 정기프로모션 여부
    private String specialPrmtYn;
    private String progressStatNm;
    private String prdAplNm;
}
