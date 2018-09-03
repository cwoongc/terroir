package com.elevenst.terroir.product.registration.option.message;

import com.elevenst.terroir.product.registration.option.code.OptionConstDef;

public class OptionServiceExceptionMessage {

    public static final String GET_OPTION_ITEM_LIST_ERROR = "옵션 아이템 List 조회 오류";
    public static final String GET_SMART_OPTION_REG_CNT_ERROR = "상품상세 스마트옵션 등록되어있는 요약이미지 개수 조회 오류";
    public static final String GET_MIX_OPTION_VALUE_ERROR = "조합형 옵션 아이템 상품옵션값 리스트 조회 오류";
    public static final String GET_STOCK_TOTAL_CNT_ERROR = "상품총재고수량 조회 오류";
    public static final String GET_IDEP_STOCK_CNT_ERROR = "독립형 옵션 개수 조회 오류";
    public static final String GET_LIVE_STOCK_CNT_ERROR = "판매중 재고 개수 조회 오류";
    public static final String GET_OPTION_CLF_CD_ERROR = "상품 옵션코드 조회 오류";
    public static final String GET_OPTION_CLF_TYP_CD_CNT_ERROR = "상품의 옵션항목구분(조합/독립/입력)에 등록된 개수 조회 오류";
    public static final String GET_PTNR_INFO_ERROR = "지정배송일 정보 조회 오류";
    public static final String GET_CALC_OPTION_ERROR = "계산형 옵션 정보 조회 오류";
    public static final String INSERT_SHOCKING_DEAL_CALC_OPTION_ERROR = "쇼킹딜 계산형 옵션 정보 등록 오류";
    public static final String DELETE_SHOCKING_DEAL_CALC_OPTION_ERROR = "쇼킹딜 계산형 옵션 정보 삭제 오류";
    public static final String UPDATE_SHOCKING_DEAL_CALC_OPTION_ERROR = "쇼킹딜 계산형 옵션 정보 수정 오류";
    public static final String INSERT_CALC_OPTION_ERROR = "계산형 옵션 정보 등록 오류";
    public static final String DELETE_CALC_OPTION_ERROR = "계산형 옵션 정보 삭제 오류";
    public static final String UPDATE_CALC_OPTION_ERROR = "계산형 옵션 정보 수정 오류";
    public static final String UPDATE_PURCHASE_INFO_ERROR = "매입상품 관련 옵션정보 수정 오류";
    public static final String NOT_EQUALS_WRITE_OPTION_CNT_ERROR = "작성형 옵션정보 수가 일치하지 않습니다.";
    public static final String OVER_WRITE_OPTION_CNT_TEN_ERROR = "작성형 옵션의 갯수가 10개가 넘었습니다.";
    public static final String SET_OPTION_DATA_ERROR = "옵션 데이타 조합중 오류가 발생하였습니다.\n데이타를 확인해 주세요.";

    public static final String UNKNOWN_USER_ERROR = "사용자 정보가 존재하지 않습니다.";
    public static final String MIX_OPTION_OVER_CNT_ERROR = "선택형 옵션명은 "+ OptionConstDef.MAX_OPTION_CNT+"개까지 입력가능합니다.";
    public static final String CANT_SAME_MIX_OPTION_CNT_ERROR = "조합형 옵션정보 수가 일치하지 않습니다.";
}
