package com.elevenst.terroir.product.registration.product.vo;

import com.elevenst.common.util.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductGroupPrdVO {
    /**
     *
     */
    private static final long serialVersionUID = -5521595584523309353L;

    private long prdGrpNo;
    private long prdNo;
    private long dispPrrt; // 순서
    private String repPrdYn; // 대표여부
    private String useYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;

    private String prdNm;
    private String prdImgPath = null;
    private String selStatCd;
    private String selMthdCd;
    private String usePeriod;
    private long selPrc;
    private String soMaxCupn;
    private String moMaxCupn;
    private String selBgnDy;
    private String selEndDy;
    private String selPrdClfCd;
    private String dispCtgrPath;
    private long dispCtgrNo;
    private long dispCtgr1No;
    private long dispCtgr2No;
    private long dispCtgr3No;
    private long dispCtgr4No;
    private String fnSellerPdDscInfo;
    private String minorSelCnYn;

    private String prdTypCd;
    private String mobile1WonYn;

    private String bcktExYn = ""; // 장바구니제한 여부
    private String dlvWyCd = ""; // 배송방법
    private String dlvCnAreaCd = ""; // 배송가능지역
    private String prdStatCd;
    private long selMnbdNo;
    private String mobileYn;
    private String sellerPrdCd;
    private int ctgrLevel;

    private long totalCount = 0;
    private int page;
    private int start = 1;
    private int end = 2;
    private int limit = 1;
    private List prdNoList = new ArrayList();
    private String searchType = null;

    // 화면 표시용
    private boolean warning;
    private boolean warningMsg;

    // 쿼리 분기용
    private String qryCondition = "";

    public void setFnSellerPdDscInfo(String fnSellerPdDscInfo) {
        this.fnSellerPdDscInfo = fnSellerPdDscInfo;

        if (StringUtil.isNotEmpty(fnSellerPdDscInfo)) {
            String[] fnSellerPdDscInfos = StringUtil.split(fnSellerPdDscInfo, "|");

            int infoLeng = fnSellerPdDscInfos.length;

            this.setSelPrc(Long.parseLong(fnSellerPdDscInfos[0]));
            if (!"NONE".equals(fnSellerPdDscInfos[1])) {
                if (fnSellerPdDscInfos[1] != null && fnSellerPdDscInfos[1].length() > 0 && ".".equals(fnSellerPdDscInfos[1].substring(0, 1))) {
                    this.setSoMaxCupn("0" + fnSellerPdDscInfos[1]);
                } else {
                    this.setSoMaxCupn(fnSellerPdDscInfos[1]);
                }
            }
            if (!"NONE".equals(fnSellerPdDscInfos[2])) {
                if (fnSellerPdDscInfos[2] != null && fnSellerPdDscInfos[2].length() > 0 && ".".equals(fnSellerPdDscInfos[2].substring(0, 1))) {
                    this.setMoMaxCupn("0" + fnSellerPdDscInfos[2]);
                } else {
                    this.setMoMaxCupn(fnSellerPdDscInfos[2]);
                }
            }

            this.setMobileYn(fnSellerPdDscInfos[11]);

        }
    }

    public void setPrdNoList(String[] chkPrdNo) {
        // this.chkPrdNo = chkPrdNo;
        String prdNo;
        for (int i = 0; i < chkPrdNo.length; i++) {
            if (chkPrdNo[i] != null) {
                prdNo = chkPrdNo[i].trim();
                if (StringUtil.isNotEmpty(prdNo))
                    this.prdNoList.add(prdNo);
            }

            // 최대 50개까지만
            if (this.prdNoList.size() >= 50)
                return;
        }
    }
}
