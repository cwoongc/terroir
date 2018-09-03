package com.elevenst.terroir.product.registration.desc.validate;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.desc.code.PrdDescTypCd;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.exception.ProductDescValidateException;
import com.elevenst.terroir.product.registration.desc.message.ProductDescValidateExceptionMessage;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductDescValidate {

    public void isUpdateDescDataEmpty(List<ProductDescVO> productDescVOList) {
        if(productDescVOList == null || productDescVOList.isEmpty()) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.UPDATE_DESC_DATA_IS_EMPTY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }


    public void isPrdCopyYnEmpty(String prdCopyYn) {
        if(prdCopyYn == null || prdCopyYn.isEmpty()) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_COPY_YN_IS_EMPTY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

    }

    public void isPrdCopyYnY(String prdCopyYn) {
        if(!prdCopyYn.equals("Y")) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_COPY_YN_IS_NOT_Y);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void isPrdCopyYnN(String prdCopyYn) {
        if(prdCopyYn.equals("Y")) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_COPY_YN_IS_Y);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

    }

    public void isPrdTypCdEmpty(String prdTypCd) {
        if(prdTypCd == null || prdTypCd.isEmpty()) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_TYP_CD_IS_EMPTY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void isPrdTypeCd11Pay(String prdTypCd) {
        if(!prdTypCd.equals(PrdTypCd.ELEVEN_PAY.getDtlsCd())) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_TYP_CD_IS_NOT_ELEVEN_PAY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void isProductDescVOEmpty(ProductDescVO productDescVO) {
        if(productDescVO == null) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRODUCT_DESC_VO_IS_EMPTY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void isPrdTypeCdServiceVoucher(String prdTypCd) {
        if(!prdTypCd.equals(PrdTypCd.SERVICE_VOUCHER.getDtlsCd())) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_TYP_CD_IS_NOT_SERVICE_VOUCHER);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void isPrdTypeCdNonServiceVoucher(String prdTypCd) {
        if(prdTypCd.equals(PrdTypCd.SERVICE_VOUCHER.getDtlsCd())) {
            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.PRD_TYP_CD_IS_NOT_NON_SERVICE_VOUCHER);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }

    public void checkReturnExchangInfo(ProductVO productVO) throws ProductDescValidateException{
        if(productVO.getProductDescVOList() == null || productVO.getProductDescVOList().size() == 0){

            ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.DESC_DATA_IS_EMPTY);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }else{
            boolean isReturnExchangExist = false;
            boolean isASInfoExist = false;
            for(ProductDescVO  productDescVO : productVO.getProductDescVOList()){
                if(GroupCodeUtil.equalsDtlsCd(productDescVO.getPrdDescTypCd(), PrdDescTypCd.RETURN_EXCHANGE_INFO)){
                    if(StringUtil.isNotEmpty(productDescVO.getPrdDescContVc())){
                        isReturnExchangExist = true;
                    }
                }
                if(GroupCodeUtil.equalsDtlsCd(productDescVO.getPrdDescTypCd(), PrdDescTypCd.AS_INFO)){
                    if(StringUtil.isNotEmpty(productDescVO.getPrdDescContVc())){
                        isASInfoExist = true;
                    }
                }
            }
            if(!isReturnExchangExist){
                ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.RETURN_DESC_DATA_IS_EMPTY);
                if (log.isErrorEnabled()) {
                    log.error(threx.getMessage(), threx);
                }
                throw threx;
            }

            if(!isASInfoExist){
                ProductDescValidateException threx = new ProductDescValidateException(ProductDescValidateExceptionMessage.AS_DESC_DATA_IS_EMPTY);
                if (log.isErrorEnabled()) {
                    log.error(threx.getMessage(), threx);
                }
                throw threx;
            }
        }
    }


}
