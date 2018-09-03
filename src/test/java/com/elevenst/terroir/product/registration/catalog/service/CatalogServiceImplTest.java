package com.elevenst.terroir.product.registration.catalog.service;

import com.elevenst.terroir.product.registration.catalog.vo.PrdModelVO;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class CatalogServiceImplTest {

    @Autowired
    CatalogServiceImpl catalogServiceImpl;

    @Test
    public void getProductCtlgModelInfo() {
        HashMap paramMap = new HashMap();
        paramMap.put("prdNo", 1244984376);
        paramMap.put("optionPopupYn", "Y");
        catalogServiceImpl.getProductCtlgModelInfo(paramMap);
        paramMap.put("optionPopupYn", "N");
        catalogServiceImpl.getProductCtlgModelInfo(paramMap);
    }

    @Test
    public void setEnuriModelInsert() {

        ProductVO productVO = new ProductVO();
        BaseVO baseVO = new BaseVO();
        PrdModelVO prdModelVO = new PrdModelVO();

        baseVO.setPrdTypCd("01");
        prdModelVO.setModelNm("DIANAMORIU TD 343938");
        prdModelVO.setModelCd("SQ48979");
        prdModelVO.setCtlgModelNo(226922);
        prdModelVO.setMode("SIMPLE");
        productVO.setUpdateNo(10000276);
        productVO.setCreateNo(10000276);
        productVO.setPrdNo(7979797979L);
        productVO.setUpdateYn("N");
        productVO.setPrdModelVO(prdModelVO);
        productVO.setBaseVO(baseVO);
        catalogServiceImpl.setEnuriModel(productVO);
    }

    @Test
    public void setEnuriModelInsertModelNoNull() {

        ProductVO productVO = new ProductVO();
        BaseVO baseVO = new BaseVO();
        PrdModelVO prdModelVO = new PrdModelVO();

        baseVO.setPrdTypCd("01");
        prdModelVO.setModelNm("DIANAMORIU TD 343938");
        prdModelVO.setModelCd("SQ48979");
        productVO.setUpdateNo(10000276);
        productVO.setCreateNo(10000276);
        productVO.setPrdNo(7979797977L);
        productVO.setUpdateYn("N");
        productVO.setPrdModelVO(prdModelVO);
        productVO.setBaseVO(baseVO);
        catalogServiceImpl.setEnuriModel(productVO);
    }

    @Test
    public void setEnuriModelUpdate() {

        ProductVO productVO = new ProductVO();
        BaseVO baseVO = new BaseVO();
        PrdModelVO prdModelVO = new PrdModelVO();

        baseVO.setPrdTypCd("01");
        prdModelVO.setModelNm("다이나모 프리 (TD) [343938]");
        prdModelVO.setModelCd("SQ48979");
        prdModelVO.setCtlgModelNo(226922);
        prdModelVO.setMode("SIMPLE");
        productVO.setUpdateNo(10000276);
        productVO.setCreateNo(10000276);
        productVO.setPrdNo(1245257360);
        productVO.setUpdateYn("Y");
        productVO.setPrdModelVO(prdModelVO);
        productVO.setBaseVO(baseVO);
        catalogServiceImpl.setEnuriModel(productVO);
    }

    @Test
    public void setMatchCatalog() {
        ProductVO productVO = new ProductVO();
        productVO.setUpdateNo(10000276);
        productVO.setCreateNo(10000276);
        productVO.setPrdNo(88888888);
        productVO.setSelMnbdNo(10000276);

        BaseVO baseVO = new BaseVO();
        baseVO.setSellerPrdCd("MYCODE758634");
        productVO.setBaseVO(baseVO);

        catalogServiceImpl.setMatchCatalog(productVO);
    }
}
