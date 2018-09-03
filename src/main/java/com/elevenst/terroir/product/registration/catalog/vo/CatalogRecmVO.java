package com.elevenst.terroir.product.registration.catalog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CatalogRecmVO extends CatalogCmnVO implements Serializable {

    private static final long serialVersionUID = 5785111633227581151L;
    private long ctlgNo = 0;
    private long stockNo = 0;
    private long prdNo = 0;
    private long prdRecmCnt = 0;
    private long prdMatchCnt = 0;
    private long ctlgGrpNo = 0;
    private long matchNo = 0;
    private long selPrc = 0;
    private long recmCtlgNo = 0;
    private long dispCtgr1No = 0;
    private long dispCtgr2No = 0;
    private long dispCtgr3No = 0;
    private long dispCtgr4No = 0;
    private long score = 0;
    private long minPrc = 0;
    private long maxPrc = 0;
    private long avgPrc = 0;
    private long optAddPrc = 0;
    private long prrtRnk = 0;
    private long ctlgAttrNo = 0;
    private long ctlgAttrValueNo = 0;
    private double totzScore = 0.0d;
    private double srchScore = 0.0d;
    private String dispModelNm = null;
    private String imgPath = null;
    private String prdNm = null;
    private String optNm = null;
    private String createCdNm = null;
    private String prdGrpCd = null;
    private String prdRecmYn = null;
    private String prdMatchYn = null;
    private String memId = null;
    private String dispCtgrNm = null;
    private String memNm = null;
    private String actionTyp = null;
    private String stockNoParam = null;
    private String prdNoParam = null;
    private String matchChannelNm = null;
    private String aprvStatCd = null;
    private String aprvTypCd = null;
    private String selStatCdNm = null;
    private String mnbdClfCd = "03";
    private String dispTyp = null;
    private String ctlgSvcClf = "";			//카탈로그 서비스 구분 코드(PD188)
    private String ctlgTypCd = "";			//카탈로그 구분코드(PD185)
    private String selStatCd = null;
    private String categorySearchYn = null;
    private String regTypCd = null;
    private String brandSearchType = null;
    private String brandSearchStr = null;
    private String ctlgStatCd = null;
    private String ctlgStatCdNm = null;
    private String searchDateType = null;
    private String searchBgnDate = null;
    private String searchEndDate = null;
    private String prcInfo = null;
    private String indexType = null;
    private String matchClfCd = null;
    private String matchClfCdUpd = null;
    private String ctlgMatchYn = null;
    private String ctlgTypCdNm = null;
    private String subIndexType = null;
    private String searchMode = null;
    private String procTypParam = null;
    private String ctlgAttrNm = null;
    private String ctlgAttrValueNm = null;
    private String inputMthdCdNm = null;
    private String dispCtgr1Nm = null;
    private String dispCtgr2Nm = null;
    private String dispCtgr3Nm = null;
    private String dispCtgr4Nm = null;

    private long aprvComCnt = 0;		// 승인 건수
    private long aprvRejCnt = 0;		// 반려 건수
    private String isptNm = null;		// 검수자
    private String isptId = null;		// 검수자 아이디
    private String isptDt = null;		// 검수일
    private String selRqstYn = "";		// 셀러 요청 여부
    private String prdOrderYn = null;	// 주문 여부
    private String totzDate = null;
    private String partNum = null;
    private String minPrcYn = null;		// 최저가 여부

    private String rjctRsnCd = null;		// 반려사유코드 PD221
    private String rjctRsnCdParam = null;

    private String appModelCnt = null; // 승인건수
    private String rjtModelCnt = null; // 반려건수

    private String srchReqDtType = null; // 조회기간 조건
    private String srchSelectDt = null; // 조회기간 기간선택

    // [신규] 2016.09.28 검색조건추가:자동승인
    private String prdAutoMatchYn = null; // 자동승인 선택
    private long autoMatchCnt = 0; // 자동승인 매칭수
    private long autoMatchModelCnt = 0; // 승인 대상수
    private String completeDt = null; // 매칭 완료일
    private String matchObjNo = null; // 매칭 대상 번호
    private String useYn = null; // 사용 여부
    private long optMatchCnt = 0; // 옵션 매칭수

    // [신규] 2016.10.12 카탈로그 매칭 작업 및 카탈로그 매칭 작업 상세 등록 추가
    private long createNo = 0; // 등록자 번호
    private String createNoParam = null;

    //카탈로그 상품 매칭 검색 조건 추가 - 스마트 카탈로그
    private String includeSearchKwd = null;
    private String exceptSearchKwd = null;
    //카탈로그 상품 매칭 조회 컬럼 추가 - 스마트 카탈로그
    private String ctlgGrpNm = null;
    private String brandNm = "";

    //뷰타입 추가
    private String prdGrpNm = "";
    private String catalogImgPath = "";
    private String etcInfo = "";

    //카탈로그작업 이력
    private String ctlgNoParam = null;
    //스마트 카탈로그 매칭 구분
    private String matchTrtClfCd = null;
    //승인대상수 구분
    private String autoMatchModelYn = null;
    //뷰타입 간략, 상세 조회 구분
    private String viewType = null;
    private String prdExtTyp = null;
    private double ctlgRecmScore = 0.0d;
    private String ctlgLogicClfCd = "01";

    private List<Long> ctlgNoList = null;
}
