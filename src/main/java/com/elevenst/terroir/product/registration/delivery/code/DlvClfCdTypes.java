package com.elevenst.terroir.product.registration.delivery.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum DlvClfCdTypes implements GroupCodeInterface{
    ELEVENST_DELIVERY("01","11번가배송"),
    COMPANY_DELIVERY("02","업체배송"),
    ELEVENST_ABROAD_DELIVERY("03","11번가해외배송"),
    SELLER_CONSIGNMENT_DELIVERY("04","셀러위탁배송"),
    ;


    private String dtlsCd;
    private String dtlsComNm;
    DlvClfCdTypes(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return null;
    }

    @Override
    public String getGrpCdNm() {
        return null;
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
