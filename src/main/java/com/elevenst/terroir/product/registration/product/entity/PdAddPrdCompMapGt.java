package com.elevenst.terroir.product.registration.product.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdAddPrdCompMapGt implements Serializable{

    private long asisPrdCompNo;
    private long asisPrdNo;

    private long asisCompPrdNo;
    private long asisPrdStckNo;

    private long tobePrdCompNo;
    private long tobePrdNo;

    private long tobeCompPrdNo;
    private long tobePrdStckNo;
}
