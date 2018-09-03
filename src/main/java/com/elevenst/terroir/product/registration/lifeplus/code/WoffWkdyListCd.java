package com.elevenst.terroir.product.registration.lifeplus.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum WoffWkdyListCd implements GroupCodeInterface {
    SUNDAY("1","일요일", "일", "sun"),
    MONDAY("2","월요일", "월", "mon"),
    TUESDAY("3","화요일", "화", "tue"),
    WEDNESDAY("4","수요일", "수", "wed"),
    THURSDAY("5","목요일", "목", "sun"),
    FRIDAY("6","금요일", "금", "sun"),
    SATURDAY("7","토요일", "토", "sun");

    private String dtlsCd;
    private String dtlsComNm;
    private String cdVal1;
    private String cdVal2;

    WoffWkdyListCd(String dtlsCd, String dtlsComNm, String cdVal1, String cdval2) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
        this.cdVal1 = cdVal1;
        this.cdVal2 = cdval2;
    }

    @Override
    public String getGrpCd() {
        return "LC049";
    }

    @Override
    public String getGrpCdNm() {
        return "영업요일코드";
    }

    @Override
    public String getDtlsCd() {
        return dtlsCd;
    }

    @Override
    public String getDtlsComNm() {
        return dtlsComNm;
    }

    public String getCdVal1() {
        return cdVal1;
    }

    public String getCdVal2() {
        return cdVal2;
    }
}
