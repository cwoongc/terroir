package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrd implements Serializable {

    private static final long serialVersionUID = 4676144602433566796L;

    //01. 상품번호
    private long prdNo = 0;
    //02. 마스터상품번호
    private long mstrPrdNo = 0;
    //03. 판매주체번호
    private long selMnbdNo = 0;
    //04. 판매주체닉네임일련번호
    private long selMnbdNckNmSeq = 0;
    //05. 상품자동진행번호
    private long prdAutoPrcsNo = 0;
    //06. 상품번호
    private String prdNm = "";
    //07. 홍보문구
    private String advrtStmt = "";
    //08. 원산지유형코드(PD003)
    private String orgnTypCd = "";
    //09. 원산지명
    private String orgnNm = "";
    //10. 판매기간구분코드(PD029)
    private String selPrdClfCd = "";
    //11. 판매시작일시
    private String selBgnDy = "";
    //12. 판매종료일시
    private String selEndDy = "";
    //13. 판매주체구분코드(PD011)
    private String selMnbdClfCd = "";
    //14. 판매방식코드 (PD012)
    private String selMthdCd = "";
    //15. 상품구성형태코드 (PD013)
    private String prdCompTypCd = "";
    //16. 판매상태코드 (PD014)
    private String selStatCd = "";
    //17. 배송가능지역코드 (PD015)
    private String dlvCnAreaCd = "01";
    //18. 배송방식코드 (PD016)
    private String dlvWyCd = "05";
    //19. 배송비설정기준코드 (PD005)
    private String dlvCstInstBasiCd = "01";
    //20. 배송비
    private long dlvCst = 0;
    //21. 배송비지불유형코드 (PD017)
    private String dlvCstPayTypCd = "01";
    //22. 반품배송비
    private long rtngdDlvCst = 0;
    //23. 교환배송비
    private long exchDlvCst = 0;
    //24. 묶음배송가능여부
    private String bndlDlvCnYn = "";
    //25. 당일배송가능여부
    private String todayDlvCnYn = "";
    //26. 지정일배송가능여부
    private String appmtDyDlvCnYn = "";
    //27. 발송예정기한
    private long sndPlnTrm = 0;
    //28. 세금계산서발행가능여부
    private String rcptIsuCnYn = "";
    //29. 상품구분코드 (PD018)
    private String prdTypCd = "";
    //30. 미성년자구매가능여부
    private String minorSelCnYn = "";
    //31. 승인필요여부
    private String aprvNessYn = "";
    //32. 제조일자
    private String mnfcDy = "";
    //33. 유효일자
    private String eftvDy = "";
    //34. 상품상태코드 (PD019)
    private String prdStatCd = "";
    //35. 부가면세상품구분코드 (PD020)
    private String suplDtyfrPrdClfCd = "";
    //36. 판매자상품코드
    private String sellerPrdCd = "";
    //37. 통합배송가능여부
    private String unDlvCnYn = "";
    //38. 무선상품명
    private String wirelssPrdNm = "";
    //39. 무선상품상세설명
    private String wirelssPrdDtlDesc = "";
    //40. 흥정거래상품여
    private String bagrnSelPrdYn = "";
    //41. 등록일시
    private String createDt = "";
    //42. 등록자번호
    private long createNo = 0;
    //43. 수정일시
    private String updateDt = "";
    //44. 수정자번호
    private long updateNo = 0;
    //45. 등록IP
    private String createIp = "";
    //46. 수정IP
    private String updateIp = "";
    //판매자 PC ID
    private String sellerPcId = "";
    //48. 가격비교사이트 등록 여부
    private String prcCmpExpYn = "";
    //49. 평균배송일
    private long avgDlvDy = 0;
    //50. 총 판매된 수량
    private long totSelQty = 0;
    //51. 해외쇼핑 상품 여부
    private String abrdBrandYn = "";
    //52. 해외쇼핑 상품 구입처 코드 (A,B,C,D,E)
    private String abrdBuyPlace = "";
    //53. 전시카테고리번호
    private long dispCtgrNoDe = 0;
    //54. 전시대카테고리번호
    private long dispCtgr1NoDe = 0;
    //55. 전시중카테고리번호
    private long dispCtgr2NoDe = 0;
    //56. 전시소카테고리번호
    private long dispCtgr3NoDe = 0;
    //57. 전시세카테고리번호
    private long dispCtgr4NoDe = 0;
    //58. 전시여부
    private String displayYn = "";
    //59. 등록형태 (PD052)
    private String createCd = "";
    //60. 중국판매여부
    private String chinaSaleYn = "";
    //61. 중국 판매가격
    private long chinaSelPrc = 0;
    //62. 판매제한구분코드 (PD053)
    private String selLimitTypCd = "";
    //63. 판매제한수량
    private long selLimitQty = 0;
    //64. e-최저가보상 제외 여부(Y:제외)
    private String lowPrcCompExYn = "";
    //65. 포인트선할인 제외 여부(Y:제외
    private String ctgrPntPreExYn = "";
    //66. 마스터상품 매칭한 주체 구분: 01(판매자), 02(운영자), 03(제휴사)
    private String mstrPrdMatchClfCd = "";
    //67. 원산지상세코드 (??)
    private String orgnTypDtlsCd = "";
    //68. 원산지가다른상품여부
    private String orgnDifferentYn = "";
    //69. 해당상품별로 주문완료에서 추가 정보를 처리하는 PAGE URL
    private String orderCmltOutLink = "";
    //70. 01:MO 일괄쿠폰적용제외, 02:모든코폰제외, null:제외없음(일괄쿠폰:카테고리, 판매자, 기획자 쿠폰)
    private String cupnExCd = "";
    //71. 해당상품별로 MYPAGE에서 추가로 정보를 처리하는 PAGE URL
    private String mypageOutLink = "";
    //72. 배송비 선결재 불가(착불)일 경우 배송비 참고 정보
    private String dlvCstInfoCd = "";
    //73. 셀링툴 업체 구분코드(PD054)
    private String sellerAgencyCd = "";
    //74. 핸드폰 1월 등록 승인 요청 여부[승인요청(Y), 승인요청안함(N, NULL)]
    private String mobile1wonYn = "";
    //75. 장바구니사용불가여부(Y:사용불가, N or NULL 사용)
    private String bcktExYn = "";
    //76. 상품무료배송기준금액
    private long prdFrDlvBasiAmt = 0;
    //77. 상품리뷰/후기 전시여부
    private String reviewDispYn = "";
    //78. [TR104]주문취소가능단계(주문 상태에 따라 각 단계별 취소코드)
    private String ordCnStepCd = "";
    //79. 판매최소제한 구분코드
    private String selMinLimitTypCd = "";
    //80. 판매최소제한 수량
    private long selMinLimitQty = 0;
    //81. 해외 사이즈조건표 노출여부
    private String abrdSizetableDispYn = "";
    //82. 방문수령 여부
    private String visitDlvYn = "";
    //83. 거래유형코드(01:수수료,02:직매입,03:특정매입)
    private String bsnDealClf = "";
    //84. 배송유형코드(01:자체배송,02:업체배송,03:해외배송,04:셀러위탁배송) (PD090)
    private String dlvClf = "";
    //85. 해외 취소 배송비
    private long abrdCnDlvCst = 0;
    //86. 11번가 해외 입고 유형 [WM002] (01:무료픽업,02:판매자발송,03:구매대행)
    private String abrdInCd = "";
    //87. 상품 무게
    private long prdWght = 0;
    //88. 제주 추가 배송비
    private long jejuDlvCst = 0;
    //89. 도서산간 추가 배송비
    private long islandDlvCst = 0;
    //90. 상품리뷰후기 옵션 노출여부
    private String reviewOptDispYn = "";
    //91. SO/BO 상품관리 리스트 노출 숨기기 상태(Y:숨김, N or NULL:노출)
    private String hideYn = "";
    //92. SP_MAKE_CO_LIVE_UPD 제휴사 배치 - 임시테이블
    private String bookAddCd = "";
    //93. 해외 상품명
    private String fdPrdNm = "";
    //94. BO 19금 설정여부 조사 Y/N
    private String minorSelCnBoYn = "";
    //95. 해외 구매대행 상품 구분(PD099)
    private String forAbrdBuyClf = "";
    //96. 원재료 유형 코드 (PD098)
    private String rmaterialTypCd = "";
    //97. 사용개발수
    private long useMon = 0;   //원래 있던 쿼리에 로직 녹여서 만들것
    //98. 관세HS코드
    private String hsCode = "";
    //99. OM상품여부 [OM상품(Y or Null), OM상품아님(N)]
    private String omPrdYn = "";
    //100. 상품서비스영역코드 (PD109)
    private String svcAreaCd = "";
    //101. 반품교환배송비 참조코드(PD111)
    private String rtngdDlvCd = "";
    //102. 상품템플릿여부
    private String templateYn = "";
    //103. 사용종료일시
    private String eftvEndDy = "";  //값처리 제대로 해줄것 daomap 쿼리에 로직 참고
    //104. 사용제한구분코드(PD113)
    private String townLmtClfCd = "";
    //105. 상점번호
    private long shopNo = 0;
    //106. 전세계배송여부
    private String gblDlvYn = "";
    //107. 전세계관세코드순번
    private long gblHsCodeNo = 0;
    //108. 생산지국가번호
    private long ntNo = 0;
    //109. 영문 상품명
    private String prdNmEng = "";
    //110. 영문 사이트 노출여부
    private String engDispYn = "";
    //111. 상품정보제공 유형 카테고리 번호
    private long infoTypeCtgrNo = 0;
    //112. 제휴추가할인 적용 제외 여부
    private String partnerCupnExYn = "";
    //113. 최저가할인 적용 제외 여부
    private String lpCupnExYn = "";
    //114. 옵션 구분 모두 (PD141)
    private String optTypCd = "";
    //115.  무형상품 환불 타입 (PD0145)
    private String rfndTypCd = "";
    //116. 최대구매수량 제한기간
    private long selLimitTerm = 0;
    //117. 세트타입코드(PD202)
    private String setTypCd = "";
    //118. 발급푸폰제외코드(PD251)
    private String issueCupnExptCd = "";
    //119. 표준상품명
    private String stdPrdNm = "";
    //120. 홍보상품명
    private String mktPrdNm = "";
    //121. 표준상품여부
    private String stdPrdYn = "";
    //122. 표준옵션그룹번호
    private long stdOptGrpNo = 0;


    private String histAplBgnDt = "";

}
