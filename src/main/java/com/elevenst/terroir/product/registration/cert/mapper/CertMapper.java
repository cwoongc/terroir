package com.elevenst.terroir.product.registration.cert.mapper;

import com.elevenst.terroir.product.registration.cert.vo.ProductCertInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CertMapper {

    List<Map<String, Object>> getProductCertInfo(long ctgrNo);

    List<Map<String, Object>> getProductCertInfoCamelCase(long ctgrNo);

    List<ProductCertInfoVO> getProductCertList(long prdNo);

    long updateCertInfoUseToN(ProductCertInfoVO elem);

    long insertNewCertInfo(ProductCertInfoVO elem);

    String getMemberCertType(long memNo);
}
