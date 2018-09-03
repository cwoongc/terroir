package com.elevenst.terroir.product.registration.lifeplus.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum RsvDisabCndtListCd implements GroupCodeInterface {
    ONE_HOUR("01","당일예약 가능"),
    TWO_HOUR("02","당일예약 불가"),
    THREE_HOUR("03","D+1일(내일)까지 예약불가"),
    FOUR_HOUR("04","D+2일까지 예약불가")
        ;

    private String dtlsCd;
    private String dtlsComNm;

    RsvDisabCndtListCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "LC047";
    }

    @Override
    public String getGrpCdNm() {
        return "예약불가 조건코드";
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
