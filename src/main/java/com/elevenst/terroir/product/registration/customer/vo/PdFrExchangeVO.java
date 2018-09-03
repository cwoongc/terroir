package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdFrExchangeVO implements Serializable {
    private static final long serialVersionUID = -8126678922365588210L;

    public String currencyCd;
    private String currencyNm;
    public double exAmt;
    private String frSellerYN;
    private String endDt;
    private String startDt;
    public double outExAmt;
    public String exchangeDt;//
    public String exTyp;
    public String countryNm;
    public int countryIdx;
}
