package com.elevenst.terroir.product.registration.lifeplus.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum SaleHmListCd implements GroupCodeInterface {
    ZERO("0000","00:00", "오전 0시"),
    ONE("0100","01:00", "오전 1시"),
    TWO("0200","02:00", "오전 2시"),
    THREE("0300","03:00", "오전 3시"),
    FOUR("0400","04:00", "오전 4시"),
    FIVE("0500","05:00", "오전 5시"),
    SIX("0600","06:00", "오전 6시"),
    SEVEN("0700","07:00", "오전 7시"),
    EIGHT("0800","08:00", "오전 8시"),
    NINE("0900","09:00", "오전 9시"),
    TEN("1000","10:00", "오전 10시"),
    ELEVEN("1100","11:00", "오전 11시"),
    TWELVE("1200","12:00", "오후 0시"),
    THIRTEEN("1300","13:00", "오후 1시"),
    FOURTEEN("1400","14:00", "오후 2시"),
    FIFTEEN("1500","15:00", "오후 3시"),
    SIXTEEN("1600","16:00", "오후 4시"),
    SEVENTEEN("1700","17:00", "오후 5시"),
    EIGHTEEN("1800","18:00", "오후 6시"),
    NINETEEN("1900","19:00", "오후 7시"),
    TWENTY("2000","20:00", "오후 8시"),
    TWENTYONE("2100","21:00", "오후 9시"),
    TWENTYTWO("2200","22:00", "오후 10시"),
    TWENTYTHREE("2300","23:00", "오후 11시"),
    ;

    private String dtlsCd;
    private String dtlsComNm;
    private String cdVal1;

    SaleHmListCd(String dtlsCd, String dtlsComNm, String cdVal1) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
        this.cdVal1 = cdVal1;
    }

    @Override
    public String getGrpCd() {
        return "LC046";
    }

    @Override
    public String getGrpCdNm() {
        return "영업시간코드";
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
}
