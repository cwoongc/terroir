package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberVO implements Serializable {
    private static final long serialVersionUID = -8889033186250210140L;

    private long    memNO;
    private String  memID = "";
    private String  memDI;
    private String  memNm;
    private String  memIdntyNo;
    private String  memClf;
    private String  memTypCD;
    private String  memStatCD;
    private String  memStatDtlsCD;
    private String  bsnDealClf;
    private String  wmsUseClfCd; // 물류센터이용코드 MB150 : 01(3PL), 02(4PL)
    private String  dlvClf;
    private String  offshoreDlvClf;
    private String  authrGrpCD;
    private String globalSellerYn;
    private boolean globalItgSeller;
    private boolean frSeller;
    private String certCardYn;
    private String memEmail;

    private boolean certStockSeller;
    private long repSellerNo;


//    private EnterpriseMemberVO enterpriseMemberVO;
//    private PrivateMemberVO privateMemberVO;

    private String chinaApplyStatCd;
    private String ocbZoneAppvCd;
//    private String bsnDealClf;
//    private String wmsUseClfCd;
    private String adltCrtfYn;
    private String frgnClf;

    private boolean privateSeller;
    private String privateFrgnClf;

    private boolean etprsSeller;
    private String etprsFrgnClf;
    private String suplCmpClfCd;

    public String getFrgnClf(){
        if(privateSeller){
            return this.privateFrgnClf;
        }else if(etprsSeller){
            return this.etprsFrgnClf;
        }else{
            return "";
        }
    }




    // [개인정보 유효기간제] widetns 이정주 :isSeparated 추가
    private boolean isSeparated = false; // 유효기간제 개인정보 분리보관 여부
    // [개인정보 유효기간제] widetns 이정주 : isNotDisplay 추가
    private boolean isNotDisplay = false; // 유효기간제 데이터 비노출 여부

    // [개인정보 유효기간제] widetns 이정주 : setIsNotDisplay() 추가
    public void setIsNotDisplay(boolean isNotDisplay) {
        /*
        this.isNotDisplay = isNotDisplay;
        if (privateMemberVO != null) {
            privateMemberVO.setIsNotDisplay(this.isNotDisplay);
        }
        if (addressBO != null) {
            addressBO.setIsNotDisplay(this.isNotDisplay);
        }

        if (accountBO != null) {
            accountBO.setIsNotDisplay(this.isNotDisplay);
        }

        if (globalAddressBO != null) {
            globalAddressBO.setIsNotDisplay(this.isNotDisplay);
        }

        if (globalAccountBO != null) {
            globalAccountBO.setIsNotDisplay(this.isNotDisplay);
        }
        */
    }

    // [개인정보 유효기간제] widetns 이정주 : setSprtInfo() 추가
    public void setSprtInfo(MemberSprtVO sprtInfo) {

        /**
         * SMS, EMAIL 수신 여부 관련해서는 분리보관 테이블에서 데이터를 가져오지도 않고 해당 메소드를 통해 세팅하지도 않는다.
         * 그 이유는 SMS, EMAIL 수신 여부는 분리보관 회원의 경우 무조건 N으로 되어 있어야 하기 때문이다.(발송하지 않기
         * 위해) 분리보관 회원 테이블의 SMS, EMAIL 수신 여부를 업데이트 하기 위해서는 따로 만들어진
         * API(updateSmsEmailRcvYnForSprtMem)를 사용해야 한다.
         */
        /*
        setMemID(sprtInfo.getMemID());
        setMemNM(sprtInfo.getMemNM());
        setMbEngNm(sprtInfo.getMbEngNm());
        setMemScrtNO(sprtInfo.getMemScrtNO());
        setGnrlTlphnNO(sprtInfo.getGnrlTlphnNO());
        setPrtblTlphnNO(sprtInfo.getPrtblTlphnNO());
        setEmail(sprtInfo.getEmail());
        setEmailAddrCD(sprtInfo.getEmailAddrCD());
        setDrctMailAddr(sprtInfo.getDrctMailAddr());
        setConcatEmail(sprtInfo.getConcatEmail());
        setBgnEntDY(sprtInfo.getBgnEntDY());
        setIntUpdateDT(sprtInfo.getIntUpdateDT());
        setRealNMCrtfDY(sprtInfo.getRealNMCrtfDY());
        setRealNMCrtfType(sprtInfo.getRealNMCrtfType());
        setIntSvcNO(sprtInfo.getIntSvcNO());

        if (privateMemberVO != null) {
            privateMemberVO.setMemNM(sprtInfo.getMemNM());
        }

        if (addressBO != null) {
            addressBO.setMemNM(sprtInfo.getMemNM());
            addressBO.setEmail(sprtInfo.getEmail());
            addressBO.setGnrlTlphnNO(sprtInfo.getGnrlTlphnNO());
            addressBO.setPrtblTlphnNO(sprtInfo.getPrtblTlphnNO());
            addressBO.setMemID(sprtInfo.getMemID());
        }
        */
    }



}


