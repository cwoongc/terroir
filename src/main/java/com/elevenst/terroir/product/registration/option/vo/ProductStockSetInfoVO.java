package com.elevenst.terroir.product.registration.option.vo;

import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductStockSetInfoVO implements Serializable {

    private static final long serialVersionUID = -7549861024629069365L;

    private long mstPrdNo;		// 세트 상품의 상품번호
    private long compPrdNo;		// 구성된 재고의 상품번호
    private long mstStckNo;		// 세트 상품의 재고번호
    private long compStckNo;	// 구성된 재고번호
    private double setDscRt;	// 세트 할인율
    private int setUnitQty;		// 세트 단위수량
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;

    private String bsnDealClf = ProductConstDef.BSN_DEAL_CLF_COMMISSION;
    private String optionMixNm;		// 옵션명:옵션값 조합
    private String prdNm;			// 상품명
    private String prdSelStatCd;	// 상품판매상태
    private long selMnbdNo;			// 판매자 일렬번호
    private long acctUntprc;		// 회계원가(회계단가:월펼균단가)
    private long addedSelPrc;		// 구성된 재고의 판매가+옵션추가금
    private long stckQty;			// 구성된 재고의 수량
    private long puchPrc;			// 매입가
    private long avgUntprc;			// 이동평균단가(일평균단가)

    private String chgTypeCd = "I";	// 구성 정보 변경 이력 (I:등록, U:수정, D:삭제, MU:단독변경)
}
