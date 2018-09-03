package com.elevenst.terroir.product.registration.addtinfo.service;

import com.elevenst.common.util.CodeEnumUtil;
import com.elevenst.terroir.product.registration.addtinfo.code.AddtInfoClfCd;
import com.elevenst.terroir.product.registration.addtinfo.entity.PdPrdAddtInfo;
import com.elevenst.terroir.product.registration.addtinfo.mapper.ProductAddtInfoServiceTestMapper;
import com.elevenst.terroir.product.registration.product.entity.PdPrd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class ProductAddtInfoServiceTest {


    private static final long PRD_NO1 = 99999999991L;
    private static final long PRD_NO2 = 99999999992L;
    private static final long PRD_NO3 = 99999999993L;

    private static final String FACTORY_PRICE = "990000";
    private static final String DECLARED_SUPPORT_FUND = "350000";
    private static final String ADDITIONAL_SUPPORT_FUND = "50000";
    private static final String BROADCASTING_PRD_YN = "Y";
    private static final String OVERSEA_DELIVERY_CUSTOMS_YN = "N";

    private List<Long> prdNoList = new ArrayList<>();

    @Autowired
    private ProductAddtInfoServiceImpl productAddtInfoService;

    @Autowired
    private ProductAddtInfoServiceTestMapper productAddtInfoServiceTestMapper;



    @Before
    public void before() {

        prdNoList.clear();
        prdNoList.add(PRD_NO1);

        productAddtInfoServiceTestMapper.insertProductAddtInfo(new PdPrdAddtInfo(
                PRD_NO1
           ,AddtInfoClfCd.FACTORY_PRICE.code
           , FACTORY_PRICE
           ,0
           ,0
        ));

        productAddtInfoServiceTestMapper.insertProductAddtInfo(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.DECLARED_SUPPORT_FUND.code
                ,DECLARED_SUPPORT_FUND
                ,0
                ,0
        ));

        productAddtInfoServiceTestMapper.insertProductAddtInfo(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.ADDITIONAL_SUPPORT_FUND.code
                , ADDITIONAL_SUPPORT_FUND
                ,0
                ,0
        ));

        productAddtInfoServiceTestMapper.insertProductAddtInfo(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.OVERSEA_DELIVERY_CUSTOMS_YN.code
                , OVERSEA_DELIVERY_CUSTOMS_YN
                ,0
                ,0
        ));

    }



    @Test
    public void t010_getProductAddtInfoList() {

        List<PdPrdAddtInfo> pdPrdAddtInfoList = productAddtInfoService.getProductAddtInfoList(PRD_NO1);

        Assert.assertEquals(4, pdPrdAddtInfoList.size());


        for (PdPrdAddtInfo pdPrdAddtInfo : pdPrdAddtInfoList) {
            switch (CodeEnumUtil.getEnumOfFieldValue(
                    AddtInfoClfCd.class,
                    CodeEnumUtil.FieldName.code,
                    pdPrdAddtInfo.getAddtInfoClfCd()
            )) {
                case FACTORY_PRICE:
                    Assert.assertEquals(FACTORY_PRICE, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                case DECLARED_SUPPORT_FUND:
                    Assert.assertEquals(DECLARED_SUPPORT_FUND, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                case ADDITIONAL_SUPPORT_FUND:
                    Assert.assertEquals(ADDITIONAL_SUPPORT_FUND, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                case BROADCASTING_PRD_YN:

                    break;
                case OVERSEA_DELIVERY_CUSTOMS_YN:
                    Assert.assertEquals(OVERSEA_DELIVERY_CUSTOMS_YN, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                default:
                    break;
            }
        }
    }


    @Test
    public void t020_insertProductAddtInfoList() {

        prdNoList.add(PRD_NO2);

        List<PdPrdAddtInfo> pdPrdAddtInfoList = new ArrayList<>();

        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO2
                ,AddtInfoClfCd.FACTORY_PRICE.code
                , FACTORY_PRICE
                ,0
                ,0
        ));

        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO2
                ,AddtInfoClfCd.DECLARED_SUPPORT_FUND.code
                ,DECLARED_SUPPORT_FUND
                ,0
                ,0
        ));

        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO2
                ,AddtInfoClfCd.ADDITIONAL_SUPPORT_FUND.code
                , ADDITIONAL_SUPPORT_FUND
                ,0
                ,0
        ));


        int insertCnt = productAddtInfoService.insertProductAddtInfoList(pdPrdAddtInfoList);

        Assert.assertEquals(3, insertCnt);

        List<PdPrdAddtInfo> gotList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO2);


        Assert.assertEquals(3, gotList.size());


        for (PdPrdAddtInfo pdPrdAddtInfo : gotList) {
            switch (CodeEnumUtil.getEnumOfFieldValue(
                    AddtInfoClfCd.class,
                    CodeEnumUtil.FieldName.code,
                    pdPrdAddtInfo.getAddtInfoClfCd()
            )) {
                case FACTORY_PRICE:
                    Assert.assertEquals(FACTORY_PRICE, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                case DECLARED_SUPPORT_FUND:
                    Assert.assertEquals(DECLARED_SUPPORT_FUND, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                case ADDITIONAL_SUPPORT_FUND:
                    Assert.assertEquals(ADDITIONAL_SUPPORT_FUND, pdPrdAddtInfo.getAddtInfoCont());
                    break;
                default:
                    break;
            }
        }

    }


    @Test
    public void t030_updateProductAddtInfoList() {

        String factoryPrice = "880000", declaredSupportFund = "250000", addtionalSupportFund = "30000";


        List<PdPrdAddtInfo> pdPrdAddtInfoList = new ArrayList<>();

        //업데이트 부가정보 3건
        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.FACTORY_PRICE.code
                , factoryPrice
                ,0
                ,0
        ));

        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.DECLARED_SUPPORT_FUND.code
                ,declaredSupportFund
                ,0
                ,0
        ));

        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.ADDITIONAL_SUPPORT_FUND.code
                , addtionalSupportFund
                ,0
                ,0
        ));

        // 새로 인서트할 부가정보
        pdPrdAddtInfoList.add(new PdPrdAddtInfo(
                PRD_NO1
                ,AddtInfoClfCd.BROADCASTING_PRD_YN.code
                , "N"
                ,0
                ,0
        ));


        int updateCnt = productAddtInfoService.updateProductAddtInfoList(pdPrdAddtInfoList);

        Assert.assertEquals(3, updateCnt);



        List<PdPrdAddtInfo> gotList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO1);

        Assert.assertEquals(5, gotList.size());

        int cnt = 0;


        for (PdPrdAddtInfo pdPrdAddtInfo : gotList) {
            switch (CodeEnumUtil.getEnumOfFieldValue(
                    AddtInfoClfCd.class,
                    CodeEnumUtil.FieldName.code,
                    pdPrdAddtInfo.getAddtInfoClfCd()
            )) {
                case FACTORY_PRICE:
                    Assert.assertEquals(factoryPrice, pdPrdAddtInfo.getAddtInfoCont());
                    cnt++;
                    break;
                case DECLARED_SUPPORT_FUND:
                    Assert.assertEquals(declaredSupportFund, pdPrdAddtInfo.getAddtInfoCont());
                    cnt++;
                    break;
                case ADDITIONAL_SUPPORT_FUND:
                    Assert.assertEquals(addtionalSupportFund, pdPrdAddtInfo.getAddtInfoCont());
                    cnt++;
                    break;
                case BROADCASTING_PRD_YN:
                    Assert.assertEquals("N", pdPrdAddtInfo.getAddtInfoCont());
                    cnt++;
                    break;
                case OVERSEA_DELIVERY_CUSTOMS_YN:
                    Assert.assertEquals(OVERSEA_DELIVERY_CUSTOMS_YN, pdPrdAddtInfo.getAddtInfoCont());
                    cnt++;
                    break;
                default:
                    break;
            }
        }

        Assert.assertEquals(5, cnt);
    }


    @Test
    public void t040_deleteProductAddtInfo_prdNo() {

        int deleteCnt = productAddtInfoService.deleteProductAddtInfo(PRD_NO1);

        Assert.assertEquals(4, deleteCnt);

        List<PdPrdAddtInfo> list = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO1);

        Assert.assertEquals(0, list.size());

    }


    @Test
    public void t050_deleteProductAddtInfo_prdNo_addtInfoClfCd() {

        int deleteCnt = productAddtInfoService.deleteProductAddtInfo(PRD_NO1, AddtInfoClfCd.OVERSEA_DELIVERY_CUSTOMS_YN);

        Assert.assertEquals(1, deleteCnt);

        List<PdPrdAddtInfo> list = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO1);

        Assert.assertEquals(3, list.size());

        boolean overseaDeliveryCustomExists = false;

        for(PdPrdAddtInfo pdPrdAddtInfo: list) {
            if(pdPrdAddtInfo.getAddtInfoClfCd().equals(AddtInfoClfCd.OVERSEA_DELIVERY_CUSTOMS_YN.code)) {
                overseaDeliveryCustomExists = true;
                break;
            }
        }

        Assert.assertEquals(false, overseaDeliveryCustomExists);

    }

    @Test
    public void t060_insertBrodOnlyProductYn() {

        this.prdNoList.add(PRD_NO2);

        int insertCnt = productAddtInfoService.insertBrodOnlyProductYn(PRD_NO2, "Y", 0, 0);

        Assert.assertEquals(1, insertCnt);

        List<PdPrdAddtInfo> pdPrdAddtInfoList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO2);

        Assert.assertEquals(1, pdPrdAddtInfoList.size());

        PdPrdAddtInfo pdPrdAddtInfo = pdPrdAddtInfoList.get(0);

        Assert.assertEquals(PRD_NO2, pdPrdAddtInfo.getPrdNo());
        Assert.assertEquals(AddtInfoClfCd.BROADCASTING_PRD_YN.code, pdPrdAddtInfo.getAddtInfoClfCd());
        Assert.assertEquals("Y", pdPrdAddtInfo.getAddtInfoCont());
        Assert.assertEquals(0, pdPrdAddtInfo.getCreateNo());
        Assert.assertEquals(0, pdPrdAddtInfo.getUpdateNo());


    }


    @Test
    public void t070_updateBrodOnlyProductYn() {


        this.prdNoList.add(PRD_NO2);

        int insertCnt = productAddtInfoServiceTestMapper.insertProductAddtInfo(
                new PdPrdAddtInfo(
                        PRD_NO2,
                        AddtInfoClfCd.BROADCASTING_PRD_YN.code,
                        "Y",
                        0,
                        0
                )
        );

        Assert.assertEquals(1,insertCnt);

        int updateCnt = productAddtInfoService.updateBrodOnlyProductYn(PRD_NO2, "N", 0, 0);

        Assert.assertEquals(1, updateCnt);

        List<PdPrdAddtInfo> pdPrdAddtInfoList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO2);

        Assert.assertEquals(1, pdPrdAddtInfoList.size());

        PdPrdAddtInfo pdPrdAddtInfo = pdPrdAddtInfoList.get(0);

        Assert.assertEquals(PRD_NO2, pdPrdAddtInfo.getPrdNo());
        Assert.assertEquals("N", pdPrdAddtInfo.getAddtInfoCont());
        Assert.assertEquals(AddtInfoClfCd.BROADCASTING_PRD_YN.code, pdPrdAddtInfo.getAddtInfoClfCd());

    }


    @Test
    public void t080_deleteBrodOnlyProductYn() {


        this.prdNoList.add(PRD_NO2);

        int insertCnt = productAddtInfoServiceTestMapper.insertProductAddtInfo(
                new PdPrdAddtInfo(
                        PRD_NO2,
                        AddtInfoClfCd.BROADCASTING_PRD_YN.code,
                        "Y",
                        0,
                        0
                )
        );

        Assert.assertEquals(1,insertCnt);

        List<PdPrdAddtInfo> pdPrdAddtInfoList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO2);

        Assert.assertEquals(1, pdPrdAddtInfoList.size());

        PdPrdAddtInfo pdPrdAddtInfo = pdPrdAddtInfoList.get(0);

        Assert.assertEquals(PRD_NO2, pdPrdAddtInfo.getPrdNo());
        Assert.assertEquals("Y", pdPrdAddtInfo.getAddtInfoCont());
        Assert.assertEquals(AddtInfoClfCd.BROADCASTING_PRD_YN.code, pdPrdAddtInfo.getAddtInfoClfCd());
        Assert.assertEquals(0,pdPrdAddtInfo.getCreateNo());
        Assert.assertEquals(0, pdPrdAddtInfo.getUpdateNo());

        int deleteCnt = productAddtInfoService.deleteBrodOnlyProductYn(PRD_NO2);

        Assert.assertEquals(1, deleteCnt);

        pdPrdAddtInfoList = productAddtInfoServiceTestMapper.getProductAddtInfoList(PRD_NO2);

        Assert.assertEquals(0, pdPrdAddtInfoList.size());
    }






    @After
    public void after() {

        for(long prdNo : prdNoList) {
            productAddtInfoServiceTestMapper.deleteProductAddtInfo(prdNo);
        }

    }
}
