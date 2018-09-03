package com.elevenst.terroir.product.registration.product.vo;

import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvLmtQtyVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvSchdlInfoVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdSvcCanRgnVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -7182740834081448820L;

    //상품자동진행번호
    private long prdAutoPrcsNo = 0;
    // 홍보문구
    private String advrtStmt = "";
    // 원산지유형코드(PD003)
    private String orgnTypCd = "";
    // 원산지명
    private String orgnNm = "";
    //판매기간 입력여부
    private String selTermUseYn = "";
    // 판매기간구분코드(PD029)
    private String selPrdClfCd = "";
    // 판매시작일시
    private String selBgnDy = "";
    // 판매종료일시
    private String selEndDy = "";
    // 판매주체구분코드(PD011)
    private String selMnbdClfCd = "";
    // 판매방식코드 (PD012)
    private String selMthdCd = "";
    private String selMthdNm = "";
    // 상품구성형태코드 (PD013)
    private String prdCompTypCd = "";
    // 판매상태코드 (PD014) ->>> ?????
    private String selStatCd = "";
    // 배송가능지역코드 (PD015)
    private String dlvCnAreaCd = "01";
    // 배송방식코드 (PD016)
    private String dlvWyCd = "05";
    // 배송비설정기준코드 (PD005)
    private String dlvCstInstBasiCd = "01";
    // 배송비
    private long dlvCst = 0;
    // 배송비지불유형코드 (PD017)
    private String dlvCstPayTypCd = "01";
    // 반품배송비
    private long rtngdDlvCst = 0;
    // 교환배송비
    private long exchDlvCst = 0;
    // 묶음배송가능여부
    private String bndlDlvCnYn = "";
    // 당일배송가능여부
    private String todayDlvCnYn = "N";
    // 지정일배송가능여부
    private String appmtDyDlvCnYn = "N";
    // 발송예정기한
    private long sndPlnTrm = 0;
    // 세금계산서발행가능여부
    private String rcptIsuCnYn = "";
    // 상품구분코드 (PD018) -->>>>>> ??
    private String prdTypCd = "";
    // 미성년자구매가능여부
    private String minorSelCnYn = "";
    // 승인필요여부
    private String aprvNessYn = "N";
    // 제조일자
    private String mnfcDy = "";
    // 유효일자(타운(PIN)의 사용시작일)
    private String eftvDy = "";
    // 상품상태코드 (PD019)
    private String prdStatCd = "";
    // 부가면세상품구분코드 (PD020)
    private String suplDtyfrPrdClfCd = "";
    // 판매자상품코드
    private String sellerPrdCd = "";
    // 통합배송가능여부
    private String unDlvCnYn = "N";
    // 무선상품명
    private String wirelssPrdNm = "";
    // 무선상품상세설명
    private String wirelssPrdDtlDesc = "";
    // 흥정거래상품여부
    private String bagrnSelPrdYn = "N";
    // 등록IP
    private String createIp = "";
    // 수정IP
    private String updateIp = "";
    //판매자 PC ID
    private String sellerPcId = "";
    // 가격비교사이트 등록 여부
    private String prcCmpExpYn = "";
    // 평균배송일
    private long avgDlvDy;
    // 총 판매된 수량
    private long totSelQty = 0;
    // 해외쇼핑 상품 여부
    private String abrdBrandYn = "";
    // 해외쇼핑 상품 구입처 코드 (A,B,C,D,E)
    private String abrdBuyPlace = "";
    // 전시대카테고리번호
    private long dispCtgr1NoDe = 0;
    // 전시중카테고리번호
    private long dispCtgr2NoDe = 0;
    // 전시소카테고리번호
    private long dispCtgr3NoDe = 0;
    // 전시세카테고리번호
    private long dispCtgr4NoDe = 0;
    // 전시여부
    private String displayYn = "Y";
    // 등록형태 (PD052)
    private String createCd = "";
    // 중국판매여부
    private String chinaSaleYn = "";
    // 중국 판매가격
    private long chinaSelPrc = 0;
    // 판매제한구분코드 (PD053)
    private String selLimitTypCd = "";
    // 판매제한수량
    private long selLimitQty = 0;
    // e-최저가보상 제외 여부(Y:제외)
    private String lowPrcCompExYn = "N";
    // 포인트선할인 제외 여부(Y:제외
    private String ctgrPntPreExYn = "N";
    // 마스터상품 매칭한 주체 구분: 01(판매자), 02(운영자), 03(제휴사)
    private String mstrPrdMatchClfCd = "";
    // 원산지상세코드 (??)
    private String orgnTypDtlsCd = "";      // 두번째 selectbox 원산지 정보
    private String orgnTypDtlsCd1 = "";     // 첫번째 selectbox 원산지 정보
    // 원산지가다른상품여부
    private String orgnDifferentYn = "";
    // 원산지 상세지역 선택
    private String useOrgnDtlYn = "";
    // 해당상품별로 주문완료에서 추가 정보를 처리하는 PAGE URL
    private String orderCmltOutLink = "";
    // 01:MO 일괄쿠폰적용제외, 02:모든코폰제외, null:제외없음(일괄쿠폰:카테고리, 판매자, 기획자 쿠폰)
    private String cupnExCd = "";
    // 해당상품별로 MYPAGE에서 추가로 정보를 처리하는 PAGE URL
    private String mypageOutLink = "";
    // 배송비 선결재 불가(착불)일 경우 배송비 참고 정보
    private String dlvCstInfoCd = "";
    // 셀링툴 업체 구분코드(PD054)
    private String sellerAgencyCd = "";
    // 핸드폰 1월 등록 승인 요청 여부[승인요청(Y), 승인요청안함(N, NULL)]
    private String mobile1wonYn = "";
    // 장바구니사용불가여부(Y:사용불가, N or NULL 사용)
    private String bcktExYn = "N";
    // 상품무료배송기준금액
    private long prdFrDlvBasiAmt = 0;
    // 상품리뷰/후기 전시여부
    private String reviewDispYn = "";
    // [TR104]주문취소가능단계(주문 상태에 따라 각 단계별 취소코드)
    private String ordCnStepCd = "";
    // 판매최소제한 구분코드
    private String selMinLimitTypCd = "";
    // 판매최소제한 수량
    private long selMinLimitQty = 0;
    // 해외 사이즈조건표 노출여부
    private String abrdSizetableDispYn = "";
    // 방문수령 여부
    private String visitDlvYn = "";
    // 거래유형코드(01:수수료,02:직매입,03:특정매입)
    private String bsnDealClf = "";
    // 배송유형코드(01:자체배송,02:업체배송) (PD090)
    private String dlvClf = "";
    // 해외 취소 배송비
    private long abrdCnDlvCst = 0;
    // 11번가 해외 입고 유형 [WM002] (01:무료픽업,02:판매자발송,03:구매대행)
    private String abrdInCd = "";
    // 상품 무게
    private long prdWght = 0;
    // 제주 추가 배송비
    private long jejuDlvCst = 0;
    // 도서산간 추가 배송비
    private long islandDlvCst = 0;
    private String islandUseYn = "N"; // 제주/도서산간 체크박스
    // 상품리뷰후기 옵션 노출여부
    private String reviewOptDispYn = "Y";
    // SO/BO 상품관리 리스트 노출 숨기기 상태(Y:숨김, N or NULL:노출)
    private String hideYn = "";
    // SP_MAKE_CO_LIVE_UPD 제휴사 배치 - 임시테이블
    private String bookAddCd = "";
    // 해외 상품명
    private String fdPrdNm = "";
    // BO 19금 설정여부 조사 Y/N
    private String minorSelCnBoYn = "";
    // 해외 구매대행 상품 구분(PD099)
    private String forAbrdBuyClf = "01";
    // 원재료 유형 코드 (PD098)
    private String rmaterialTypCd = "";
    // 사용개발수
    private long useMon = 0;   //원래 있던 쿼리에 로직 녹여서 만들것
    // 관세HS코드
    private String hsCode = "";
    // OM상품여부 [OM상품(Y or Null), OM상품아님(N)]
    private String omPrdYn = "Y";
    // 상품서비스영역코드 (PD109)
    private String svcAreaCd = "";
    // 반품교환배송비 참조코드(PD111)
    private String rtngdDlvCd = "";
    // 상품템플릿여부
    private String templateYn = "";
    // 사용종료일시(타운(PIN)의 사용종료일)
    private String eftvEndDy = "";  //값처리 제대로 해줄것 daomap 쿼리에 로직 참고
    // 사용제한구분코드(PD113)
    private String townLmtClfCd = "";
    // 상점번호
    private long shopNo = 0;
    // 전세계배송여부
    private String gblDlvYn = "N";
    // 전세계관세코드순번
    private long gblHsCodeNo = 0;
    //전세계관세코드
    private String gblHsCode = "";
    // 생산지국가번호
    private long ntNo = 0;
    // 영문 상품명
    private String prdNmEng = "";
    // 영문 사이트 노출여부
    private String engDispYn = "";
    // 상품정보제공 유형 카테고리 번호
    private long infoTypeCtgrNo = 0;
    // 제휴추가할인 적용 제외 여부
    private String partnerCupnExYn = "";
    // 최저가할인 적용 제외 여부
    private String lpCupnExYn = "";
    // 옵션 구분 모두 (PD141)
    private String optTypCd = "";
    // 무형상품 환불 타입 (PD0145)
    private String rfndTypCd = "";
    // 최대구매수량 제한기간
    private long selLimitTerm = 0;
    // 세트타입코드(PD202)
    private String setTypCd = "";
    // 발급푸폰제외코드(PD251)
    private String issueCupnExptCd = "";
    // 표준상품명
    private String stdPrdNm = "";
    // 홍보상품명
    private String mktPrdNm = "";
    // 표준상품여부
    private String stdPrdYn = "";
    // 표준옵션그룹번호
    private long stdOptGrpNo = 0;
    // 영문상품명
    private String sellerPrdNmEng = "";
    // 중문상품명
    private String sellerPrdNmCn = "";



    //기본즉시할인 적용여부
//    private String cuponCheckYn = "N";
    //기본즉시할인 수치(원 혹은 %)
//    private String dscAmtPercnt = "";
    //기본즉시할인 수치(01:원, 02:%)
//    private String cupnDscMthdCd = "";
    // 기본즉시할인기간 설정여부
//    private String	cupnUseLmtDyYn = "N";
    // 기본즉시할인 시작일
//    private String	cupnIssStartDy = "";
    // 기본즉시할인 종료일
//    private String	cupnIssEndDy = "";

    //상품태그 사용여부
    private String productTagUseYn = "";



    private boolean isQCVerifiedStat;












    /////애매한 녀석들 일단 임시 저장 공간
    private long avgDeliDays;
    private long employeeNo;		// 사용자번호(담당MD)
    private String employee;		// 담당MD명
    private String frCtrCd;         // 해외 명품직매입 , 해외주문국가코드

    private long globalInAddrSeq;
    private long globalOutAddrSeq;
    private long inAddrSeq;
    private long outAddrSeq;
    private long visitAddrSeq;
    private long inMemNo;
    private long outMemNo;
    private String globalInMbAddrLocation;
    private String globalOutMbAddrLocation;
    private String inMbAddrLocation;
    private String outMbAddrLocation;
    private String visitMbAddrLocation;

    private String[] topStyle;		// TOP STYLE 코너
//    private String topStyleDispCd;	// TOP STYLE 코너 전시상태

    private String certTypCd;
    private String prdInfoType;
    private long templatePrdNo;
    private long reRegPrdNo;
    private String selTermDy;
    private String itemInfo; // 전시아이템 정보 - 일괄적용에서 그리드에 보여주기 위해 사용됨
    //tour
    private String tourSidoSelectInfo;
    private String sigunguSelectInfoID;
    //town
    private String certDownYn;  //외부 인증 사용여부
    private String townSellerCertType;
    private long frPrdNo;
    private long mstrPrdNo;
    private String allBranchUseYn;
    private long townSelLmtDy = 0;  //--> PD_PRD_SVC_AREA
    // 쇼킹딜 판매중
    private long pdEventCnt;
    // 쇼킹딜 확정완료 상태
    private long evtConfirmCnt;
    private long prdStckQty;

    private long modelNo;
    private String stdBaseDt;
    private String brandCd;
    private String brandNm;
    private String modelNm;
    private String statChgRsn;
    private String certType;

    private String dlvCstInstBasiNm;
    private String usedMnfcDyYn;

    private AgencyVO agencyVO;

    //생활플러스
    private String svcCnAreaRgnClfCd;
    private List<PdSvcCanRgnVO> svcCanRgnVOList;
    private String rsvSchdlModifyYn;
    private List<PdRsvLmtQtyVO> pdRsvLmtQtyVOList;
    private List<PdRsvSchdlInfoVO> pdRsvSchdlInfoVOList;
}
