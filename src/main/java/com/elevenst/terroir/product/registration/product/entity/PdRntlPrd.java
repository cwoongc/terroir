package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdRntlPrd implements Serializable{
    private static final long serialVersionUID = -8002935736086035504L;

    private long prdNo = 0;
    private String histDt = "";
    private String cstTermCd = "";
    private String freeInstYn = "";
    private String coCardBnft = "";
    private String instCstDesc = "";
    private String frgftDesc = "";
    private String etcInfo = "";
    private String useYn = "";
    private String createDt = "";
    private long createNo = 0;
    private String updateDt = "";
    private long updateNo = 0;
    private long minRntlCst = 0;
    private long maxRntlCst = 0;
}
