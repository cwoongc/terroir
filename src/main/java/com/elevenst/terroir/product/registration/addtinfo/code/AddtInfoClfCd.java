package com.elevenst.terroir.product.registration.addtinfo.code;


import com.elevenst.common.util.GroupCodeInterface;

/**
 * PD248 부가정보구분코드
 * PD_PRD_ADDT_INFO.ADDT_INFO_CLF_CD
 */
public enum AddtInfoClfCd implements GroupCodeInterface {

    FACTORY_PRICE("01","출고가"),
    DECLARED_SUPPORT_FUND("02","공시지원금"),
    ADDITIONAL_SUPPORT_FUND("03","추가지원금"),
    BROADCASTING_PRD_YN("04","방송전용상품여부 "),
    OVERSEA_DELIVERY_CUSTOMS_YN("05","해외배송 관부가세 여부")
    ;


    public final String groupCode = "PD248";
    public final String groupCodeName = "부가정보구분코드";
    public final String code;
    public final String codeName;


    AddtInfoClfCd(String code, String codeName) {
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
