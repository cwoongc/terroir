package com.elevenst.terroir.product.registration.coordi_prd.validate;

import com.elevenst.terroir.product.registration.coordi_prd.entity.PdPrdCoordi;
import com.elevenst.terroir.product.registration.coordi_prd.exception.CoordiProductValidateException;
import com.elevenst.terroir.product.registration.coordi_prd.mapper.CoordiProductMapper;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoordiProductValidate {

    @Autowired
    CoordiProductMapper coordiProductMapper;

    @Autowired
    ProductValidate productValidate;

//    //Fixme
    public void validateInsert(PdPrdCoordi pdPrdCoordi) {
//        isExampleNameEmpty(pdPrdCoordiProductexampleEntity);
//        isExampleCdInvalid(pdPrdCoordiProductexampleEntity);
    }
//
//    //Fixme
//    public void validateUpdate(PdPrdCoordi pdPrdCoordiProductexampleEntity) {
//        isExampleNameEmpty(pdPrdCoordiProductexampleEntity);
//        isExampleCdInvalid(pdPrdCoordiProductexampleEntity);
//    }
//
//    //Fixme
//    public void isExampleNameEmpty(PdPrdCoordi pdPrdCoordiProductexampleEntity) {
//        String exampleName = pdPrdCoordiProductexampleEntity.getPrdNo();
//        if(exampleName == null || exampleName.isEmpty())
//            throwException(CoordiProductValidateExceptionMessage.EXAMPLE_NAME_IS_EMPTY);
//    }
//
//    //Fixme
//    public void isExampleCdInvalid(PdPrdCoordi pdPrdCoordiProductexampleEntity) {
//        String code = pdPrdCoordiProductexampleEntity.getCoordiPrdNo();
//        if(code == null && code.isEmpty()) {
//
//            boolean exists = false;
//
//            for(CoordiProductExampleCd exampleCd : CoordiProductExampleCd.values()) { //Fixme
//                if(exampleCd.code.equals(code)) {
//                    exists = true;
//                    break;
//                }
//            }
//
//            if(!exists)
//                throwException(CoordiProductValidateExceptionMessage.EXAMPLE_CD_IS_INVALID);
//        }
//    }


    private void throwException(String message) {
        CoordiProductValidateException threx = new CoordiProductValidateException(message);
        if(log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }

    public boolean getMyCoordiPrdCnt(ProductVO productVO) throws CoordiProductValidateException{
        if(productVO.getProductCoordiVOList() == null || productVO.getProductCoordiVOList().size() == 0) return false;
        long cnt = coordiProductMapper.getMyCoordiPrdCnt(productVO);
        if(productVO.getProductCoordiVOList().size() == cnt){
            return true;
        }else{
            return false;
        }
    }

    public void checkCoodiProductInfo(ProductVO productVO) throws CoordiProductValidateException{
        if(productValidate.isUsePrdGrpTypCd(productVO)){
            if(productVO.getProductCoordiVOList() != null && productVO.getProductCoordiVOList().size() > 0){
                if(productVO.getProductCoordiVOList().size() > 10) throw new ProductException("연관 상품은 10개를 초과 할 수 없습니다.");
                if(!this.getMyCoordiPrdCnt(productVO)) throw new ProductException("해당 셀러의 상품이 아니거나 단일상품이 아닌게 존재합니다.");
            }
        }
        ;
    }



}
