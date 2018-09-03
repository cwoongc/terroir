package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class BasisStockVO implements Serializable {

    private static final long serialVersionUID = 692157952739671221L;
    private	String		stockNo;		//재고번호
    private	String		prdNo;			//상품번호
    private String		dstPrdClfCd;	//물류상품구분코드
    private String		dstPrdClfNm;	//물류상품구분코드명
    private	String		optYn;			//옵션여부
    private	String		empNo;			//담당MD
    private	String		empNm;			//담당MD명
    private	String		wmsPrdNm;		//관리상품명
    private	String		optValueNm;		//옵션값
    private	String		barCode;		//바코드
    private String		boxUntMngYn;	//상자단위관리여부
    private	String		boxUntgdsCunt;	//BOX입수
    private	String		boxWhoutCanYn;	//BOX출고여부
    private	String		boxBarcdNo;		//BOX바코드
    private	String		dstStckStatCd;	//물류재고상태코드
    private	String		dstStckStatNm;	//물류재고상태코드명
    private String		selStatCd;		//판매상태
    private String		selStatNm;		//판매상태명
    private	String		repPtnrMemNo;	//대표거래처
    private	String		repPtnrMemNm;	//대표거래처명
    private	String		ssnlPrdYn;		//시즌성상품여부
    private	String		seasonClfCd;	//시즌구분코드
    private	String		seasonClfNm;	//시즌구분코드명
    private	String		onePackYn;		//합포장여부
    private	String		exprTrmChkYn;	//유통기한관리여부
    private	String		exprTrmClfCd;	//유통기한구분
    private	String		exprTrmClfNm;	//유통기한구분명
    private	String		exprTrmUnt;		//유통기한적용단위
    private	String		exprTrmUntNm;	//유통기한적용단위명
    private	String		exprTrmVal;		//유통기한적용값
    private String		incmAllwTrmRt;	//입고가능기한율
    private String		exprTrmInfo;	//유통기한적용값정보
    private String		incmAllwTrmInfo;//입고가능기한율정보
    private String		stckAttrRegYn;	//재고속성등록여부
    private String		dstChgrNm;		//물류담당자명
    private String		dstChgrTelno;	//물류담당자전화번호
    private String		avgUntprc;		//이동평균단가
    private String		acctUntprc;		//회계단가
    private String		ordrYn;			//입고요청여부
    private String		selMnbdId;		//판매자ID

    private	String		stockCnt;		//전체재고수
    private	String		notRegCnt;		//재고속성 미등록 수
    private	String		normalCnt;		//정상
    private	String		waitCnt;		//일시중지
    private	String		completeCnt;	//취급종료

    private	String		frigTypCd;		//보관구분
    private	String		frigTypNm;		//보관구분명
    private	String		rtnTwYn;		//반품수거여부
    private	String		selUnt;			//판매단위
    private	String		selUntNm;		//판매단위명
    private String		volMngYn;		//부피(체적)관리여부
    private	String		wdthLen;		//(체적)가로길이
    private	String		vrtclLen;		//(체적)세로길이
    private	String		hghtLen;		//(체적)높이길이
    private	String		stckWght;		//(단품)무게(g)
    private	String		maxLoadFlrCunt;	//최대적재층수(입고최대적재량-단수)
    private	String		maxLoadHghtLen;	//최대적재높이길이(입고최대적재량-높이)
    private	String		maxPlltBoxCunt;	//최대팰릿상자수(입고최대적재량-Pallet당 박스)

    private String		dstStckStatChgYn;	//물류재고상태변경여부
    private	String		chgCont;		//변경내용
    private	String		createNo;		//수정자번호
    private	String		updateNo;		//수정자번호
    private	String		updateNm;		//수장자명
    private	String		histDt;			//이력일시

    private	String		srchSel;		//상품검색
    private	String[]	srchVal;		//상품검색값
    private String		dstStckYn;		//재고속성등록여부
    private	int			start=1;		//Paging Start num
    private	int			end;			//Paging End num
    private	int			rowCnt;			//순서
    private	int			totalCount;		//Paging Total Count
    private long		selMnbdNo;		//판매자번호
    private String      itemPerBox;
    private long		lqtyRegNo;		//대량등록번호
    private long 		stckRegSeq;		//재고등록번호
    private String		rsltClfCd;		//결과구분코드
    private String		sflifeMngYn;	//유통기한관리여부
    private long		stckNo;			//재고번호
    private String		errMsgCont;		//오류메시지내용
    private	String		repPtnrMemId;	//대표거래처ID
    private String		urlType;
    private String 		deptCd;			//부서코드
    private String 		prdNm;			//상품명
    private int         stckMoveCnt;    //재고 이동수량
    private String      mngRsnNm;       //재고이동 사유명
    private String      fromCenterNm;   //from 센터
    private String      toCenterNm;     //to센터
    private String      toWhClfNm;      //이동창고유형명
    private String      centerCd;       //센터코드

    private ArrayList<BasisStockVO> barcodeAttrList;	//바코드속성 리스트
}
