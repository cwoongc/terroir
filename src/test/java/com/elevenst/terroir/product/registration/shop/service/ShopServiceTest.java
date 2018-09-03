package com.elevenst.terroir.product.registration.shop.service;


import com.elevenst.terroir.product.registration.shop.entity.PdTownShopBranchComp;
import com.elevenst.terroir.product.registration.shop.mapper.ShopServiceTestMapper;
import com.elevenst.terroir.product.registration.shop.vo.ShopBranchVO;
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
public class ShopServiceTest {


    private static final long PRD_NO1 = 99999999991L;
    private static final long PRD_NO2 = 99999999992L;
    private static final long SHOP_NO1 = 99999999981L;
    private static final long SHOP_NO2 = 99999999982L;
    private static final long BRANCH_NO1 = 99999999971L;
    private static final long BRANCH_NO2 = 99999999972L;
    private boolean clearProductShopBranch = false;
    private boolean clearShopBranch = false;


    @Autowired
    ShopServiceImpl shopService;

    @Autowired
    ShopServiceTestMapper shopServiceTestMapper;



    @Before
    public void before() {

    }



    @Test
    public void t010_insertProductShopBranch() {

        this.clearProductShopBranch = true;
        this.clearShopBranch = false;


        int insertCnt = shopService.insertProductShopBranch(new PdTownShopBranchComp(
                PRD_NO1,
                SHOP_NO1,
                BRANCH_NO1
        ));

        insertCnt += shopService.insertProductShopBranch(new PdTownShopBranchComp(
                PRD_NO1,
                SHOP_NO1,
                BRANCH_NO2
        ));


        Assert.assertEquals(2, insertCnt);


        boolean[] checker = {false, false};

        List<PdTownShopBranchComp> list = shopServiceTestMapper.getProductShopBranchListByPrdNo(PRD_NO1);

        for(PdTownShopBranchComp prdShopBranch : list ) {
            Assert.assertEquals(PRD_NO1, prdShopBranch.getPrdNo());
            Assert.assertEquals(SHOP_NO1, prdShopBranch.getShopNo());

            if(prdShopBranch.getShopBranchNo() == BRANCH_NO1) {
                checker[0] = true;
            } else if (prdShopBranch.getShopBranchNo() == BRANCH_NO2) {
                checker[1] = true;
            }
        }

        for(boolean check : checker) {
            Assert.assertTrue(check);
        }
    }



    @Test
    public void t020_getShopBranchNoList() {


        this.clearProductShopBranch = false;
        this.clearShopBranch = true;


        int insertCnt = shopServiceTestMapper.insertShopBranch(SHOP_NO1, BRANCH_NO1,"01", "012345");
        insertCnt += shopServiceTestMapper.insertShopBranch(SHOP_NO1, BRANCH_NO2, "02","543210");

        Assert.assertEquals(2, insertCnt);

        List<Long> branchNoList = shopService.getShopBranchNoList(SHOP_NO1);

        boolean[] checker = {false, false};

        for (Long branchNo : branchNoList) {
            if(branchNo == BRANCH_NO1) checker[0] = true;
            else if(branchNo == BRANCH_NO2) checker[1] = true;
        }

        for (boolean ck : checker) {
            Assert.assertTrue(ck);
        }

    }


    @Test
    public void t030_getShopBranchList() {


        this.clearProductShopBranch = false;
        this.clearShopBranch = true;


        int insertCnt = shopServiceTestMapper.insertShopBranch(SHOP_NO1, BRANCH_NO1,"01", "012345");
        insertCnt += shopServiceTestMapper.insertShopBranch(SHOP_NO1, BRANCH_NO2, "02","543210");

        Assert.assertEquals(2, insertCnt);

        List<ShopBranchVO> branchList = shopService.getShopBranchList(SHOP_NO1);

        boolean[] checker = {false, false};

        for (ShopBranchVO branch : branchList) {
            if(branch.getShopBranchNo() == BRANCH_NO1) {
                Assert.assertEquals("01", branch.getShopBranchTypCd());
                Assert.assertEquals("012345", branch.getMailNo());

                checker[0] = true;
            }
            else if(branch.getShopBranchNo() == BRANCH_NO2) {
                Assert.assertEquals("02", branch.getShopBranchTypCd());
                Assert.assertEquals("543210", branch.getMailNo());
                checker[1] = true;
            }
        }

        for (boolean ck : checker) {
            Assert.assertTrue(ck);
        }

    }

    @Test
    public void t40_getPromotionCheck() {
        ShopBranchVO shopBranchVO = new ShopBranchVO();
        shopBranchVO.setSelMnbdNo(10000276);
        shopBranchVO.setShopNo(10000021);
        int retCnt = shopService.getPromotionCheck(shopBranchVO);
        boolean isTrue = retCnt > 0 ? true : false;
        Assert.assertTrue(isTrue);
    }

    @Test
    public void t50_getTownShopBranchUsingList() {
        long shopNo = 10000021;
        ArrayList<ShopBranchVO> retList = shopService.getTownShopBranchUsingList(shopNo);
        Assert.assertNotNull(retList);
    }

    @Test
    public void t60_getShopNo() {
        long shopBranchNo = 0;
        ShopBranchVO shopBranchVO = shopService.getShopNo(shopBranchNo);
        Assert.assertTrue(shopBranchVO == null);
    }

    @Test
    public void t70_getShopList() {
        long selNo = 10000276;
        ArrayList<ShopBranchVO> retList = shopService.getShopList(selNo);
        Assert.assertTrue(retList != null);
    }

    @Test
    public void t80_getCheckBranchList() {
        ShopBranchVO shopBranchVO = new ShopBranchVO();
        shopBranchVO.setPrdNo(409115932);
        shopBranchVO.setShopNo(10000202);
        ArrayList<ShopBranchVO> retList = shopService.getCheckBranchList(shopBranchVO);
        Assert.assertTrue(retList != null);
    }









    @After
    public void after() {

        if(this.clearProductShopBranch) shopServiceTestMapper.deleteProductShopBranchByPrdNo(PRD_NO1);
        if(this.clearShopBranch) shopServiceTestMapper.deleteShopBranchByShopNo(SHOP_NO1);


    }


}
