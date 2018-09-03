package com.elevenst.terroir.product.registration.cert.thirdparty;

import org.springframework.stereotype.Component;

import java.util.Map;


public abstract class ProductCertValidApiService {

//    protected String certType;
//    protected String certKey;

    public abstract boolean doRequestApi(String certType, String certKey);

    public abstract Map<String, Object> getCertInfo(String certType, String certKey);

}
