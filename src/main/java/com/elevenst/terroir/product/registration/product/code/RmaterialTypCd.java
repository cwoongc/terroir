package com.elevenst.terroir.product.registration.product.code;

import com.elevenst.common.util.GroupCodeInterface;

/**
 * PD_PRD.RMATERIAL_TYP_CD
 */
public enum RmaterialTypCd implements GroupCodeInterface {
    FARM_PRODUCT("01","농산물"),
    FISHERY_PRODUCT("02","수산물"),
    PROCESSED_PRODUCT("03","가공품"),
    ORIGIN_MARK_NOT_REQUIRED("04","원산지 의무 표시대상 아님"),
    SEE_DETAILED_DESCRIPTION_FOR_PRODUCT_ORIGIN("05","상품별 원산지는 상세설명 참조")
    ;


    public final String groupCode = "PD098";
    public final String groupCodeName = "원재료 유형 코드";
    public final String code;
    public final String codeName;


    RmaterialTypCd(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getGrpCd() {
        return groupCode;
    }

    @Override
    public String getGrpCdNm() {
        return groupCodeName;
    }

    @Override
    public String getDtlsCd() {
        return code;
    }

    @Override
    public String getDtlsComNm() {
        return codeName;
    }
}
