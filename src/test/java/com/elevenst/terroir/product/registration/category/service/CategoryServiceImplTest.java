package com.elevenst.terroir.product.registration.category.service;

import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.category.vo.DpDispObjVO;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.vo.BaseVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    public void testGetServiceDisplayCategoryInfo() {
        CategoryVO categoryVO = categoryService.getServiceDisplayCategoryInfo(4);

        Assert.assertTrue(categoryVO != null);
    }

    @Test
    public void testGetFreshCategoryYn() {
        ProductVO productVO = new ProductVO();
        productVO.getBaseVO().setPrdTypCd(PrdTypCd.BRANCH_DELIVERY_MART.getDtlsCd());
        productVO.setDispCtgrNo(944265);
        productVO.setSelMnbdNo(10000276);

        String retVal = categoryService.getFreshCategoryYn(productVO);
        Assert.assertEquals(retVal, "N");
    }

    @Test
    public void getDisplayCategoryChildList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("hgCtgrNo", "1");
        categoryService.getDisplayCategoryChildList(map);
    }

    @Test
    public void getServiceDisplayCategoryProduct() {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setLev("1");
        categoryVO.setDispCtgrNo(944265);
        long[] dispCtgr1Nos = new long[2];
        dispCtgr1Nos[0] = 1;
        dispCtgr1Nos[1] = 2;
        categoryVO.setDispCtgr1NoArr(dispCtgr1Nos);
        categoryVO.setUserUniqSohoYn("N");
        categoryVO.setUserAbrdBrandYn("N");
        categoryVO.setUserOcbZoneYn("N");
        categoryVO.setOmCtgrYn("N");
        categoryVO.setSvcAreaCd("03");
        categoryService.getServiceDisplayCategoryProduct(categoryVO);
    }

    @Test
    public void getHelpDpDispObjList() {
        DpDispObjVO dpDispObjVO = new DpDispObjVO();
        dpDispObjVO.setDispSpceId("1");
        dpDispObjVO.setDispObjNo(1);
        categoryService.getHelpDpDispObjList(dpDispObjVO);
    }

    @Test
    public void getProductRegInfobyCtgrNo() {
        categoryService.getProductRegInfobyCtgrNo(1);
    }

    @Test
    public void getProductRegPossibleCtgrNo() {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setSelMnbdNo(10000276);
        categoryVO.setDispCtgrNo(944265);
        categoryService.getProductRegPossibleCtgrNo(categoryVO);
    }

    @Test
    public void isProductInfoInputLimit() {
        long prdNo = 1244990384;
        categoryService.isProductInfoInputLimit(prdNo);
    }

    @Test
    public void getProductLCtgrRefInfo() {
        long dispCtgrNo = 944265;
        categoryService.getProductLCtgrRefInfo(dispCtgrNo);
    }

    @Test
    public void insertCategoryRecommedations() {
        ProductVO productVO = new ProductVO();
        productVO.setPrdNo(1212121215L);
        String[] recomCtgrNos = {"123"};
        productVO.setRecomCtgrNos(recomCtgrNos);
        productVO.setCreateNo(10000276);

        categoryService.insertCategoryRecommedations(productVO);
    }

    @Test
    public void testCanRegistProductTypeToCategory(){
        ProductVO productVO = new ProductVO();
        BaseVO baseVO = new BaseVO();
        baseVO.setPrdTypCd("01");
        productVO.setBaseVO(baseVO);
        productVO.setDispCtgrNo(4);
        categoryService.canRegistProductTypeToCategory(productVO);
    }
}
