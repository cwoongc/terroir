package com.elevenst.terroir.product.registration.cert.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCertInfoVO implements Serializable {

    private long prdNo;
    private String certNo;
    private String certType;
    private String certTypeNm;
    private String certTypeEngNm;
    private String certAuth;
    private String certKey;
    private String useYn;
    private String kcMarkYn;
    private String kcMarkColor;

    private String updateDt;
    private String createDt;
    private long updateNo;
    private long createNo;
    private String dispCtgrNo;


    @Override
    public boolean equals(Object o) {

        if(o == null) {
            return false;
        }
        ProductCertInfoVO param= (ProductCertInfoVO)o;

        if(certType == null && param.getCertType() == null &&
            certKey == null && param.getCertKey() == null) { //객체가 null이 아닌데 모든 엘리먼트가 null이면 true 로 간주
            return true;
        }

        if ((certType != null && certType.equals(param.getCertType())) &&
            (certKey != null  && certKey.equals( param.getCertKey()))) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (certType + certKey).hashCode();
    }
}
