package com.elevenst.terroir.product.registration.wms.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpVO implements Serializable {
    private static final long serialVersionUID = 8719612473915225934L;

    private long    dispCtgrNo;
    private String  dispCtgrNm;
    private String  dispCtgr1Nm;
    private String  empNo;
    private String  empNm;
    private String  empId;
    private String  email;
    private String  gnrlTlphnNo;
    private String  searchType;

    private int start=1;
    private int limit=10;
    private String totalCount;

    /** 검색조건1	*/
    private String searchText1;
    /** 검색조건2	*/
    private String searchText2;
    /** 검색조건3	*/
    private String searchText3;
}
