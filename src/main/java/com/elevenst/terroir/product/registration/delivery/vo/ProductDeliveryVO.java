package com.elevenst.terroir.product.registration.delivery.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDeliveryVO implements Serializable {
    private static final long serialVersionUID = 5131740649710034497L;
    private long prdNo = 0;
    private long createNo = 0;
    private long updateNo = 0;
    private long selMnbdNo = 0;
    private long dispCtgrNo = 0;

    private String reglDlvTypCd = null;
    private String reglDlvTypCdNm = null;
    private String reglDlvMthdCd = null;
    private String reglDlvMthdCdNm = null;
    private String imdtDlvYn = null;
    private String reglDlvCnWkdy = null;
    private String reglDlvCnWkdyNm = null;
    private String createDt = null;
    private String updateDt = null;
    private String chkPrdNo = null;
    private String prdNm = null;
    private String reglDlvYn = null;
    private String reglAuthSellerYn = null;
    private String resultMsg = null;
    private String mode = null;
    private String prdTypCd = null;
    private String optTypCd = null;
    private String inputOptYn = null;
    private String reglDlvDy = null;
    private String useYn = null;
    private String existYn = null;

    private String[] prdNoArr = null;
}
