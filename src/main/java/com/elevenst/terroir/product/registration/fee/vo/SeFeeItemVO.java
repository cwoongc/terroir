package com.elevenst.terroir.product.registration.fee.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SeFeeItemVO implements Serializable {
    private static final long serialVersionUID = -6234331754025605962L;

    private String feeItmNo = "";               // 수수료 항목 번호
    private String feeItmNm = "";               // 수수료 항목명
    private String aplBgnDy = "";               // 적용 시작 일자
    private String aplEndDy = "";               // 적용 종료 일자
    private String feeKdCd = "";                // 수수료 종류 코드 (SE011)
    private String feeTypCd = "";               // 수수료 유형 코드 (SE012)
    private String feeTypCdNm = "";             // 수수료 유형 코드명 (SE012)
    private String feeAplUnitCd = "";           // 수수료 적용 단위 코드 (SE013)
    private String fee = "";                    // 수수료
    private String addPrdFee = "";              // 추가구성상품수수료
    private String frcEndYn = "";               // 강제 종료 여부
    private String feeItmDesc = "";             // 수수료 항목 설명
    private String rngUseYn = "";               // 구간 사용 여부
    private String prgrsAplYn = "";             // 누진제 적용 여부
    private String selFeeAplObjClfCd = "";      // 판매 수수료 적용 대상 구분
    private String selFeeAplObjNo = "";         // 판매 수수료 적용 대상 번호
    private String dispCtgrNo = "";             // 전시 카테고리 번호
    private String selMthdCd = "";              // 판매 방식 코드
    private String basiSelFeeYn = "";           // 기본 수수료인지 YN
    private String createDt = "";               // 등록 일시
    private String createNo = "";               // 등록자 번호
    private String updateDt = "";               // 수정 일시
    private String updateNo = "";               // 수정자 번호
    private String requestDt = "";				// 요청 일시
    private String requestNo = "";				// 요청자 번호
    private String aprvStatCd = "";				// 승인 상태
    private String approveDt = "";				// 승인 일시
    private String approveNo = "";				// 승인자 번호
    private String rejectDt	= "";				// 거부 일시
    private String rejectNo = "";				// 거부자 번호

    // 판매자별 카테고리 수수료 조정시 셀러아이디가 들어오는 경우 사용함
    private String sellerId = "";               // 셀러아이디 - 엑셀업로딩에서 넘어옴

    // 히스토리 저장시 사용 변수들
    private String dmlTypCd = "";               // I/U/D 삽입/갱신/삭제 코드
    private String histOccrDt = "";				// 히스토리 저장일시

    /**
     * 검색일시
     * 데이터를 셋팅하고 쿼리를 돌리면 일자에 해당하는 수수료를 가져올 수 있음.
     * ex) String today = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMMddHHmmss");
     */
    private String targetDate = "";

    /**
     * 판매 수수료 조회시 필요함.
     */
    private String memNo = "";          // 회원번호
    private long prdNo = 0;      //상품 번호

    private long dispCtgr1No = 0;    // 대카테고리 번호
    private long dispCtgr2No = 0;       // 전시 중 카테고리 번호
    private long dispCtgr3No = 0;       // 전시 소 카테고리 번호
    private String chinaYn   = "";              // 중국 수수료  여부
    private long selPrc = 0;				    // 상품판매가격

    private String updateNm = "";       // 수정자 이름 (emp_nm)

    private String isPaging = "";       // 페이징 여부
    private String startNum = "";       // 페이징 ( 시작 번호 )
    private String endNum = "";         // 페이징 ( 끝 번호 )
    private int limit;
    private int start;
    private int end;

    private String sortCol = "";        // 정렬

    //private SaleFeeJoinBO saleFeeJoinBO = null;

    private String feeTypCdLike = "";               // 수수료 유형 코드(SE012) Like 검색시 필요

    //private List<SeFeeAplRngBO> seFeeAplRngBOList = new ArrayList(); // 구간적용된경우 구간에 대한 정보리스트

    private String sellerAgreeYN;
    private String consultNo;
    private String prolAgreeYn;

    private long selMnbdNo;
}
