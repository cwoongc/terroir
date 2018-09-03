package com.elevenst.terroir.product.registration.desc.code;


import com.elevenst.common.util.GroupCodeInterface;

/**
 * PD022 상품설명유형코드
 * PD_PRD_DESC.PRD_DESC_TYP_CD
 */
public enum PrdDescTypCd implements GroupCodeInterface {

    BASIC("01","기본설명"),
    DETAIL("02","상세설명"),
    UNUSUAL("03","특이사항"),
    CAUTIONS("04","주의사항"),
    SPECIAL_SALE_COPY("05","특판선전문구"),
    AS_INFO("06","AS 안내정보"),
    RETURN_EXCHANGE_INFO("07","반품/교환안내"),
    INSTALLATION_CHARGE("08","설치비"),
    APPEARANCE_FUNCTION_DESC("09","외관,기능상 특이사항"),
    COD_OR_INSTALLATION_CHARGE_INFO("10","착불/설치비안내"),
    PIN("11","PIN(구매자정보)"),
    SMART_OPTION_INTRO_DESC("12", "스마트옵션인트로설명"),
    SMART_OPTION_OUTRO_DESC("13", "스마트옵션아웃트로설명"),
    MOBILE_DESC("14","모바일상세설명"),
    MOBILE_SMART_OPTION_INTRO_DESC("15","모바일스마트옵션인트로설명"),
    MOBILE_SMART_OPTION_OUTRO_DESC("16","모바일스마트옵션아웃트로설명"),
    E_COUPON_PRODUCT_USING_INFO("17","E쿠폰상품이용안내")
    ;

    public final String groupCode = "PD022";
    public final String groupCodeName = "상품 설명 유형 코드";
    public final String code;
    public final String codeName;


    PrdDescTypCd(String code, String codeName) {
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
