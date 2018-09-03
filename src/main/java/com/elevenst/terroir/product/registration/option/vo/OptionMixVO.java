package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OptionMixVO implements Serializable {
    private static final long serialVersionUID = -6940362192220331787L;

    private List<String> colValue1 = new ArrayList<String>();
    private List<String> colValue2 = new ArrayList<String>();
    private List<String> colValue3 = new ArrayList<String>();
    private List<String> colValue4 = new ArrayList<String>();
    private List<String> colValue5 = new ArrayList<String>();
    private List<String> colCount = new ArrayList<String>();
    private List<String> oriColCount = new ArrayList<String>();
    private List<String> colOptPrice = new ArrayList<String>();
    private List<String> prdStckStatCd = new ArrayList<String>();
    private List<String> colOptWght = new ArrayList<String>();
    private List<String> colSellerStockCd = new ArrayList<String>();
    private List<String> barCode = new ArrayList<String>();
    private List<String> colPuchPrc = new ArrayList<String>();
    private List<String> colMrgnAmt = new ArrayList<String>();
    private List<String> colMrgnRt = new ArrayList<String>();
    private List<String> colAprvStatCd = new ArrayList<String>();
    private List<String> colCstmAplPrc = new ArrayList<String>();

    private List<String> colSetTypCd = new ArrayList<String>();
    private List<String> colCompInfoJson = new ArrayList<String>();
    private List<String> colCtlgNo = new ArrayList<String>();
    private List<String> colAddDesc = new ArrayList<String>();
    private List<String> colSpplBnftCd = new ArrayList<String>();

    private List<String> groupNumber = new ArrayList<String>();
    private List<String> summaryImage = new ArrayList<String>();
    private List<String> summaryImageStr = new ArrayList<String>();
    private List<String> detailImage = new ArrayList<String>();
    private List<String> detailImageStr = new ArrayList<String>();
}
