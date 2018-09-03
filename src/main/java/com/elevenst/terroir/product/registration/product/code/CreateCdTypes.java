package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

public enum CreateCdTypes implements GroupCodeInterface {

    /**
     * GRP_CD : PD052
     * GRP_CD_NM : 등록형태
     * PD_PRD.CREATE_CD
     */

    SELLER_API("0100", "OPENAPI")                            // OPENAPI
    ,SO_FORMREG("0200", "단일등록")			                // 단일등록
    ,SO_BULKREG("0202", "대량등록")			                // 대량등록
    ,MO_SIMPLEREG("0203", "모바일 간편등록")		                // 모바일 등록
    ;

    private final String dtlsCd;
    private final String dtlsComNm;

    CreateCdTypes(String dtlsCd, String dtlsComNm) {
        this.dtlsCd = dtlsCd;
        this.dtlsComNm = dtlsComNm;
    }

    @Override
    public String getGrpCd() {
        return "PD052";
    }

    @Override
    public String getGrpCdNm() {
        return "등록형태";
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
