package com.elevenst.terroir.product.registration.registration.service;

import com.elevenst.common.vo.SyAppMngVO;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FDSThreadServiceImpl {

    @Autowired
    CommonServiceImpl commonService;

    public void sendFDSForProduct(ProductVO preProductVO, ProductVO productVO) throws ProductException{


        if( "Y".equals(productVO.getIsOpenApi())) {
            return;
        }

        SyAppMngVO syAppMngVO = new SyAppMngVO();
        syAppMngVO.addKey(ProductConstDef.SWITCH_FDS_MASTER);
        syAppMngVO.addKey(ProductConstDef.SWITCH_PRD_FDS);

        commonService.getValidAppList(syAppMngVO);

        if( !syAppMngVO.isExist(ProductConstDef.SWITCH_FDS_MASTER) || !syAppMngVO.isExist(ProductConstDef.SWITCH_PRD_FDS) ) {
            if( log.isWarnEnabled() ) {
                log.warn("Switch is off");
            }
            return;
        }

        // 상품정보 FDS 전송
        ProductFDSThread fdsThread = new ProductFDSThread(preProductVO, productVO);
        fdsThread.start();
    }
}
