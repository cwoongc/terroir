package com.elevenst.terroir.product.registration.shop.validate;

import com.elevenst.terroir.product.registration.shop.entity.PdTownShopBranchComp; //Fixme
import com.elevenst.terroir.product.registration.shop.code.ShopExampleCd; //Fixme
import com.elevenst.terroir.product.registration.shop.exception.ShopValidateException;
import com.elevenst.terroir.product.registration.shop.message.ShopValidateExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShopValidate {

    //Fixme
    public void validateInsertProductShopBranch(PdTownShopBranchComp pdTownShopBranchComp) {

    }

    //Fixme
    public void validateUpdateProductShopBranch(PdTownShopBranchComp pdTownShopBranchComp) {

    }

//    //Fixme
//    public void isExampleNameEmpty(PdTownShopBranchComp shopexampleEntityPdTown) {
//        String exampleName = shopexampleEntityPdTown.getExampleName();
//        if(exampleName == null || exampleName.isEmpty())
//            throwException(ShopValidateExceptionMessage.EXAMPLE_NAME_IS_EMPTY);
//    }
//
//    //Fixme
//    public void isExampleCdInvalid(PdTownShopBranchComp shopexampleEntityPdTown) {
//        String code = shopexampleEntityPdTown.getExampleCd();
//        if(code == null && code.isEmpty()) {
//
//            boolean exists = false;
//
//            for(ShopExampleCd exampleCd : ShopExampleCd.values()) { //Fixme
//                if(exampleCd.code.equals(code)) {
//                    exists = true;
//                    break;
//                }
//            }
//
//            if(!exists)
//                throwException(ShopValidateExceptionMessage.EXAMPLE_CD_IS_INVALID);
//        }
//    }


    private void throwException(String message) {
        ShopValidateException threx = new ShopValidateException(message);
        if(log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }



}
