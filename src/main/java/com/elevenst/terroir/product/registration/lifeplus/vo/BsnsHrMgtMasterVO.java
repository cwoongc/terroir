package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BsnsHrMgtMasterVO implements Serializable {
    private static final long serialVersionUID = -1259295645766736013L;
    private long strNo;
    private long shopNo;
    private String saleBgnHm;
    private String saleEndHm;
    private String woffWkdyList;
    private String phldyWoffYn;
    private long rsvCanWk;
    private String rsvDisabCndtCd;
    private String rsvDisabHhCd;
    private String saleEndOrdCanYn;
    private String saleEndOrdCanHhCd;
    private long createNo;
    private long updateNo;

    private String exceptionAdd;
    private String specifiedDate;

    private List<BsnsHrMgtDetailExctVO> bsnsHrMgtDetailExctVO;
    private List<BsnsHrMgtDetailWoffVO> bsnsHrMgtDetailWoffVO;

    private String rsvCanStrDt;
    private String rsvCanEndDt;
    private String todaySaleBgnHm;
    private String solarDy;
    private String weekdy;
    private String saleTimeHm;
    private String grpCd;
    private String isToday;
    private List<String> saleTimeHmList;
    private String exctTimeList;
    private long prdNo;
    private int rsvQty;
    private int rsvLmtQty;
    private String saleEndNextBgnHm;
    private String reqTypCd;	//[TR205] 요청구분코드
    private String bgnDt;
    private String endDt;
    private String rsvLmtQtyInfo;
    private int sameSchdlCnt;

    private String[] woffWkdyArr;
}
