package com.elevenst.terroir.product.registration.delivery.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 * PD035
 */
public enum PrdAddrClfCd implements GroupCodeInterface {

    EX_WAREHOUSE("01",                      "출고지"),
    EXCHANGE_RETURN("02",                   "교환/반품지"),
    VISIT_RECEIVE("03",                     "방문수령지"),
    SELLER_GLOBAL_EX_WAREHOUSE("04",        "판매자해외출고지"),
    SELLER_EXCHANGE_RETURN("05",            "판매자반품교환지")
    ;


    private String dtlsCd;
    private String dtlsComNm;
    PrdAddrClfCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD035";
    }

    @Override
    public String getGrpCdNm() {
        return "상품주소지구분코드";
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
