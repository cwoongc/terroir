package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;
import skt.tmall.conveyor.product.commondb.DatabaseType;
import skt.tmall.conveyor.product.commondb.annotation.Column;
import skt.tmall.conveyor.product.commondb.annotation.CommonDBBO;

import java.io.Serializable;
import java.util.Date;

@CommonDBBO(database = DatabaseType.LOGDB, table = "PD_PRD_LDNG_TM", author = "yskim", email = "yskim12@sk.com")
@Data
public class ProductLdngTmVO implements Serializable {
    private static final long serialVersionUID = -6963108735764082181L;

    @Column(name="PCID")
    private String pcId;
    @Column(name="MEM_NO")
    private long memNo;
    @Column(name="PROC_TYP")
    private String procTyp;
    @Column(name="BROW_TYP")
    private String browTyp;
    @Column(name="LDNG_TM")
    private float ldngTm;
    @Column(name="CREATE_DT")
    private Date createDt;
}
