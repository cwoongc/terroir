package com.elevenst.terroir.product.registration.category.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DpGnrlDisp implements Serializable{

    private static final long serialVersionUID = 5217984563755889763L;

    //전시카테고리번호
    private long dispCtgrNo = 0;
    //상품번호
    private long prdNo = 0;
    //전시시작일자
    private String dispBgnDy = "";
    //전시종료일자
    private String dispEndDy = "";
    //등록일시
    private String createDt = "";
    //등록자번호
    private long createNo = 0;
    //수정일시
    private String updateDt = "";
    //수정자번호
    private long updateNo = 0;
    //전시카테고리번호
    private long gnrlCtgrNo = 0;
    //전시대카고리번호
    private long gnrlCtgr1No = 0;
    //전시중카테고리번호
    private long gnrlCtgr2No = 0;
    //전시소카테고리번호
    private long gnrlCtgr3No = 0;
    //전시세카테고리번호
    private long gnrlCtgr4No = 0;
    //판매상태 PD_PRD.SEL_STAT_CD(PD014)
    private String gnrlSelStatCd = "";
    //판매방식 PD_PRD.SEL_MTHD_CD(PD012)
    private String gnrlSelMthdCd = "";
    //판매주체번호(셀러번호)
    private long gnrlSelMnbdNo = 0;
    //총재고수량
    private long gnrlStckQty = 0;
    //옵션관련 [ 옵션상품없음(X), 옵션상품있음+옵션가없음(Y), 옵션상품있음+옵션가있음(Z) ]
    private String gnrlOptCd = "";
    //추가구성상품수량
    private long gnrlAddPrdQty = 0;
    //배송비지불유형코드 PD_PRD.DLV_CST_PAY_TYP_CD (PD017)
    private String gnrlDlvCstPayTypCd = "";
    //배송비설정기준코드 PD_PRD.DLV_CST_INST_BASI_CD (PD005)
    private String gnrlDlvCstInstBasiCd = "";
    //미성년자구매가능여부
    private String gnrlMinorSelCnYn = "";
    //브랜드코드
    private String gnrlBrandCd = "";
    //거래유형코드 PD_PRD.BSN_DEAL_CLF (01:수수료,02:직매입,03:특정매입)
    private String gnrlBsnDealClf = "";
    //배송유형코드 PD_PRD.DLV_CLF (01:자체배송,02:업체배송) (PD090)
    private String gnrlDlvClf = "";
    //OM상품여부 [OM상품(Y or NULL), OM상품아님(N)]
    private String gnrlOmPrdYn = "";
    //상품서비스영역코드 PD_PRD.SVC_AREA_CD (PD109)
    private String gnrlSvcAreaCd = "";
    //SO / BO 상품관리 리스트 노출 숨기기 상태 (Y:숨김, N or Null:노출)
    private String gnrlHideYn = "";
    //전세계 배송여부
    private String gblDlvYn = "";
    //영문사이트 노출 여부
    private String gnrlEngDispYn = "";
    //표준상품여부
    private String stdPrdYn = "";
}
