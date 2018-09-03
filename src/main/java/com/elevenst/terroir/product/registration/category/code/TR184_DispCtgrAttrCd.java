package com.elevenst.terroir.product.registration.category.code;

import com.elevenst.common.util.GroupCodeInterface;


public enum TR184_DispCtgrAttrCd implements GroupCodeInterface{
    TICKET_PRD__01(                     "01","TicketPrd"),
    ATTR02_PRD__02(                     "02","Attr02Prd"),
    POINT_PRD__03(                      "03","PointPrd"),
    POINT_PAY__04(                      "04","PointPay"),
    POINT_VA_PAY__05(                   "05","PointVaPay"),
    GOLD__06(                           "06","Gold"),
    HP_PAY__07(                         "07","HpPay"),
    PHOENIX_PARK__08(                   "08","phoenixpark"),
    ATTR09_PRD__09(                     "09","Attr09Prd"),
    ATTR10_PRD__10(                     "10","Attr10Prd"),
    ATTR11_PRD__11(                     "11","Attr11Prd")
    ;

    private String dtlsCd;
    private String dtlsComNm;

    TR184_DispCtgrAttrCd(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "TR184";
    }

    @Override
    public String getGrpCdNm() {
        return "카테고리별 경제수단 차단 코드(전시카테고리속성코드)";
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
