package com.elevenst.terroir.product.registration.lifeplus.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum RsvDisabHhListCd implements GroupCodeInterface {
    ONE_HOUR("01","1시간"),
    TWO_HOUR("02","2시간"),
    THREE_HOUR("03","3시간"),
    FOUR_HOUR("04","4시간")
    ;

    private String dtlsCd;
    private String dtlsComNm;

    RsvDisabHhListCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "LC048";
    }

    @Override
    public String getGrpCdNm() {
        return "예약 불가시 코드";
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
