package com.elevenst.terroir.product.registration.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItgMemVO implements Serializable {

    private String itgMemID ;
    long    itgMemNO;
    long    selSuplMemNO;
    long    itgMemAddrBaseSeq;
    String  mbAddrLocation;
    Date    createDt;
}
