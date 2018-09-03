package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WmWghtDlvCstVO extends WmsVO implements Serializable {
    private static final long serialVersionUID = -6181845676851177304L;

    /**
     * 시작무게(Kg) 소수점이하 2자리
     */
    private double bgnWght;
    /**
     * 종료무게(Kg) 소수점이하 2자리
     */
    private double endWght;
    /**
     * 배송비(원)
     */
    private long dlvCst;

    /**
     * 신고가 합계 금액
     */
    private long totCstmAplPrc;
    private long cstmUndDlvCst;
    private long cstmOvrDlvCst;


    public long getCstmUndDlvCst() {
        return cstmUndDlvCst;
    }
    public void setCstmUndDlvCst(long cstmUndDlvCst) {
        this.cstmUndDlvCst = cstmUndDlvCst;
    }
    public long getCstmOvrDlvCst() {
        return cstmOvrDlvCst;
    }
    public void setCstmOvrDlvCst(long cstmOvrDlvCst) {
        this.cstmOvrDlvCst = cstmOvrDlvCst;
    }
    public long getTotCstmAplPrc() {
        return totCstmAplPrc;
    }
    public void setTotCstmAplPrc(long totCstmAplPrc) {
        this.totCstmAplPrc = totCstmAplPrc;
    }
    /**
     * @return the bgnWght
     */
    public double getBgnWght() {
        return bgnWght;
    }
    /**
     * @param bgnWght the bgnWght to set
     */
    public void setBgnWght(double bgnWght) {
        this.bgnWght = bgnWght;
    }
    /**
     * @return the endWght
     */
    public double getEndWght() {
        return endWght;
    }
    /**
     * @param endWght the endWght to set
     */
    public void setEndWght(double endWght) {
        this.endWght = endWght;
    }
    /**
     * @return the dlvCst
     */
    public long getDlvCst() {
        return dlvCst;
    }
    /**
     * @param dlvCst the dlvCst to set
     */
    public void setDlvCst(long dlvCst) {
        this.dlvCst = dlvCst;
    }
    /**
     * @param bgnWght the bgnWght to set
     */
    public void setBgnWghtByLbs(double bgnWght) {
        this.bgnWght = Math.round(bgnWght * 0.453592 * 100) / 100.0;
    }
    /**
     * @param endWght the endWght to set
     */
    public void setEndWghtByLbs(double endWght) {
        this.endWght = Math.round(endWght * 0.453592 * 100) / 100.0;
    }
    /**
     * @return the bgnWght
     */
    public double getBgnWghtByLbs() {
        return Math.round(this.bgnWght / 0.453592 * 100) / 100.0;
    }
    /**
     * @return the endWght
     */
    public double getEndWghtByLbs() {
        return Math.round(this.endWght / 0.453592 * 100) / 100.0;
    }
}
