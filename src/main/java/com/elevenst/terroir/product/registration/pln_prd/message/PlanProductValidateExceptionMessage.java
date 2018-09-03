package com.elevenst.terroir.product.registration.pln_prd.message;

public class PlanProductValidateExceptionMessage {
    public static final String PRD_NO_IS_EMPTY = "예약 상품번호 전달이 누락되었습니다.";
    public static final String WRHS_PLN_DY_IS_EMPTY = "입고예정일자 전달이 누락되었습니다.";
    public static final String WRHS_DY_IS_EMPTY = "입고일자 전달이 누락되었습니다.";
    public static final String UPDATE_DT_IS_EMPTY = "수정일자 전달이 누락되었습니다.";
    public static final String PRD_TYPE_CD_IS_EMPTY = "상품구분코드 전달이 누락되었습니다.";
    public static final String SERVICE_VOUCHER_TYPE_NOT_ALLOWED = "서비스이용권상품 유형의 상품은 허용되지 않습니다." ;
    public static final String SEL_MTHD_CD_IS_EMPTY = "판매방식코드 전달이 누락되었습니다.";
    public static final String SEL_MTHD_CD_IS_NOT_PLN = "예약상품이 아닌 판매상품 코드가 전달 되었습니다.";
}
