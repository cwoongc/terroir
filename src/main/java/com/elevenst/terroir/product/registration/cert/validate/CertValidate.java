package com.elevenst.terroir.product.registration.cert.validate;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.cert.code.PD065_PrdCertTypeCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CertValidate {


    public boolean checkCertKeyNoNeed(String certType) throws ProductException {
        if(GroupCodeUtil.isContainsDtlsCd(certType,
                                          PD065_PrdCertTypeCd.PD065_124_LIFE_PRODUCER_SUITABLE,
                                          PD065_PrdCertTypeCd.PD065_127_ELECTRONIC_PRODUCER_SUITABLE,
                                          PD065_PrdCertTypeCd.PD065_130_CHILD_PRODUCER_SUITABLE,
                                          PD065_PrdCertTypeCd.PD065_131_NOT_APPLICABLE,
                                          PD065_PrdCertTypeCd.PD065_132_SEE_DETAILS)) {
            return true;
        }else {
            return false;
        }
    }

    public void checkCertInfo(String certType, String certKey, List<Map<String, Object>> certInfoList) throws ProductException{

        //certType이 DB("categotyDAOMap.getProductCertInfo")로부터 가져온 인증코드 중에 있는지 check
        boolean notExistCertType = true;
        for (Map elem : certInfoList) {
            String dtlsCd = (String)elem.get("DTLSCD");
            if (StringUtils.equals(certType, dtlsCd)) {
                notExistCertType = false;
                break;
            }
        }
        if(notExistCertType) throw new ProductException("유효하지 않은 인증유형 코드 입니다.");

        if (StringUtils.isEmpty(certKey) && !this.checkCertKeyNoNeed(certType)){
            throw new ProductException("인증번호는 반드시 입력하셔야 합니다.");
        }

        if(certType.getBytes().length > 10){
            throw new ProductException("인증유형은 10byte(한글5자,영문/숫자10자) 이하로 입력해야 합니다.");
        }

        if(certKey.getBytes().length > 100){
            throw new ProductException("인증번호는 100byte(한글50자,영문/숫자100자) 이하로 입력해야 합니다.");
        }
    }

    public void checkCertTypeInfo(ProductVO productVO) throws ProductException{
        if(ProductConstDef.CERT_TYPE_102.equals(productVO.getBaseVO().getCertTypCd())
            && StringUtil.isEmpty(productVO.getBaseVO().getSellerPrdCd())){
            throw new ProductException("판매자 상품코드는 필수 입력 입니다.");
        }
    }

}
