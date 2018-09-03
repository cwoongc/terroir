package com.elevenst.terroir.product.registration.lifeplus.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdRsvSchdlInfoVO implements Serializable {
    private static final long serialVersionUID = 4748897535375176662L;

    private String bgnDt;		//시작일시
    private String endDt;		//종료일시
    private String dtlDesc;		//상세설명
    private String wkdyCd;		//요일코드
    private String wkdyNm;		//요일이름(영문)
    private String weeknm;		//주차별 이름
    private String hhmm;		//시분
    private long rsvLmtQty;		//예약재고수량
}
