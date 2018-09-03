package com.elevenst.terroir.product.registration.deal.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EventPricePegVO implements Serializable {

    private static final long serialVersionUID = 1647464108081719847L;
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
    private Date fullEventBgnDt;
    private Date fullEventEndDt;
    private String premiumAplYn;
    private String soRngCd;
    private String soRngNm;
    private long soCostDfrmRt;
    private long moCostDfrmRt;
    private long cupnNo;
    private String eventStatCd;
    private String useYn;
    private String createDt;
    private long createNo;
    private String createNm;
    private String createId;
    private String updateDt;
    private long updateNo;
    private String eventStat;  //진행상태
    private String aprvStatCd;

    private String dispYN; //전시여부
    private String eventNo;
    private String plnDispNm;
    private long plnDispNo;

    private String stat;  //진행상태
    private String remark;
    private int rqstQty;
    private String prdAddStatCd; // 쇼킹딜상품상태코드(PD136)
    private String reqPrcBaseCd; // 쇼킹딜신청가근거코드(PD137)
    private String reqPrcBaseNm; // 쇼킹딜신청가근거코드명
    private String drctCnfmReqYn; // 즉시확정요청여부
    private String baseDataTxt;	// 근거자료(텍스트)
    private String baseDataImg; // 근거자료(이미지)
    private String etcBaseDataImg; // 기타근거자료(이미지)

    private String dispCtgrNm;		//리프카테고리
    private String dispCtgr1Nm;		//대카
    private String dispCtgr2Nm;		//중카
    private String dispCtgr3Nm;		//소카

    private boolean changeBgnDt;	// 신청일 변경 여부
    private String payAgreeYn;		// 결제동의여부

    private long selPrc; 		 // 판매가
    private long hgrnkSelPrc;  // 상위 이벤트 판매가
    private String postSelStatCd; // 쇼킹딜 종료 후 판매상태 코드
    private String postRollbackYn; // 쇼킹딜 종료 후 상품정보 원복여부
    private String cnfmDt;	  //확정일

    private String eventPrdNm;		// 쇼킹딜 제2 상품명....
    private long selQty;				// 총 판매수량
    private long firstEventNo;			// 최초이벤트번호
    private long hgrnkEventNo;		// 상위이벤트번호
    private String eventTmtrCd;		// 이벤트회차코드
    private String eventTmtrCdNm;	// 이벤트회차코드명
    private String incrObjYn;			// 연장대상여부
    private String incrPayAgreeYn;	// 연장결제동의여부
    private String incrPayYn;			// 연장진행비결제여부
    private String dpEncoreYn;		// 앵콜아이콘 노출여부
    private String ptnrPrmtYn;		// 제휴사 자체 프로모션 여부

    private int chnlBitNo = 0;
    private String drctVisitYn = null;

    private String bssCont1;
    private String bssCont2;
    private String bssCont3;
    private String bssCont4;
    private String bssCont5;

    private String prolAfMntnYn; // 연장이후유지여부

    private String mdNo;
    private String dscAmt;
    private String dscRt;
    private String cupnDscMthdCd;
    private String prdNo ;
    private String dscAmtRt;
    //private UploadedFileItem baseDataImgFile;
    //private UploadedFileItem etcBaseDataImgFile;
    //private UploadedFileItem dealPrdImgFile;

    private String repImgUrl;
    private String dealImgUrl;
    private String onlyImgUrl;
    private int verNo;

    private long refEventObjNo ; //aplNo와 같음

    //S.배송비관련
    private String       dlvCstInstBasiCd;
    private String       dlvCstPayTypCd;
    private long         dlvCst;
    private long		 dlvBasiAmt;
    private String       dlvCstInfoCd;
    private String       bndlDlvCnYn;

    private List ordQtyBasDlvcstBOList = new ArrayList();                   // 주문수량기준배송비 목록
}
