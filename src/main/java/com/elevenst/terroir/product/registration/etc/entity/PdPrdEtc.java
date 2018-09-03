package com.elevenst.terroir.product.registration.etc.entity;

import lombok.Data;

@Data
public class PdPrdEtc {
    private long    prdNo;           					//상품번호
    private String  prdSvcBgnDy;							//첫출발일 (서비스시작일)
    private String  prdSvcEndDy;							//마지막 출발일 (서비스 종료일)
    private String  prdDetailOutLink;				//상품상세 외부 링크
    private long    createNo;		/*생성자번호*/
    private String  createDt;	/*생성일시*/
    private long    updateNo;		/*수정자번호*/
    private String  updateDt;	/*수정일시*/

    //ADD (2011.10.10. 쇼셜커머스 상품에 대한  기준판매량과 현재판매량에 대한 값 insert or update)
    private long    standardSalesQty;		/*기준판매량*/
    private long    salesQty; 				/*현재판매량*/

    private String  seatTyp;				/* 좌석형태, Y:지정석상품, N:비지정석상품 */
    private String  theaterNm;			/* 공연장명 */
    private String  theaterAreaInfo;	/* 공연장지역정보 */
    private String  authorInfo;			//저자정보
    private String  transInfo;			//역사정보
    private String  picInfo;				//그림정보
    private String  isbn13Cd;			//ISBN-13 코드
    private String  isbn10Cd;			//ISBN-10 코드
    private String  bookSize;			//사이즈
    private String  bookPage;			//페이지
    private String  compInfo;			//구성/수량
    private String  preVwCd;				//미리보기 코드
    /**HMALL 상품 동영상 재생 타입
     * IExplore : IE
     * Safari : SF
     * Opera : OP
     * Fire Fox : FF
     * Chrome : CR
     */
    private String  vodBrowserTyp;

    /** HMALL 상품 동영상 URL  */
    private String  vodUrl;
    /** 사은품 대상번호1(제조사) */
    private String  freeGiftTgNo1;		//사은품 대상번호1(제조사)

    /** 사은품 대상번호2(공급처) */
    private String  freeGiftTgNo2;		//사은품 대상번호2(공급처)
    /** 도서 원제*/
    private String  mainTitle;

    /** 음반 라벨*/
    private String  mudvdLabel;
    /* e쿠폰 개편 추가 */
    private String  issuCoLogoUrl;		//발행회사로고URL
    private String  pntCupnNo;

}
