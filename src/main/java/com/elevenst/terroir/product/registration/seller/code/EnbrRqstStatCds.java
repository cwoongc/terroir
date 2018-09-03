package com.elevenst.terroir.product.registration.seller.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum EnbrRqstStatCds implements GroupCodeInterface {

    APPLYING("01", "신청중")
    ,STANDBY("02", "승인대기")
    ,APPROVAL("03", "승인")
    ;

    private final String dtlsCd;
    private final String dtlsComNm;

    EnbrRqstStatCds(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getDtlsCd() {
        return dtlsCd;
    }

    @Override
    public String getDtlsComNm() {
        return dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "MB171";
    }

    @Override
    public String getGrpCdNm() {
        return "입점신청상태코드";
    }
}
