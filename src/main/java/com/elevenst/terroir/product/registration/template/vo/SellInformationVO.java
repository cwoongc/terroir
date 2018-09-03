package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

@Data
public class SellInformationVO{

    private long    prdInfoTmpltNo;
    private String  dlvCnAreaCd;
    private String  dlvWyCd;
    private String  dlvCstPayTypCd;
    private String  dlvCstInfoCd;
    private long    dlvCst;
    private long    rtngdDlvCst;
    private long    exchDlvCst;
    private long    frDlvBasiAmt;
    private String  bndlDlvCnYn;
    private String  todayDlvCnYn;
    private String  appmtDyDlvCnYn;
    private long    sndPlnTrm;
    private String  rcptIsuCnYn;
    private long    createNo;
    private long    updateNo;
    private String  dlvCstInstBasiCd;
    private String  unDlvCnYn;
    private String  visitDlvYn;
    private String  dlvClf;
    private long 	abrdCnDlvCst;		// 해외 취소 배송비
    private String 	abrdInCd;			// 11번가 해외 입고 유형
    private long 	prdWght;			// 상품무게
    private long    jejuDlvCst;         //제주 추가 배송비.
    private long    islandDlvCst;       //도서산간 추가 배송비.
    private String  rtngdDlvCd;         // 반품/교환배송비참조코드(PD111) 01:왕복,02:편도
}
