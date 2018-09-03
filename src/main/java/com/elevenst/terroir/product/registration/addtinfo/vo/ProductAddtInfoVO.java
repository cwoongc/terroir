package com.elevenst.terroir.product.registration.addtinfo.vo;

import lombok.Data;

import java.io.Serializable;

/* Fixme
import net.sf.json.JSONObject;
import java.util.List;
*/

/**
 * 상품부가정보
 */
@Data
public class ProductAddtInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 상품번호
     */
    private long PrdNo;

    /**
     * 부가정보구분코드(PD248)
     */
    private String addtInfoClfCd;

    /**
     * 부가정보내용
     */
    private String addtInfoCont;

    /**
     * 생성일시
     */
    private long createNo;

    /**
     * 생성자
     */
    private String createDt;

    /**
     * 수정자
     */
    private long updateNo;

    /**
     * 수정일시
     */
    private String updateDt;

    private String CommonDtlsComNm;

    public ProductAddtInfoVO() {
    }

    /* net.sf.json.lib.JSONObject를 직접 다루는 로직은 제거할 예정. Fixme
    public ProductAddtInfoVO(JSONObject json) {
        if (json.has("PrdNo"))
            this.PrdNo = json.getLong("PrdNo");
        if (json.has("addtInfoClfCd"))
            this.addtInfoClfCd = json.getString("addtInfoClfCd");
        if (json.has("addtInfoCont"))
            this.addtInfoCont = json.getString("addtInfoCont");
        if (json.has("CommonDtlsComNm"))
            this.CommonDtlsComNm = json.getString("CommonDtlsComNm");
    }
    */

    /* 부가정보구분코드명(addInfoClfCdNm)을 저장해두었다가 역으로 코드캐쉬에서 부가정보구분코드를 꺼내오는 로직도 제거할 예정 Fixme


    public String Name2ClfCd(String DtlsName) {

        List<CodeDetail> detail = TmallCommonCode.searchListDetailByDtlsComNm(ProductConstDef.COMMON_CODE_PRD_ADDT_INFO, DtlsName);
        if (detail.size() > 0) {
            return detail.get(0).getDetailCode();
        }
        return "";
    }

    public String Name2ClfCd() {
        return Name2ClfCd(this.CommonDtlsComNm);
    }
    */
}
