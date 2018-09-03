package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class NickNameVO implements Serializable {
    private static final long serialVersionUID = -3196399297354817455L;

    private int nickNMSeq;
    private String nickNM;
    private String delYN;
    private String baseNickNMYN;
    private long memNO;
    private Date createDT;
    private int createNO;
    private Date updateDT;
    private int updateNO;
    private int nckCnt;
    private String concatNckNM;
    private String nickNMImgUrl;
    private long prdNo;

    private List<Long> prdNoList = new ArrayList<Long>();
}
