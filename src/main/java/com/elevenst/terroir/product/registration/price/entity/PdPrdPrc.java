package com.elevenst.terroir.product.registration.price.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdPrc implements Serializable {

    private static final long serialVersionUID = -8427430226856549667L;

    //01. 상품가격번호
    private long prdPrcNo = 0;
    //02. 상품번호
    private long prdNo = 0;
    //03. 이벤트번호
    private long evntNo = 0;
    //04. 적용시작일자
    private String aplBgnDy = "";
    //05. 적용종료일자
    private String aplEndDy = "";
    //06. 판매가
    private long selPrc = 0;
    //07. 구매가능가
    private long buylCnPrc = 0;
    //08. 쿠폰할인방식코드
    private String cupnDscMthdCd = "";
    //09. 할인금액
    private long dscAmt = 0;
    //10. 할인비율
    private double dscRt = 0.0;
    //11. 쿠폰발급제한횟수
    private long cupnUseLmtQty = 0;
    //12. 쿠폰발급횟수
    private long cupnUseQty = 0;
    //13. 마진율
    private double mrgnRt = 0.0;
    //14. 사용여부
    private String useYn = "";
    //15. 등록일시
    private String createDt = "";
    //16. 등록자번호
    private long createNo = 0;
    //17. 수정일시
    private String updateDt = "";
    //18. 수정자번호
    private long updateNo = 0;
    //19. 최종할인가격
    private long finalDscPrc = 0;
    //20. 시중가
    private long maktPrc = 0;
    //21. 마진정책코드[PD073]
    private String mrgnPolicyCd = "";
    //22. 매입가
    private long puchPrc = 0;
    //23. 마진금액
    private long mrgnAmt = 0;
    //24. 해외 판매가
    private long frSelPrc = 0;
    //25. 통화코드
    private String currencyCd = "";
    //26. 관세청상품 신고가 (단위 : cent)
    private long cstmAplPrc = 0;
    //27. 구입당시판매가
    private long paidSelPrc = 0;
    //28. 프로모션 할인
    private long prmtDscAmt = 0;
    //29. 휴대폰 약정 유형 코드
    private String mpContractTypCd = "";


    private String histAplBgnDt = "";


}
