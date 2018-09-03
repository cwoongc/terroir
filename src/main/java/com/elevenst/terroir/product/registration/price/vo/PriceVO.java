package com.elevenst.terroir.product.registration.price.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PriceVO implements Serializable {

    private static final long serialVersionUID = -1666996028893072703L;

    private long prdPrcNo = 0;
    private long prdNo = 0;
    private long evntNo = 0;
    private String aplEndDy = "";
    private String aplBgnDy = "";
    private long selPrc = 0;
    private long buylCnPrc = 0;
    private long dscAmt = 0;
    private double dscRt = 0;
    private long mrgnRt = 0;
    private long cupnUseLmtQty = 0;
    private String cupnDscMthdCd = "";
    private long maktPrc = 0;
    private String mrgnPolicyCd = "";
    private long puchPrc = 0;
    private long mrgnAmt = 0;
    private long frSelPrc = 0;
    private String currencyCd = "";
    private long cstmAplPrc = 0;  //신고가이나 cent단위임 그러므로 화면에서 넘어오는 double형은 밑에 prdCstmAplPrc 셋팅
    private long paidSelPrc = 0;
    private long prmtDscAmt = 0;
    private String mpContractTypCd = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;


    //기본즉시할인 설정유무   //기본즉시할인 적용여부
    private String cuponCheckYn = "N";

    //기본즉시할인 쿠폰지급기간 설정 유무 / 기본즉시할인기간 설정여부
    private String cupnUseLmtDyYn = "N";
    //복수구매 (pludsc ) 할인 설정 유무
    private boolean usePlusDsc = false;

    private List<PlusDscVO> plusDscVOList;

    private String issCnBgnDt = "";// 기본즉시할인 시작일
    private String issCnEndDt = "";// 기본즉시할인 종료일

    private long soCupnAmt;
    private double prdCstmAplPrc; //화면에서 넘어오는 저장용
    private long aplDcSelPrc;
    private long eventNo = 0;
    private long mobilePhoneInstallment = 0;    //단말기 출고가 (저장시 -> maktPrc로 저장)

}
