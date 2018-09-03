package com.elevenst.terroir.product.registration.common.process.prdtyp;

import com.elevenst.exception.ExceptionEnumTypes;

public enum TypeExceptionEnumTypes implements ExceptionEnumTypes {

    RENTAL_ONLY_FREE_DLV("PRD_REG", "TYPE001",  "렌탈상품은 배송비 무료로만 설정이 가능합니다.")
    ,RENTAL_NOT_ENOUGH_CASH("PRD_REG","TYPE002","렌탈상품 등록을 위해서는 셀러캐시가 30만원 이상 충전되어 있어야 합니다.")
    ,RENTAL_NOT_API("PRD_REG","TYPE003","렌탈옵션 상품은 API 등록을 지원하지 않습니다.")
    ,RENTAL_IS_NOT_SELLER("PRD_REG","TYPE004","렌탈옵션 서비스를 이용 할 수 없는 판매자 입니다.")
    ,RENTAL_OPT_REG("PRD_REG","TYPE005","렌탈샵 셀러분들은 렌탈전용 옵션을 사용해 상품을 등록해주세요!")
    ,RENTAL_NOT_INFO("PRD_REG","TYPE006","렌탈옵션의 옵션정보가 없습니다")
    ;

    private final String msgGroupId;
    private final String code;
    private final String message;

    TypeExceptionEnumTypes(String msgGroupId, String code, String message) {
        this.msgGroupId = msgGroupId;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMsgGroupId() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
