package com.elevenst.terroir.product.registration.category.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DpDispObjVO implements Serializable {
    private static final long serialVersionUID = 7700982801040709699L;

    private String dispSpceId;
    private int dispAdvrtNo;
    private int dispObjNo;
    private String dispObjClfCd;
    private String bnnrObjClfCd;
    private String dispObjStatCd;
    private Date rqstDt;
    private int rqstAmt;
    private Date rqstCnDt;
    private Date slctDt;
    private String cmStbyPrdYn;
    private int stbyPrrtRnk;
    private int dispPrrtRnk;
    private Date dispCnDt;
    private String dispObjLnkUrl;
    private String lnkBnnrImgUrl;
    private String dispObjLnkUrl2;
    private String lnkBnnrImgUrl2;
    private String dispVisibleWghtCd; // 노출비중
    private String plnDispLnkStmt;
    private Date createDt;
    private int createNo;
    private Date updateDt;
    private int updateNo;
    private String updateNm;
    private String dispObjCnCd;
    private String dispObjAutoRegCd;
    private String dispObjNm;
    private int dispObjQty;
    private int plnPrdQty; //스케쥴타입

    private String dispObjScheTypeCd;
    private Date dispObjBgnDy;
    private Date dispObjEndDy;
    private String dispObjBgnDt;
    private String dispObjEndDt;
    private String dispObjBgnTime;
    private String dispObjEndTime;
    private int dispObjWinWidth;
    private int dispObjWinHeight;
    private String dispObjHtml;
    private String tmpltType;
    private String sellerID;
    private String memberId;
    private String memberGrp;
}
