package com.elevenst.terroir.product.registration.desc.vo;

import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDescVO implements Serializable{
    private static final long serialVersionUID = 354672571708915160L;

    private long prdDescNo;
    private long prdNo;
    private String prdDescTypCd;
    private String prdDescContVc;
    private String prdDescContClob;
    private String prdIntroDescContClob;
    private String prdOutroDescContClob;
    private String clobTypYn;
    private String createDt;
    private long createNo;
    private String updateDt;
    private long updateNo;
    private Long reRegPrdNo;
    private long splitedCnt;
    private String splitedUrl;
    private String prdDtlTypCd;		// 상품 상세 구분 코드 (PD160)
    private String mode;
    private List<String> prdDescTypCdList;

    public ProductDescVO() {}

    public ProductDescVO (long reRegPrdNo, long newPrdNo) {
        this.reRegPrdNo = reRegPrdNo;
        this.prdNo =  newPrdNo;
    }

    public ProductDescVO(long prdNo, String prdDescTypCd) {
        this.prdNo = prdNo;
        this.prdDescTypCd = prdDescTypCd;
    }

    public ProductDescVO(long prdNo, String prdDescTypCd, List<String> prdDescTypCdList) {
        this.prdNo = prdNo;
        this.prdDescTypCd = prdDescTypCd;
        this.prdDescTypCdList = prdDescTypCdList;
    }

    public ProductDescVO(long prdDescNo, long prdNo, String prdDescTypCd, String prdDescContVc, String prdDescContClob, String prdIntroDescContClob, String prdOutroDescContClob, String clobTypYn, String createDt, long createNo, String updateDt, long updateNo, Long reRegPrdNo, long splitedCnt, String splitedUrl, String prdDtlTypCd, String mode, List<String> prdDescTypCdList) {
        this.prdDescNo = prdDescNo;
        this.prdNo = prdNo;
        this.prdDescTypCd = prdDescTypCd;
        this.prdDescContVc = prdDescContVc;
        this.prdDescContClob = prdDescContClob;
        this.prdIntroDescContClob = prdIntroDescContClob;
        this.prdOutroDescContClob = prdOutroDescContClob;
        this.clobTypYn = clobTypYn;
        this.createDt = createDt;
        this.createNo = createNo;
        this.updateDt = updateDt;
        this.updateNo = updateNo;
        this.reRegPrdNo = reRegPrdNo;
        this.splitedCnt = splitedCnt;
        this.splitedUrl = splitedUrl;
        this.prdDtlTypCd = prdDtlTypCd;
        this.mode = mode;
        this.prdDescTypCdList = prdDescTypCdList;
    }

    public static ProductDescVO convertFromPdPrdDesc(PdPrdDesc from, String prdIntroDescContClob, String prdOutroDescContClob, Long reRegPrdNo, List<String> prdDescTypCdList) {
        return new ProductDescVO(
                from.getPrdDescNo(),
                from.getPrdNo(),
                from.getPrdDescTypCd(),
                from.getPrdDescContVc(),
                from.getPrdDescContClob(),
                prdIntroDescContClob,
                prdOutroDescContClob,
                from.getClobTypYn(),
                from.getCreateDt(),
                from.getCreateNo(),
                from.getUpdateDt(),
                from.getUpdateNo(),
                reRegPrdNo,
                from.getSplitedCnt(),
                from.getSplitedUrl(),
                from.getPrdDtlTypCd(),
                from.getMode(),
                prdDescTypCdList
        );
    }

    public static ProductDescVO convertFromPdPrdDesc(PdPrdDesc from) {
        return ProductDescVO.convertFromPdPrdDesc(from,null,null, null, null);
    }

    public String getPrdDescContClob() {
        return (prdDescContClob == null) ? "" : prdDescContClob;
    }

    public String getPrdDescContVc() {
        return (prdDescContVc == null) ? "" : prdDescContVc;
    }

    public boolean isSmartOptionTypCd(){
        return ProductConstDef.SMART_OPTION_TYPE.contains(prdDtlTypCd);
    }

    public boolean isDesignSmartOptionTypCd(){
        return ProductConstDef.DESIGN_SMART_OPTION_TYPE.contains(prdDtlTypCd);
    }

    public boolean isAllSmartOptionTypCd(){
        return ProductConstDef.ALL_SMART_OPTION_TYPE.contains(prdDtlTypCd);
    }


}
