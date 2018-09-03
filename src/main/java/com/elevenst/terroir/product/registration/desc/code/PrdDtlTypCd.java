package com.elevenst.terroir.product.registration.desc.code;

import com.elevenst.common.util.GroupCodeInterface;


/**
 * PD160 상품상세 구분 코드
 * PD_PRD_DESC.PRD_DTL_DYP_CD
 */
public enum PrdDtlTypCd implements GroupCodeInterface{

    OPTION_BARO_SELECT_1DAN("01", "옵션 바로선택(1단)","old", ""),
    OPTION_BARO_SELECT_2DAN("02", "옵션 바로선택(2단)", "old", "") ,
    DESIGN_TEMPLATE_NOT_APPLY_VERTICAL_1DAN("03","디자인템플릿미적용+세로1단", "old",""),
    DESIGN_TEMPLATE_NOT_APPLY_VERTICAL_2DAN("04","디자인템플릿미적용+세로2단", "old", ""),
    DESIGN_TEMPLATE_APPLY_BASIC_VERTICAL_1DAN("05", "디자인템플릿적용+템플릿타입기본+세로1단", "basic", ""),
    DESIGN_TEMPLATE_APPLY_BASIC_VERTICAL_2DAN("06", "디자인템플릿적용+템플릿타입기본+세로2단", "basic", ""),
    DESIGN_TEMPLATE_APPLY_IMAGE_EMPHASIS_VERTICAL_1DAN("07", "디자인템플릿적용+템플릿타입이미지강조+세로1단", "image", ""),
    DESIGN_EDITOR_NOT_APPLY("08",  "디자인편집기미연동", "", ""),
    DESIGN_EDITOR_APPLY_PRODUCT_DESC("09",  "디자인편집기연동+상품상세", "", ""),
    DESIGN_EDITOR_APPLY_OPTION_VERTICAL_1DAN("10","디자인편집기연동+옵션세로1단", "design",""),
    DESIGN_EDITOR_APPLY_OPTION_VERTICAL_2DAN("11","디자인편집기연동+옵션세로2단", "design", ""),
    DESIGN_EDITOR_APPLY_OPTION_VERTICAL_3DAN("12","디자인편집기연동+옵션세로3단", "design", "")
    ;



    public final String groupCode = "PD160";
    public final String groupCodeName = "상품상세 구분 코드";
    public final String code;
    public final String codeName;
    public final String type;
    public final String typeName;

    PrdDtlTypCd(String code, String codeName, String type, String typeName) {
        this.code = code;
        this.codeName = codeName;
        this.type = type;
        this.typeName = typeName;
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
