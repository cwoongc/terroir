package com.elevenst.terroir.product.registration.lifeplus.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum  SvcCnAreaTypCd implements GroupCodeInterface {

    HEADSHOP("01","본점")
    ,PRODUCT("02","상품")
    ;

    private String dtlsCd;
    private String dtlsComNm;

    SvcCnAreaTypCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "LC050";
    }

    @Override
    public String getGrpCdNm() {
        return "서비스가능정책구분";
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
