package com.elevenst.terroir.product.registration.cert.thirdparty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 1003811 on 2017. 10. 13..
 */
@Slf4j
@Component
public class ProductNoCertValidApiService extends ProductCertValidApiService {

    @Override
    public boolean doRequestApi(String certType, String certKey) {
        //no cert. must return true.
        return true;
    }

    @Override
    public Map<String, Object> getCertInfo(String certType, String certKey) {
        return null;
    }
}
