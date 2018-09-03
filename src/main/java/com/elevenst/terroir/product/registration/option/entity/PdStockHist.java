package com.elevenst.terroir.product.registration.option.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdStockHist implements Serializable {
    private static final long serialVersionUID = -286282138968318209L;

    private long stockNo;
    private long prdNo;
    private Date histAplBgnDt;
    private long optItemNo1;
    private long optValueNo1;
    private String optValueNm1;
    private long optItemNo2;
    private long optValueNo2;
    private String optValueNm2;
    private long optItemNo3;
    private long optValueNo3;
    private String optValueNm3;
    private long optItemNo4;
    private long optValueNo4;
    private String optValueNm4;
    private long optItemNo5;
    private long optValueNo5;
    private String optValueNm5;
    private long optItemNo6;
    private long optValueNo6;
    private String optValueNm6;
    private long optItemNo7;
    private long optValueNo7;
    private String optValueNm7;
    private long addPrc;
    private long stckQty;
    private String prdStckStatCd;
    private String useYn;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
    private long selQty;
    private String barCode;
    private long realStckQty;
    private long resvStckQty;
    private long tempStckQty;
    private long dfctStckQty;
    private long regRnk;
    private long optWght;
    private long cstmOptAplPrc;
    private long puchPrc;
    private long mrgnRt;
    private long mrgnAmt;
    private long ctlgNo;
    private String repOptYn;
    private String addDesc;
    private String spplBnftCd;
    private Date matchDt;
    private long matchNo;
    private String setTypCd;
    private long rntlCst;
}
