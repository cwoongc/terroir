package com.elevenst.terroir.product.registration.option.vo;

import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductStockVO implements Serializable {
    private static final long serialVersionUID = -7936430001444403990L;

    private long stockNo;
    private long prdNo;
    private long optItemNo1;
    private long optValueNo1;
    private String optValueNm1;
    private long optItemNo2;
    private long optValueNo2;
    private String optValueNm2;
    private long optItemNo3;
    private long optValueNo3;
    private String optValueNm3;
    private long optItemNo4;
    private long optValueNo4;
    private String optValueNm4;
    private long optItemNo5;
    private long optValueNo5;
    private String optValueNm5;
    private long optItemNo6;
    private long optValueNo6;
    private String optValueNm6;
    private long optItemNo7;
    private long optValueNo7;
    private String optValueNm7;
    private long addPrc;
    private long selQty;
    private long stckQty;
    private String prdStckStatCd;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private String prdSelStatCd;
    private String barCode;
    private int barCodeDupChkCnt;
    private long realStckQty;
    private long resvStckQty;
    private long tempStckQty;
    private long dfctStckQty;
    private long regRnk;
    private long optWght;
    private double cstmOptAplPrc;

    // 옵션적용시 재고중복 체크 키값을 위해 2010.05.17
    private String optItemNm1;
    private String optItemNm2;
    private String optItemNm3;
    private String optItemNm4;
    private String optItemNm5;
    private String optClfCd1;
    private String optClfCd2;
    private String optClfCd3;
    private String optClfCd4;
    private String optClfCd5;
    private long optItemCnt = 0;
    private long optValueCnt1 = 0;
    private long optValueCnt2 = 0;
    private long optValueCnt3 = 0;
    private long optValueCnt4 = 0;
    private long optValueCnt5 = 0;

    // 페이징처리시 필요 2010.12.08 모바일SO에서 사용됨
    private long totalCount; // 페이징때 사용되는 카운트
    private long start = -1; // -1로 초기화
    private long limit = -1; // -1로 초기화

    // 재고 증감 처리를 위해 필요한 필드들
    private String interfaceId;   // 물류에서 넘어오는 케이스는 각 프로세스구분이 필요함

    private long increaseQty;       // 가용재고 증감수량
    private long increaseRealQty;   // 정상창고 실재고 증감수량
    private long increaseResvQty;   // 예약재고 증감수량
    private long increaseTempQty;   // 불량창고 임의재고 증감수량
    private long increaseDfctQty;   // 불량창고 실재고 증감수량

    // 재고의 상품정보
    private String selStatCd;    // 상품의 판매상태코드
    private String bsnDealClf;    // 상품의 거래유형 (01:수수료상품, 02:직매입, 03:특정매입)

    private String histAplEndDt;    // 이력적용 종료일시
    private String chgType = "I";    // 이력변경타입 (I:등록, U:수정, D:삭제)
    private String extChgType;    // 셀러재고번호 이력변경타입

    //해외명품직구매 추가
    private String createIp;   //변경자 IP
    private String stckQtyModYn = "Y";

    private long eventNo = -1;        // 쇼킹딜 이벤트 번호
    private ProductConstDef.PrdInfoType prdInfoType = ProductConstDef.PrdInfoType.ORIGINAL;

    private long addedSelPrc;        // 판매가 + 옵션가
    private String mrgnPolicyCd;    // 마진정책
    private long puchPrc;            // 매입가
    private double mrgnRt;            // 마진율
    private long mrgnAmt;            // 마진금액
    private String aprvStatCd;        // 승인상태코드
    private String aprvStatNm;        // 승인상태명
    private long selMnbdNo;            // 판매자 no

    private long ctlgNo;            // 마트옵션, 카탈로그번호
    private long prevCtlgNo;        // 마트옵션, 카탈로그번호
    private String addDesc;            // 마트옵션, 추가설명
    private String spplBnftCd;        // 마트옵션, 추가혜택 코드 PD167
    private String repOptYn;        // 마트옵션, 대표옵션여부
    private String matchDt;            // 마트옵션, 카탈로그 매칭일자
    private long matchNo;            // 마트옵션, 카탈로그 매칭자
    private String dispModelNm;        // 카탈로그명
    private String optNm;            // 옵션명
    private String addInfoNm;        // 부가정보명
    private String prdExposeClfCd;  // 옵션 정렬 순서

    private String isOpenApi;        // OpenAPI 여부
    private String sellerStockCd;    // 셀러재고번호
    private String uniqChkCd;    // 유니크체크코드 (판매자재고번호 or 판매자+판매자재고번호)
    private String groupNumber;        //그룹번호
    private String summaryImage;    //요약정보이미지
    private String detailImage;        //상세정보이미지
    private String detailImageStr;    //상세정보이미지 값
    private String dgstExtNm;        //요약정보이미지 (db값)
    private String dtlExtNm;        //상세정보이미지 (db값)
    private String prdDtlTypCd;        // 상품 상세 구분 코드
    private String matchClfCd;        // 매칭 구분 코드
    private String memId;
    private String ptnrPrdSellerYN;    // 배송일지정셀러여부

    private String setTypCd;        // 상품구성코드
    private long rntlCst;            // 렌탈 가격

    private boolean isNotNecessaryMasterStock;    // 묶음 상품의 구성재고목록 필요 여부

    private ProductStockSetInfoVO stockSetInfoVO;    // 묶음 상품의 구성재고정보
    private List<ProductStockSetInfoVO> stockSetInfoListVO;    // 세트 상품의 구성재고정보
    private String optTypCd;
    private String updateType;
    private boolean isQCVerifiedStat;
    private boolean optPop;
    private String onlyOneBarCodeYn;    // 'Y' 인 경우 셀러의 모든 상품에서 하나만 존재

    private String prdSellerStockCd;
    private String stdPrdYn;
}