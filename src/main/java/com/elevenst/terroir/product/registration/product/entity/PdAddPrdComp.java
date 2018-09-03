package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PdAddPrdComp implements Serializable {
    private long	PrdCompNo;
    private long	MainPrdNo;
    private long	CompPrdNo;
    private long	CompPrdStckNo;
    private long	EvntNo;
    private Date	AplBgnDy;
    private Date	AplEndDy;
    private String	MainPrdYn;
    private long	SelPrc;
    private long	AddCompPrc;
    private long	CompPrdQty;
    private long	DispPrrtRnk;
    private String	PrdCompTypCd;
    private long	StckQty;
    private long	SelQty;
    private String	UseYn;
    private Date	CreateDt;
    private long	CreateNo;
    private Date UpdateDt;
    private long	UpdateNo;
    private long	AddPrdGrpNo;
    private long	CstmAplPrc;
    private String	CompPrdNm;
}
