package com.elevenst.terroir.product.registration.cert;

import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductCertValidApiService;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductElectroCertValidApiService;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductNoCertValidApiService;
import com.elevenst.terroir.product.registration.cert.thirdparty.ProductSafetyCertValidApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 *
 *  상품인증 API factory class
 *      실시간 상품인증 시, 특정 인증정보의 경우는 외부 API를 이용하여 인증 validate를 수행함.
 *      USAGE : ProductCertValidApiService svc = (ProductCertValidApiService)ProductCertApiFactory.createService("105", "인증키값입니다.false로나오겠죠");
 */
@Component
public class ProductCertFactory {

    @Autowired
    ProductElectroCertValidApiService productElectroCertValidApiService;
    @Autowired
    ProductNoCertValidApiService productNoCertValidApiService;
    @Autowired
    ProductSafetyCertValidApiService productSafetyCertValidApiService;

    private enum CertInfoApiType {
        ELECTROMAGNETIC("105"),
        SAFETY_CERT_CHILD("128"),
        SAFETY_CONFIRM_CHILD("129");


        private String certType;
        CertInfoApiType(String certType) {
            this.certType = certType;
        }

    }

    private static List<String> electroType = Arrays.asList(
        CertInfoApiType.ELECTROMAGNETIC.certType
    );

    private static List<String> safetyType = Arrays.asList(
        CertInfoApiType.SAFETY_CERT_CHILD.certType,
        CertInfoApiType.SAFETY_CONFIRM_CHILD.certType
    );


    public ProductCertValidApiService createService(String certType, String certKey) {

        ProductCertValidApiService result = null;

        validateCertTypeAndKey(certType, certKey);



        if (electroType.contains(certType)) {
            return productElectroCertValidApiService;
        }
        else if (safetyType.contains(certType)) {
            return productSafetyCertValidApiService;
        }
        else {
            return productNoCertValidApiService;
        }
    }


    private final String CANNOT_USE_CERT_TYPE_CHAR = "<>()#&'\"";
    public void validateCertTypeAndKey(String cetType, String certKey) {

        if (StringUtils.isEmpty(cetType)) {
            throw new ProductException("인증유형이 없습니다.");
        }
        if (StringUtils.containsAny(certKey, CANNOT_USE_CERT_TYPE_CHAR)) {
            throw new ProductException("등록 할 수 없는 문자가 포함되어 있습니다 인증에 해당하는 문자만 입력가능합니다.");
        }
    }

}
