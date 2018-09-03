package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WmCustomsVO extends WmsVO implements Serializable {
    private static final long serialVersionUID = 6335862628007600160L;

    private String hsCode = "";	//HS코드(세번부호)
    private long bgnPrc;	//적용시작 상품 가격
    private long endPrc;	//적용종료 상품 가격
    private String hsName;	//HS명(세번명)
    private String wmsCd;	//WMS코드(WMS 제휴사용 코드)
    private double tvar;	//과세가격적용율(Taxable Value Apply Rate )
    private double cstr;	//관세율(Customs Rate)
    private double ictr;	//개별소비세율(Individual consumption tax Rate)
    private double rstr;	//농특세율(Special tax for rural development Rate)
    private double edtr;	//교육세율(Education tax Rate)
    private double vatr;	//부가세율(Value added tax Rate)
    private String cscAplYn; //통관수수료 적용 여부 (Customs Service Charge Apply YN)

    //add 2011.08.18
    private String deliveryCd = "";		//출고지코드(EU/EU외/미국)
    private double importCharge;	//수입 제반비용율
    private String remark; 			//비고

    private String korNm;			//wm_gbl_hs_code 한글명
    private String engNm;			//wm_gbl_hs_code 영문명
    private long gblHsCodeNo;		//wm_gbl_hs_code 순번
}
