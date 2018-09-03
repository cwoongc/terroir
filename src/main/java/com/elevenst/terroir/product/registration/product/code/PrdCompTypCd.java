package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum PrdCompTypCd implements GroupCodeInterface{
    PACKAGE_PRODUCT("01", "패키지상품"),
    ADD_PRODUCT("02", "추가상품"),
    GIFT("03", "사은품")
    ;

    private String dtlsCd;
    private String dtlsComNm;
    PrdCompTypCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD006";
    }

    @Override
    public String getGrpCdNm() {
        return "상품구성유형코드";
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
