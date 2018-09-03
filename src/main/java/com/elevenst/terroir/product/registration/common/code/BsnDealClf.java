package com.elevenst.terroir.product.registration.common.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 * 거래유형코드
 *  SY_CO_MASTER에는 미존재.
 *  @see PD_PRD.BSN_DEAL_CLF
 */
public enum BsnDealClf implements GroupCodeInterface{
    COMMISSION(             "01", "수수료 유형(일반상품)"),
    DIRECT_PURCHASE(        "02", "직매입 유형"),
    SPECIFIC_PURCHASE(      "03", "특정매입 유형"),
    TRUST_PURCHASE(         "04", "위탁판매 유형")
    ;

    private String dtlsCd;
    private String dtlsComNm;
    BsnDealClf(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "";
    }

    @Override
    public String getGrpCdNm() {
        return "거래유형코드";
    }

    @Override
    public String getDtlsCd() {
        return dtlsCd;
    }

    @Override
    public String getDtlsComNm() {
        return dtlsComNm;
    }
}
