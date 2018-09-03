package com.elevenst.terroir.product.registration.seller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MbRoadShopVO implements Serializable {
    private static final long serialVersionUID = 4424143673853471956L;

    private long memNo;
    private Date histDt;
    private String enbrRqstStatCd;
    private String chgrNm;
    private String chgrCpno;
    private String chgrEmailAddr;
    private String shopImgUrl1;
    private String shopImgUrl2;
    private String shopImgUrl3;
    private String rqstMsgCont;
    private Date createDt;
    private long createNo;
    private Date updateDt;
    private long updateNo;

    public boolean isApproval() {
        return ("02".equals(enbrRqstStatCd) || "03".equals(enbrRqstStatCd)) ? true : false;
    }
}
