package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 *  PD029 판매기간구분코드
 */
public enum SelPrdClfCd implements GroupCodeInterface {

    FIXED_MANUAL_INPUT(     "100", "직접입력",          "0"),
    FIXED_DAY_3(            "101", "3일",             "3"),
    FIXED_DAY_5(            "102", "5일",             "5"),
    FIXED_DAY_7(            "103", "7일",             "7"),
    FIXED_DAY_15(           "104", "15일",            "15"),
    FIXED_DAY_30(           "105", "30일(1개월)",      "30"),
    FIXED_DAY_60(           "106", "60일(2개월)",      "60"),
    FIXED_DAY_90(           "107", "90일(3개월)",      "90"),
    FIXED_DAY_120(          "108", "120일(4개월)",     "120"),

    COBUY_DAY_3(            "201", "3일",             "3"),
    COBUY_DAY_5(            "202", "5일",             "5"),
    COBUY_DAY_7(            "203", "7일",             "7"),

    AUCT_DAY_15(            "301", "15일",            "15"),
    AUCT_DAY_30(            "302", "30일(1개월)",      "30"),
    AUCT_DAY_60(            "303", "60일(2개월)",      "60"),
    AUCT_DAY_90(            "304", "90일(3개월)",      "90"),
    AUCT_DAY_120(           "305", "120일(4개월)",     "120"),

    PLN_MANUAL_INPUT(       "400", "직접입력",          "0"),
    PLN_DAY_3(              "401", "3일",             "3"),
    PLN_DAY_5(              "402", "5일",             "5"),
    PLN_DAY_7(              "403", "7일",             "7"),
    PLN_DAY_15(             "404", "15일",            "15"),
    PLN_DAY_30(             "405", "30일(1개월)",      "30"),
    PLN_DAY_60(             "406", "60일(2개월)",      "60"),
    PLN_DAY_90(             "407", "90일(3개월)",      "90"),

    USED_MANUAL_INPUT(      "500", "직접입력",          "0"),
    USED_DAY_3(             "501", "3일",             "3"),
    USED_DAY_5(             "502", "5일",             "5"),
    USED_DAY_7(             "503", "7일",             "7"),
    USED_DAY_15(            "504", "15일",            "15"),
    USED_DAY_30(            "505", "30일(1개월)",      "30"),
    USED_DAY_60(            "506", "60일(2개월)",      "60"),
    USED_DAY_90(            "507", "90일(3개월)",      "90"),
    USED_DAY_120(           "508", "180일(6개월)",     "120"),


    SIX_MANUAL_INPUT(     "600", "직접입력",          "0"),
    SIX_DAY_1(            "601", "1일",             "1"),
    SIX_DAY_2(            "602", "2일",             "2"),
    SIX_DAY_3(            "603", "3일",             "3"),
    SIX_DAY_4(            "604", "4일",             "4"),
    SIX_DAY_5(            "605", "5일",             "5"),
    SIX_DAY_6(            "606", "6일",             "6"),
    SIX_DAY_7(            "607", "7일",             "7"),
    ;

    private String dtlsCd;
    private String dtlsComNm;
    private String cdVal1;

    SelPrdClfCd(String dtlsCd, String dtlsComNm, String cdVal1) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
        this.cdVal1 = cdVal1;
    }

    @Override
    public String getGrpCd() {
        return "PD029";
    }

    @Override
    public String getGrpCdNm() {
        return "판매기간구분코드";
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
