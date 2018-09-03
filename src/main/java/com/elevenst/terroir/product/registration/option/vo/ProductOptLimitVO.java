package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProductOptLimitVO implements Serializable {
    private static final long serialVersionUID = -5353571176523206289L;

    private long optLmtObjNo;
    private String optLmtClfCd;
    private String optLmtClfNm;
    private long dispCtgrNo;
    private long combOptNmCnt;
    private long combOptValueCnt;
    private long combOptCnt;
    private long idepOptNmCnt;
    private long idepOptValueCnt;
    private long writeOptNmCnt;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;

    private long sellerNo;
    private String sellerId;
    private String sellerNm;
    private String dispCtgrNm;
    private long prdNo;
    private String prdNm;
    private String[] optLmtObjNoArr;
    private List<String> sellerIdList = new ArrayList<String>();
    private List<Long> optLmtObjNoList = new ArrayList<Long>();
    private List<Long> dispCtgrNoList = new ArrayList<Long>();
    private List<String> optLmtClfCdList = new ArrayList<String>();
    private List<String> optLmtSellerIdList = new ArrayList<String>();
    private String onlyOneRowYN;
    private long totalCount;
    private String createDtStr;
    private String updateDtStr;
    private String empId;
    private String empNm;
    private long prdDispCtgrNo;
    private String dispCtgr1Nm;
    private String dispCtgr2Nm;
    private String dispCtgr3Nm;
    private String procType;
    private String optLmtType;
    private long start;
    private long end;
    private long limit;
    private long level;
    private String mode;

    // 조합형 옵션 단수 별 옵션값 제한 개수
    private long combOptValCntNo1;
    private long combOptValCntNo2;
    private long combOptValCntNo3;
    private long combOptValCntNo4;
    private long combOptValCntNo5;

    private long prdNmLenLmt;
    private long prdNmLenLmtDefault;
    private String stdPrdYn;
}
