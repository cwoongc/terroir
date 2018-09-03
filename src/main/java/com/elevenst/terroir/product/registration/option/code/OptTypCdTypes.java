package com.elevenst.terroir.product.registration.option.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum OptTypCdTypes implements GroupCodeInterface{

    /**
     * GRP_CD : PD141
     * GRP_CD_NM : 옵션 구분 코드
     * PD_PRD.OPT_TYP_CD
     */

    NULL("", "")
    ,DATE("01", "날짜형") 			                // 날짜형
    ,CALC("02", "계산형") 			                // 계산형
    ,RENTAL("03", "렌탈형")   			            // 렌탈형
    ;

    private final String dtlsCd;
    private final String dtlsComNm;

    OptTypCdTypes(String dtlsCd, String dtlsComNm) {
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
        return "PD141";
    }

    @Override
    public String getGrpCdNm() {
        return "옵션구분코드";
    }


}
