package com.elevenst.terroir.product.registration.cert.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertInfoVO;
import com.elevenst.terroir.product.registration.cert.vo.ProductCertParamVO;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class CertValidateTest {

    @Autowired
    private CertService certService;

    @Test
    public void checkProductCertVO() {

        ProductCertParamVO vo = new ProductCertParamVO();
        List<String> certTypeList = new ArrayList<String>();
        List<String> certKeyList = new ArrayList<String>();

        vo.setDispCtgrNo(1011752);
        vo.setCertInfoCnt(1);
        //1
        certTypeList.add("101");
        certKeyList.add("CB065R0152-7002");
        //2
//        certTypeList.add("105");
//        certKeyList.add("KCC-REI-PKC-DEH-1550UB");
        //3(고의적 에러)
//        certTypeList.add("124");
//        certKeyList.add(null);
        //4(고의적 에러)
//        certTypeList.add("인증유형 길이를 10byte이상으로 늘리면 메시지가 떠야 함.");
//        certKeyList.add(null);
        //5(고의적 에러)
//        certTypeList.add("");
//        certKeyList.add("인증유형 길이를 100byte이상으로 늘려보자. 11234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 234567890 ");

        vo.setCertTypeCd(certTypeList);
        vo.setCertKey(certKeyList);

        String result = StringUtil.printFieldsToString(vo);
        log.info(result);

        List<ProductCertInfoVO> resultVO = certService.setProductCertVOList(vo);
        for(int i = 0 ; i <resultVO.size() ; i++) {
            result = StringUtil.printFieldsToString(resultVO.get(i));
            log.info(result);
        }

    }


    @Test
    public void checkRemoveCertElemTest() {

        List<ProductCertInfoVO> newCertList = new ArrayList<>();

        ProductCertInfoVO elem1 = new ProductCertInfoVO();
        elem1.setPrdNo(   1);
        elem1.setCertType("type1");
        elem1.setCertKey( "key1");
        newCertList.add(elem1);

        ProductCertInfoVO elem2 = new ProductCertInfoVO();
        elem2.setPrdNo(   2);
        elem2.setCertType("type2");
        elem2.setCertKey( "key2");
        newCertList.add(elem2);

        ProductCertInfoVO elem3 = new ProductCertInfoVO();
        elem3.setPrdNo(   3);
        elem3.setCertType("type3");
        elem3.setCertKey( "key3");
        newCertList.add(elem3);

        ProductCertInfoVO elem4 = new ProductCertInfoVO();
        elem4.setPrdNo(   4);
        elem4.setCertType("type4");
        elem4.setCertKey( "key4");
        newCertList.add(elem4);

        ///////////////////////////////////////////////////
        List<ProductCertInfoVO> oldCertList = new ArrayList<>();

        ProductCertInfoVO elem5 = new ProductCertInfoVO();
        elem5.setPrdNo(   1);
        elem5.setCertType("type1");
        elem5.setCertKey( "key1");
        oldCertList.add(elem5);

        ProductCertInfoVO elem6 = new ProductCertInfoVO();
        elem6.setPrdNo(   2);
        elem6.setCertType("type2");
        elem6.setCertKey( "key2");
        oldCertList.add(elem6);

        ProductCertInfoVO elem7 = new ProductCertInfoVO();
        elem7.setPrdNo(   7);
        elem7.setCertType("type7");
        elem7.setCertKey( "key7");
        oldCertList.add(elem7);

        List<ProductCertInfoVO> newCertList2 = new ArrayList<ProductCertInfoVO>(newCertList);
        List<ProductCertInfoVO> oldCertList2 = new ArrayList<ProductCertInfoVO>(oldCertList);

        newCertList.removeAll(oldCertList);
        for(ProductCertInfoVO elem : newCertList) {
            log.debug("product number : " + elem.getPrdNo());
            log.debug("Cert Type : " + elem.getCertType());
            log.debug("Cert Key : " + elem.getCertKey());
            log.debug("====================");
        }

        log.debug("**********************************************");

        oldCertList2.removeAll(newCertList2);
        for(ProductCertInfoVO elem : oldCertList2) {
            log.debug("product number : " + elem.getPrdNo());
            log.debug("Cert Type : " + elem.getCertType());
            log.debug("Cert Key : " + elem.getCertKey());
            log.debug("====================");
        }

    }

    @Test
    public void mergeProductCertInfoWithExistedInfo() {
        ProductVO productVO = new ProductVO();
        ProductCertInfoVO productCertInfoVO = new ProductCertInfoVO();
        productCertInfoVO.setCertKey("TEST");
        productCertInfoVO.setCertType("107");
        productCertInfoVO.setCertAuth("");

        List<ProductCertInfoVO> newProductCertInfoVOList = new ArrayList<>();
        newProductCertInfoVOList.add(productCertInfoVO);
        productVO.setProductCertInfoVOList(newProductCertInfoVOList);

        BaseVO baseVO = new BaseVO();
        productVO.setBaseVO(baseVO);
        productVO.setPrdNo(888888888);

        certService.mergeProductCertInfoWithExistedInfo(productVO);
    }

}
