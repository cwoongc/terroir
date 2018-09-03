package com.elevenst.terroir.product.registration.desc.service;


import com.elevenst.terroir.product.registration.deal.vo.ProductEvtVO;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.desc.mapper.ProductDescServiceTestMapper;
import com.elevenst.terroir.product.registration.desc.vo.ProductDescVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
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
public class ProductDescServiceTest {

    private static final long PRD_NO = 99999999991L;
    private static final long COPY_PRD_NO = 99999999992L;

    private List<Long> prdNoList = new ArrayList<Long>();


    @Autowired
    private ProductDescServiceImpl productDescService;

    @Autowired
    ProductDescServiceTestMapper productDescServiceTestMapper;


    @Before
    public void before() {

        this.prdNoList.clear();
        this.prdNoList.add(PRD_NO);


        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "02",
                null,
                "test1",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "01",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "06",
                null,
                "test2",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "02",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "07",
                null,
                "test3",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "03",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "12",
                null,
                "test4",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "04",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "13",
                null,
                "test5",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "05",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "14",
                null,
                "test6",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "06",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "15",
                null,
                "test7",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "07",
                null
        ));

        productDescServiceTestMapper.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "16",
                null,
                "test8",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "08",
                null
        ));


    }




    @Test
    public void t010_getProductDesc() {
        PdPrdDesc pdPrdDesc = productDescServiceTestMapper.getProductDesc(PRD_NO, "16");

        Assert.assertEquals("16", pdPrdDesc.getPrdDescTypCd());
        Assert.assertEquals("test8", pdPrdDesc.getPrdDescContClob());
        Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
        Assert.assertEquals("08", pdPrdDesc.getPrdDtlTypCd());
        Assert.assertEquals(0, pdPrdDesc.getCreateNo());
        Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
    }


    @Test
    public void t020_getProductDescList() {



        List<PdPrdDesc> pdPrdDescList = productDescService.getProductDescList(PRD_NO);

        Assert.assertEquals(8, pdPrdDescList.size());

        for (PdPrdDesc pdPrdDesc : pdPrdDescList) {
            switch (pdPrdDesc.getPrdDescTypCd()) {
                case "02":
                    Assert.assertEquals("test1", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("01", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "06":
                    Assert.assertEquals("test2", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("02", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());

                    break;
                case "07":
                    Assert.assertEquals("test3", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("03", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "12":
                    Assert.assertEquals("test4", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("04", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "13":
                    Assert.assertEquals("test5", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("05", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "14":
                    Assert.assertEquals("test6", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("06", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "15":
                    Assert.assertEquals("test7", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("07", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                case "16":
                    Assert.assertEquals("test8", pdPrdDesc.getPrdDescContClob());
                    Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
                    Assert.assertEquals("08", pdPrdDesc.getPrdDtlTypCd());
                    Assert.assertEquals(0, pdPrdDesc.getCreateNo());
                    Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
                    break;
                default:
                    break;
            }
        }

    }


    @Test
    public void t030_getProductDetailCont() {

        PdPrdDesc pdPrdDesc = productDescService.getProductDetailCont(PRD_NO);
        Assert.assertEquals("02", pdPrdDesc.getPrdDescTypCd());
        Assert.assertEquals("test1", pdPrdDesc.getPrdDescContClob());
        Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
        Assert.assertEquals("01", pdPrdDesc.getPrdDtlTypCd());
        Assert.assertEquals(0, pdPrdDesc.getCreateNo());
        Assert.assertEquals(0, pdPrdDesc.getUpdateNo());
    }


    @Test
    public void t040_getProductDescContForSmtOption() {

        List<ProductDescVO> productDescVOList = productDescService.getProductDescContForSmtOption(PRD_NO);

        Assert.assertEquals(3, productDescVOList.size());

        int cnt = 0;

        for(ProductDescVO productDescVO : productDescVOList) {
            String prdDescTypCd = productDescVO.getPrdDescTypCd();

            switch(prdDescTypCd) {
                case "02":
                    Assert.assertEquals("test1", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("01", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                case "12":
                    Assert.assertEquals("test4", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("04", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                case "13":
                    Assert.assertEquals("test5", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("05", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                default:
                    break;
            }
        }

        Assert.assertEquals(3, cnt);
    }



    @Test
    public void t050_getProductDescContForSmtOptionMW() {
        List<ProductDescVO> productDescVOList = productDescService.getProductDescContForSmtOptionMW(PRD_NO);

        Assert.assertEquals(3, productDescVOList.size());

        int cnt = 0;

        for(ProductDescVO productDescVO : productDescVOList) {
            String prdDescTypCd = productDescVO.getPrdDescTypCd();

            switch(prdDescTypCd) {
                case "14":
                    Assert.assertEquals("test6", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("06", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                case "15":
                    Assert.assertEquals("test7", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("07", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                case "16":
                    Assert.assertEquals("test8", productDescVO.getPrdDescContClob());
                    Assert.assertEquals("Y", productDescVO.getClobTypYn());
                    Assert.assertEquals("08", productDescVO.getPrdDtlTypCd());
                    Assert.assertEquals(0, productDescVO.getCreateNo());
                    Assert.assertEquals(0, productDescVO.getUpdateNo());
                    cnt++;
                    break;
                default:
                    break;
            }
        }

        Assert.assertEquals(3, cnt);

    }

    @Test
    public void t060_insertProductDesc_PdPrdDesc() {

        productDescService.insertProductDesc(new PdPrdDesc(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "17",
                null,
                "test9",
                "Y",
                null,
                0,
                null,
                0,
                0,
                null,
                "09",
                null
        ));


        PdPrdDesc pdPrdDesc = productDescServiceTestMapper.getProductDesc(PRD_NO, "17");

        Assert.assertEquals("17", pdPrdDesc.getPrdDescTypCd());
        Assert.assertEquals("test9", pdPrdDesc.getPrdDescContClob());
        Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
        Assert.assertEquals("09", pdPrdDesc.getPrdDtlTypCd());
        Assert.assertEquals(0, pdPrdDesc.getCreateNo());
        Assert.assertEquals(0, pdPrdDesc.getUpdateNo());

    }

    @Test
    public void t070_insertProductDesc_ProductDescVO() {

        productDescService.insertProductDesc(new ProductDescVO(
                productDescServiceTestMapper.getProductDescNo(),
                PRD_NO,
                "18",
                null,
                "test10",
                null,
                null,
                "Y",
                null,
                0,
                null,
                0,
                0L,
                0,
                null,
                "10",
                null
                ,null
        ));


        PdPrdDesc pdPrdDesc = productDescServiceTestMapper.getProductDesc(PRD_NO, "18");

        Assert.assertEquals("18", pdPrdDesc.getPrdDescTypCd());
        Assert.assertEquals("test10", pdPrdDesc.getPrdDescContClob());
        Assert.assertEquals("Y", pdPrdDesc.getClobTypYn());
        Assert.assertEquals("10", pdPrdDesc.getPrdDtlTypCd());
        Assert.assertEquals(0, pdPrdDesc.getCreateNo());
        Assert.assertEquals(0, pdPrdDesc.getUpdateNo());

    }


    @Test
    public void t080_insertProductDescReReg() {

        this.prdNoList.add(COPY_PRD_NO);

        productDescService.insertProductDescReReg(PRD_NO, COPY_PRD_NO);



        List<PdPrdDesc> originalDescList = productDescServiceTestMapper.getProductDescList(PRD_NO);
        List<PdPrdDesc> copiedDescList = productDescServiceTestMapper.getProductDescList(COPY_PRD_NO);

        for(PdPrdDesc original : originalDescList) {

            for(PdPrdDesc copy : copiedDescList) {

                if(original.getPrdDescTypCd().equals(copy.getPrdDescTypCd())) {

                    Assert.assertEquals(original.getClobTypYn(), copy.getClobTypYn());
                    Assert.assertEquals(original.getPrdDtlTypCd(), copy.getPrdDtlTypCd());
                    switch(original.getClobTypYn()) {
                        case "Y": Assert.assertEquals(original.getPrdDescContClob(), copy.getPrdDescContClob());
                        break;
                        case "N": Assert.assertEquals(original.getPrdDescContVc(), copy.getPrdDescContVc());
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void t090_updateProductDetail() {

        ProductDescVO productDescVO = new ProductDescVO();

        productDescVO.setPrdNo(PRD_NO);
        productDescVO.setClobTypYn("Y");
        productDescVO.setPrdDescContClob("update");
        productDescVO.setUpdateNo(1);

        productDescService.updateProductDetail(productDescVO);

        PdPrdDesc pdPrdDesc = productDescServiceTestMapper.getProductDetailCont(PRD_NO);

        Assert.assertEquals("02", pdPrdDesc.getPrdDescTypCd());
        Assert.assertEquals(productDescVO.getClobTypYn(), pdPrdDesc.getClobTypYn());
        Assert.assertEquals(productDescVO.getPrdDescContClob(), pdPrdDesc.getPrdDescContClob());
        Assert.assertEquals(productDescVO.getUpdateNo(), pdPrdDesc.getUpdateNo());

    }


    @Test
    public void t100_updateProductDescCont() {
        List<ProductDescVO> productDescVOList = new ArrayList<>();

        ProductDescVO productDescVo1 = new ProductDescVO(
                0,
                PRD_NO,
                "02",
                null,
                "updateProductDescCont1",
                null,
                null,
                "Y",
                null,
                0,
                null,
                0,
                0L,
                0,
                null,
                "45",
                null
                ,null
        );


        ProductDescVO productDescVo2 = new ProductDescVO(
                0,
                PRD_NO,
                "12",
                null,
                "updateProductDescCont2",
                null,
                null,
                "Y",
                null,
                0,
                null,
                0,
                0L,
                0,
                null,
                "90",
                null
                ,null
        );



        productDescVOList.add(productDescVo1);
        productDescVOList.add(productDescVo2);


        productDescService.updateProductDescCont(productDescVOList);


        PdPrdDesc pdPrdDesc1 = productDescServiceTestMapper.getProductDesc(PRD_NO, "02");
        PdPrdDesc pdPrdDesc2 = productDescServiceTestMapper.getProductDesc(PRD_NO, "12");

        Assert.assertEquals(productDescVo1.getPrdDescTypCd(), pdPrdDesc1.getPrdDescTypCd());
        Assert.assertEquals(productDescVo1.getPrdDescContClob(), pdPrdDesc1.getPrdDescContClob());
        Assert.assertEquals(productDescVo1.getPrdDtlTypCd(), pdPrdDesc1.getPrdDtlTypCd());

        Assert.assertEquals(productDescVo2.getPrdDescTypCd(), pdPrdDesc2.getPrdDescTypCd());
        Assert.assertEquals(productDescVo2.getPrdDescContClob(), pdPrdDesc2.getPrdDescContClob());
        Assert.assertEquals(productDescVo2.getPrdDtlTypCd(), pdPrdDesc2.getPrdDtlTypCd());

    }



    @Test
    public void t110_deleteProductDesc_NULL_prdDescTypCd() {


        int deleteCnt = productDescService.deleteProductDesc(PRD_NO,null);

        Assert.assertEquals(3, deleteCnt);

        List<PdPrdDesc> pdPrdDescList = productDescServiceTestMapper.getProductDescList(PRD_NO);


        boolean wasDeleted = true;

        for(PdPrdDesc pdPrdDesc : pdPrdDescList) {
            String prdDescTypCd = pdPrdDesc.getPrdDescTypCd();

            switch (prdDescTypCd) {
                case "02": wasDeleted = false;
                break;
                case "06": wasDeleted = false;
                break;
                case "07": wasDeleted = false;
                break;
                default:
                    break;
            }

        }

        Assert.assertEquals(true, wasDeleted);

    }


    @Test
    public void t120_deleteProductDesc_NOT_NULL_prdDescTypCd() {

        int deleteCnt = productDescService.deleteProductDesc(PRD_NO,"02");

        Assert.assertEquals(1, deleteCnt);

        deleteCnt += productDescService.deleteProductDesc(PRD_NO,"13");

        Assert.assertEquals(2, deleteCnt);


        List<PdPrdDesc> pdPrdDescList = productDescServiceTestMapper.getProductDescList(PRD_NO);


        boolean wasDeleted = true;

        for(PdPrdDesc pdPrdDesc : pdPrdDescList) {
            String prdDescTypCd = pdPrdDesc.getPrdDescTypCd();

            switch (prdDescTypCd) {
                case "02": wasDeleted = false;
                    break;
                case "13": wasDeleted = false;
                    break;
                default:
                    break;
            }

        }

        Assert.assertEquals(true, wasDeleted);

    }


    @Test
    public void t130_deleteProductDescContInfo_SINGLE () {

        int deleteCnt = productDescService.deleteProductDescContInfo(new ProductDescVO(PRD_NO, "14"));

        Assert.assertEquals(1, deleteCnt);

        List<PdPrdDesc> pdPrdDescList = productDescServiceTestMapper.getProductDescList(PRD_NO);


        boolean wasDeleted = true;

        for(PdPrdDesc pdPrdDesc : pdPrdDescList) {
            String prdDescTypCd = pdPrdDesc.getPrdDescTypCd();

            switch (prdDescTypCd) {
                case "14": wasDeleted = false;
                    break;
                default:
                    break;
            }
        }

        Assert.assertEquals(true, wasDeleted);

    }


    @Test
    public void t140_deleteProductDescContInfo_LIST () {

        List<String> prdDescTypCdList = new ArrayList<>();
        prdDescTypCdList.add("06");
        prdDescTypCdList.add("13");
        prdDescTypCdList.add("14");

        int deleteCnt = productDescService.deleteProductDescContInfo(
                new ProductDescVO(PRD_NO, null, prdDescTypCdList)
        );

        Assert.assertEquals(3, deleteCnt);

        List<PdPrdDesc> pdPrdDescList = productDescServiceTestMapper.getProductDescList(PRD_NO);

        boolean wasDeleted = true;

        for(PdPrdDesc pdPrdDesc : pdPrdDescList) {
            String prdDescTypCd = pdPrdDesc.getPrdDescTypCd();

            switch (prdDescTypCd) {
                case "06": wasDeleted = false;
                    break;
                case "13": wasDeleted = false;
                    break;
                case "14": wasDeleted = false;
                    break;
                default:
                    break;
            }
        }

        Assert.assertEquals(true, wasDeleted);

    }

    @Test
    public void t150_getSellerProductDetailDesc() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1244964953);

        ProductDescVO productDescVO = new ProductDescVO();
        productVO.setProductDescVO(productDescVO);
        productDescService.getSellerProductDetailDesc(productVO);

        productVO.getProductDescVO().setPrdDescTypCd("05");
        productDescService.getSellerProductDetailDesc(productVO);
    }





    @After
    public void after() {

        for(long prdNo : this.prdNoList ) {

            productDescServiceTestMapper.deleteProductDesc(prdNo, null);
            productDescServiceTestMapper.deleteProductDesc(prdNo, "12");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "13");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "14");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "15");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "16");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "17");
            productDescServiceTestMapper.deleteProductDesc(prdNo, "18");

        }

    }
}
