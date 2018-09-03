package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum PrdStatCd implements GroupCodeInterface {


    /**
     * GRP_CD : PD019
     * GRP_CD_NM : 상품상태코드
     * PD_PRD.PRD_STAT_CD
     */

    NEW("01",           "새 상품")
    ,USED("02",         "중고상품")
    ,RETURN("03",       "재고상품")
    ,REFUR("04",        "리퍼상품")
    ,DISPLAY("05",      "진열상품")
//    ,DIRECT_MADE("06",  "직접제작상품 (사용안함)")
    ,RARE("07",         "희귀상품")
    ,REFUND("08",       "반품상품")
    ,SCRATCH("09",      "스크래치 상품")
    ,ORDER_MADE("10",   "주문제작상품")
    ,PIN_NUMBER("11",   "PIN(정보입력)상품")
    ;

    private final String dtlsCd;
    private final String dtlsComNm;

    PrdStatCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD019";
    }

    @Override
    public String getGrpCdNm() {
        return "상품상태코드";
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
