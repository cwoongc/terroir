package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductNameMultiLangVO implements Serializable {
    private static final long serialVersionUID = 5775098464561140792L;

    private long         prdNo;							//상품번호
    private String 		 mtlglLangClfCd;				//언어타입(ML002)
    private String 		 sellerTrsltPrdNm;				//셀러입력 상품명
    private String 		 mchnTrsltPrdNm;				//기계번역된 상품명
    private String       createDt;
    private String       createDtTo;
    private long         createNo;
    private String       updateDt;
    private long         updateNo;
    private String 		 trsltYn;

    private String 		 sellerPrdNmEn="";
    private String 		 sellerPrdNmCn="";
    private String 		 sellerPrdNmEnPre; //상품페이지에서 쓰이는 수정하기 전 영문상품명
    private String 		 sellerPrdNmCnPre; //상품페이지에서 쓰이는 수정하기 전 중문상품명
    private String 		 mchnTrsltPrdNmEn;
    private String 		 mchnTrsltPrdNmCn;

    private String 		 trsltYnCn ="";
    private String 		 trsltYnEn ="";

    private long 		 histSeq;

    private long 		 copyPrdNo;						//상품 복사시 복사되는 상품번호
}
