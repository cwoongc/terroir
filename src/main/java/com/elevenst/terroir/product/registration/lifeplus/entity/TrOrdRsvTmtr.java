package com.elevenst.terroir.product.registration.lifeplus.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrOrdRsvTmtr implements Serializable {
    private static final long serialVersionUID = -1519765246035239088L;

    private long rsvNo;
    private long rsvTmtr;
    private String rsvCnfrmDt;
    private String rsvStatCd;
    private String chgrNm;
    private long ptnrEmpNo;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;
}
