package com.elevenst.terroir.product.registration.addtinfo.validate;

import com.elevenst.terroir.product.registration.addtinfo.code.AddtInfoClfCd;
import com.elevenst.terroir.product.registration.addtinfo.entity.PdPrdAddtInfo;
import com.elevenst.terroir.product.registration.addtinfo.exception.ProductAddtInfoValidateException;
import com.elevenst.terroir.product.registration.addtinfo.message.ProductAddtInfoValidateExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductAddtInfoValidate {

    public void validateInsert(PdPrdAddtInfo pdPrdAddtInfo) {
        isPrdNoEmpty(pdPrdAddtInfo);
        isAddtInfoClfCdEmpty(pdPrdAddtInfo);
        isAddtInfoClfCdInvalid(pdPrdAddtInfo);
        isAddtInfoContEmpty(pdPrdAddtInfo);
    }

    public void validateUpdate(PdPrdAddtInfo pdPrdAddtInfo) {
        isPrdNoEmpty(pdPrdAddtInfo);
        isAddtInfoClfCdEmpty(pdPrdAddtInfo);
        isAddtInfoClfCdInvalid(pdPrdAddtInfo);
        isAddtInfoContEmpty(pdPrdAddtInfo);
    }


    public void isPrdNoEmpty(PdPrdAddtInfo pdPrdAddtInfo) {
        if(pdPrdAddtInfo.getPrdNo() == 0)
            throwException(ProductAddtInfoValidateExceptionMessage.PRD_NO_IS_EMPTY);
    }

    public void isAddtInfoClfCdEmpty(PdPrdAddtInfo pdPrdAddtInfo) {
        String addtInfoClfCd = pdPrdAddtInfo.getAddtInfoClfCd();
        if(addtInfoClfCd == null || addtInfoClfCd.isEmpty())
            throwException(ProductAddtInfoValidateExceptionMessage.ADDT_INFO_CLF_CD_IS_EMPTY);
    }

    public void isAddtInfoClfCdInvalid(PdPrdAddtInfo pdPrdAddtInfo) {
        String code = pdPrdAddtInfo.getAddtInfoClfCd();
        if(code == null && code.isEmpty()) {

            boolean exists = false;

            for(AddtInfoClfCd addtInfoClfCd : AddtInfoClfCd.values()) {
                if(addtInfoClfCd.code.equals(code)) {
                    exists = true;
                    break;
                }
            }

            if(!exists)
                throwException(ProductAddtInfoValidateExceptionMessage.ADDT_INFO_CLF_CD_IS_INVALID);
        }
    }

    public void isAddtInfoContEmpty(PdPrdAddtInfo pdPrdAddtInfo) {
        String addtInfoCont = pdPrdAddtInfo.getAddtInfoCont();
        if(addtInfoCont == null || addtInfoCont.isEmpty())
            throwException(ProductAddtInfoValidateExceptionMessage.ADDT_INFO_CONT_IS_EMPTY);
    }




    private void throwException(String message) {
        ProductAddtInfoValidateException threx = new ProductAddtInfoValidateException(message);
        if(log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }



}
