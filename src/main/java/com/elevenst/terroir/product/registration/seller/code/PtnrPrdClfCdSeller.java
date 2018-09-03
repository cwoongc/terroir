package com.elevenst.terroir.product.registration.seller.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum PtnrPrdClfCdSeller implements GroupCodeInterface{

    TEST01(     "01",   "11349581",	""),
    TEST02(     "02",   "21808397",	""),
    SAMSUNG01(  "03",   "10000276",	"SAMSUNG"),
    LG01(       "04",   "26817898",	"LG"),
    LG02(       "05",   "36560843",	"LG"),
    LG03(       "06",   "26655457",	"LG"),
    LG04(       "07",   "37729571",	"LG"),
    LG05(       "08",   "25957786",	"LG"),
    LG06(       "09",   "10007228",	"LG"),
    LG07(       "10",   "37549390",	"LG"),
    LG08(       "11",   "42360667",	"LG"),
    LG09(       "12",   "10003757",	"LG"),
    LG10(       "13",   "23928376",	"LG"),
    LG11(       "14",   "10003003",	"LG"),
    LG12(       "15",   "38015454",	"LG"),
    LG13(       "16",   "19403351",	"LG"),
    LG14(       "17",   "10002840",	"LG"),
    LG15(       "18" ,  "10000490",	"LG"),
    LG16(       "19",   "27023899",	"LG"),
    LG17(       "20",   "10933409",	"LG"),
    LG18(       "21",   "19791501",	"LG"),
    LG19(       "22",   "37191365",	"LG"),
    LG20(       "23",   "44710913",	"LG"),
    SAMSUNG02(  "24",   "27161196",	"SAMSUNG"),
    SAMSUNG03(  "25",   "36426731",	"SAMSUNG"),
    SAMSUNG04(  "26",   "36734384",	"SAMSUNG"),
    SAMSUNG05(  "27",   "27019507",	"SAMSUNG"),
    SAMSUNG06(  "28",   "36646048",	"SAMSUNG"),
    SAMSUNG07(  "29",   "36760147",	"SAMSUNG"),
    SAMSUNG08(  "30",   "37393162",	"SAMSUNG"),
    SAMSUNG09(  "31",   "10002398",	"SAMSUNG"),
    SAMSUNG10(  "32",   "27137829",	"SAMSUNG"),
    SAMSUNG11(  "33",   "42055118",	"SAMSUNG"),
    SAMSUNG12(  "34",   "37588289",	"SAMSUNG"),
    LG21(       "35",   "26835551",	"LG"),
    LG22(       "36",   "47119374",	"LG")
    ;


    private String dtlsCd;
    private String dtlsComNm;
    private String cdVal1;

    PtnrPrdClfCdSeller(String dtlsCd, String dtlsComNm, String cdVal1) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
        this.cdVal1 = cdVal1;
    }

    @Override
    public String getGrpCd() {
        return "PD243";
    }

    @Override
    public String getGrpCdNm() {
        return "지정배송일셀러관리";
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
