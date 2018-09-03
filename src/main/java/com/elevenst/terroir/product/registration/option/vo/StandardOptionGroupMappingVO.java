package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StandardOptionGroupMappingVO implements Serializable {
    private static final long serialVersionUID = -5031441675006103265L;

    private long dispCtgrNo;
    private long stdOptGrpNo;
    private long stdOptNo;
    private long stdOptValNo;
    private String useYn;
    private long createNo;
    private Date createDt;
    private long updateNo;
    private Date updateDt;

    private long prdNo;
}
