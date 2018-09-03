package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Deprecated
public class EnterpriseMemberVO implements Serializable{
    private long memNO;
    private String enprClf 	= "";
    private String psnCobsEnprNO = "";
    private String cobsRptvRegNO = "";
    private String enpAddr = "";
    private String taxStmtIsuYN = "";
    private String comuMrenDclNO = "";
    private String etprsStatCD = "";
    private String enprMemCrtfStatCD = "";
    private String enprAprvEndDT = "";
    private String mainPrdDtlsDesc = "";
    private Date createDT;
    private int createNO;
    private Date updateDT;
    private int updateNO;
    private String bsnsNM = "";
    private String empID = "";
    private String empNM = "";

    private String cobsRPTV = "";		//대표자명
    private String rptvTlphnNO = "";	//대표자전화번호
    private String orgPtvTlphnNo = "";
    private String itm		= "";		//업종
    private String bsnSt	= "";		//업태
    private String faxNO	= "";		//FAX
    private String enpPlcMailNO = "";	//사업장우편번호
    private String enpPlcBaseAddr = "";	//사업장주소
    private String enpPlcDtlsAddr = "";	//사업장상세주소
    private String crgrNM = "";			//담당자이름
    private String gnrlTlphnNO = "";	//담당자전화번호
    private String prtblTlphnNO = "";	//담당자핸드폰번호
    private String email = "";			//담당자이메일
    private String ocbZoneAppvCd = "";	//OCB 승인코드

    private String comuMrenDclYN = "";
    private String comuMrenDclNotCode = "";
    private String comuMrenDclNotEtc = "";
    private String comuMrenDclNotRsn = "";

    private String etprFrgnclf = "";         //글로벌 회원 국내 해외 구분

    private String hotlinePrtblTlphnNO = ""; // hotline 휴대폰번호
    private Date hotlinePrtblTlphnNoDT ; // hotline 휴대폰번호 인증일자.

    private String keySellerYn;  		//Key판매자 여부
    private String  mngTeamNm ; 		//Key판매자 담당자팀명
    private String  mngMngrNm ; 		//Key판매자 담당자 명
    private String  mngTelNo ; 			// Key판매자 담당저내선번호
    private String  keySellerCreateDt;  //Key판매자 등록일자
    private String  trtRsn; 			//KeySeller해제사유
    private String createIp;

    //상품배송유형
    private String prdDeliTypCd;
    //수입형태
    private String imptTypCd;

    //옵션교환여부
    private String optChgYn;


    //제품관련문의연락처
    private String prdctReqTlphnNO;
    //제품관련문의연락처상품상세노출여부
    private String prdctReqTlphnNOYn;
    //AS/반품관련문의연락처
    private String clmReqTlphnNO;
    //AS/반품관련문의연락처상품상세노출여부
    private String clmReqTlphnNOYn;


    private String recommendMemID ; // 추천인 아이디.

    private String enprAprvComment ; // 승인 코멘트.

    //2011-01-11 추가됨
    private long basicMarginRate;//기본마진율
    private String dlvStlCD;//배송비 정산방식 코드(01:온라인정산, 02:오프라인정산)
    private String itgIdMemo;//통합ID 메모 설명

    private String entrustStlMthdCd;	// 위탁판매 정산 구분
    private int entrustStlRt;			// 위탁판매 정산율
    private String entrustStlDocNo;		// 위탁판매 정산 품의번호

    private Date baseInfoUpdateDt; // 기본정보 업데이트 일시

    private String suplCmpClfCd; // 공급업체구분 MB149

    private String qltyMngObjYn; // 품질관리대상 여부
}
