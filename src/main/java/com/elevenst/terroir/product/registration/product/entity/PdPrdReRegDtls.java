package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdPrdReRegDtls implements Serializable {
    private static final long serialVersionUID = 8141250650236663381L;

    private long prdAutoPrcsNo = 0;
    private long exstPrdNo = 0;
    private String selPrdClfCd = "";
    private String autoPrcsIntrvlCd = "";
    private String autoPrcsTmtrCd = "";
    private String autoPrcsClfCd = "";
    private long autoPrcsedTmtr = 0;
    private String useYn = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
    private String cupnUseYn = "";
}
