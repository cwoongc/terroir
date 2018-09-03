package com.elevenst.terroir.product.registration.pln_prd.service;


import com.elevenst.terroir.product.registration.pln_prd.entity.PdPlnPrd;
import com.elevenst.terroir.product.registration.pln_prd.mapper.PlanProductServiceTestMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class PlanProductServiceTest {

    private static final long PRD_NO1 = 99999999991L;
    private static final long PRD_NO2 = 99999999992L;
    private static final long PRD_NO3 = 99999999993L;
    private static final long PRD_NO4 = 99999999994L;


    @Autowired
    PlanProductServiceImpl planProductService;

    @Autowired
    PlanProductServiceTestMapper planProductServiceTestMapper;


    @Before
    public void before() {
        planProductServiceTestMapper.insertPlanProduct(new PdPlnPrd(
            PRD_NO1,
                "2015/10/30",
                "2015/11/01",
                0,
                0
        ));

        planProductServiceTestMapper.insertPlanProduct(new PdPlnPrd(
                PRD_NO2,
                "2016/10/30",
                "2016/11/01",
                0,
                0
        ));

        planProductServiceTestMapper.insertPlanProduct(new PdPlnPrd(
                PRD_NO3,
                "2017/10/30",
                null,
                0,
                0
        ));

    }


    @Test
    public void t010_insertPlanProduct() {

        PdPlnPrd pdPlnPrd = planProductServiceTestMapper.getPlanProduct(PRD_NO4);

        Assert.assertNull(pdPlnPrd);

        int insertCnt = planProductService.insertPlanProduct(new PdPlnPrd(
                PRD_NO4,
                "2016/11/05",
                "2016/11/07",
                0,
                0
        ));

        Assert.assertEquals(1, insertCnt);

        pdPlnPrd = planProductServiceTestMapper.getPlanProduct(PRD_NO4);

        Assert.assertNotNull(pdPlnPrd);
        Assert.assertEquals(PRD_NO4, pdPlnPrd.getPrdNo());
        Assert.assertEquals("2016-11-05 00:00:00.0", pdPlnPrd.getWrhsPlnDy());
        Assert.assertEquals("2016-11-07 00:00:00.0", pdPlnPrd.getWrhsDy());
        Assert.assertEquals(0, pdPlnPrd.getCreateNo());
        Assert.assertEquals(0, pdPlnPrd.getUpdateNo());



    }

    @Test
    public void t020_updatePlanProduct() {

        Date now = new Date();

        String date = new SimpleDateFormat("yyyyMMddkkmmss").format(now);

        System.out.println(date);


        PdPlnPrd pdPlnPrd = new PdPlnPrd(
                PRD_NO3,
                "20171101000000",
                date,
                1
        );


        int updateCnt = planProductService.updatePlanProduct(pdPlnPrd);

        Assert.assertEquals(1,updateCnt);

        PdPlnPrd selected = planProductServiceTestMapper.getPlanProduct(PRD_NO3);


        Assert.assertEquals(pdPlnPrd.getPrdNo(), selected.getPrdNo());

        Assert.assertEquals("2017-11-01 00:00:00.0", selected.getWrhsPlnDy());
        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.0").format(now) , selected.getUpdateDt());
        Assert.assertEquals(1, selected.getUpdateNo());


    }


    @Test
    public void t030_deletePlanProduct() {

        PdPlnPrd pdPlnPrd = planProductServiceTestMapper.getPlanProduct(PRD_NO1);

        Assert.assertNotNull(pdPlnPrd);

        planProductService.deletePlanProduct(PRD_NO1);

        pdPlnPrd = planProductServiceTestMapper.getPlanProduct(PRD_NO1);

        Assert.assertNull(pdPlnPrd);
    }


    @After
    public void after() {
        planProductServiceTestMapper.deletePlanProduct(PRD_NO1);
        planProductServiceTestMapper.deletePlanProduct(PRD_NO2);
        planProductServiceTestMapper.deletePlanProduct(PRD_NO3);
        planProductServiceTestMapper.deletePlanProduct(PRD_NO4);
    }


}
