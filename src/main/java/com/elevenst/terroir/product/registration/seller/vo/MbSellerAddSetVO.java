package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MbSellerAddSetVO implements Serializable {
    private static final long serialVersionUID = -5008533233546667316L;

    private long memNo;
    private Date addSetHistDt;
    private String pay11UseYn;
    private long pay11PrdNo;
    private long pay11StockNo;
    private Date pay11UpdateDt;
    private String ctgrChngAuthYn;
    private long ctgrChngAuthEmpNo;
    private Date ctgrChngAuthDt;
    private String ovseSellSuptUseYn;
    private Date ovseSellSuptUseCrdt;
    private Date ovseSellSuptUseUpdt;
    private long ovseSellSuptUseChngNo;
    private String optSvcCd;
    private String sortCol = "";
    private String ovseSellAprvYn = "N";
    private Date ovseSellAprvRegDt;
    private Date ovseSellAprvDelDt;
    private long ovseSellAprvEmpNo;
}
