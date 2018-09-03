package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WmCustomsResultVO extends WmCustomsVO implements Serializable {
    private static final long serialVersionUID = -7136849951212542686L;

    private long prdDscPrc;		//할인모음가
    private long dlvCst;		//배송비
    private long insrCst;		//보험료
    private long taxableValue;	//과세가격
    private long cst;			//관세(Customs) = 과세가격 * 관세율
    private long ict;			//개별소비세(Individual consumption tax) = (과세가격 + 관세) * 소비세율
    private long rst;			//농특세(Special tax for rural development) = 개별소비세액 * 농특세율
    private long edt;			//교육세율(Education tax) = 개별소비세액 * 육세율
    private long vat;			//부가세율(Value added tax) = (과세가격 + 관세 + 개별세 + 교육세 + 교통세 + 농특세) * 부가세율
    private long totalCstVAT;	//총관부가세 = 관세 + 개별세 + 교육세 + 교통세 + 농특세 + 부가세
    private String applyDt;		//적용일자
    private String deliveryCd;	//출고지

    private boolean isCalc = false;
    private String cstmCalc;   //관세청 기준 관부과세 계산인지 아닌지 구분, 01: 관세청 기준 계산 , null 이면 기존 계산 공식

    public String getCstmCalc() {
        return cstmCalc;
    }
    public long getPrdDscPrc() {
        return prdDscPrc;
    }
    public long getDlvCst() {
        return dlvCst;
    }
    public long getInsrCst() {
        return insrCst;
    }
    public long getTaxableValue() {
        return taxableValue;
    }
    public long getCst() {
        this.calc();
        return this.cst;
    }
    public long getIct() {
        this.calc();
        return this.ict;
    }
    public long getRst() {
        this.calc();
        return this.rst;
    }
    public long getEdt() {
        this.calc();
        return edt;
    }
    public long getVat() {
        this.calc();
        return vat;
    }
    public long getTotalCstVAT() {
        this.calc();
        return totalCstVAT;
    }
    public String getApplyDt() {
        return applyDt;
    }

    public void calc()
    {
        if(isCalc) return;

        //관세(Customs:절삭) = 과세가격 * 관세율
        this.cst = (long)(taxableValue * this.getCstr() / 100) ;

        //개별소비세(Individual consumption tax:절삭) = (과세가격 + 관세) * 소비세율
        this.ict = (long)((taxableValue + this.cst) * this.getIctr() / 100);

        //농특세(Special tax for rural development:절삭) = 개별소비세액 * 농특세율
        this.rst = (long)(this.ict * this.getRstr() / 100);

        //교육세율(Education tax) = 개별소비세액 * 육세율
        this.edt = (long)(this.ict * this.getEdtr() / 100);

        //부가세율(Value added tax) = (과세가격 + 관세 + 개별세 + 교육세  + 농특세) * 부가세율
        this.vat = (long)((taxableValue + this.cst + this.ict + this.rst + this.edt) * this.getVatr() / 100);

        //관부가세
        //this.totalCstVAT = taxableValue + this.cst + this.ict + this.rst + this.edt + this.vat;
        this.totalCstVAT = (this.cst + this.ict + this.rst + this.edt + this.vat)/10 * 10;

        isCalc = true;
    }
    public String getDeliveryCd() {
        return deliveryCd;
    }
}
