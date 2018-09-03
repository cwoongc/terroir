package com.elevenst.terroir.product.registration.coordi_prd.service;


import com.elevenst.terroir.product.registration.coordi_prd.entity.PdPrdCoordi;
import com.elevenst.terroir.product.registration.coordi_prd.mapper.CoordiProductServiceTestMapper;
import com.elevenst.terroir.product.registration.coordi_prd.vo.CoordiProductVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class CoordiProductServiceTest {

    private static final long PRD_NO1 = 99999999991L;
    private static final long PRD_NO2 = 99999999992L;
    private static final long PRD_NO3 = 99999999993L;
    private static final long PRD_NO4 = 99999999994L;

    @Autowired
    CoordiProductServiceImpl coordiProductService;

    @Autowired
    CoordiProductServiceTestMapper coordiProductServiceTestMapper;


    @Before
    public void before() {

        coordiProductServiceTestMapper.insertCoordiProduct(new PdPrdCoordi(
            PRD_NO1,
            PRD_NO2,
            0
        ));

        coordiProductServiceTestMapper.insertCoordiProduct(new PdPrdCoordi(
                PRD_NO1,
                PRD_NO3,
                0
        ));

        coordiProductServiceTestMapper.insertCoordiProduct(new PdPrdCoordi(
                PRD_NO1,
                PRD_NO4,
                0
        ));

    }

    @Test
    public void t010_insertCoordiProductList() {

        int deleteCnt = coordiProductServiceTestMapper.deleteCoordiProduct(PRD_NO1);

        Assert.assertEquals(3, deleteCnt);

        List<PdPrdCoordi> pdPrdCoordiList = new ArrayList<>();


        pdPrdCoordiList.add(new PdPrdCoordi(
                PRD_NO1,
                PRD_NO2,
                0
        ));

        pdPrdCoordiList.add(new PdPrdCoordi(
                PRD_NO1,
                PRD_NO3,
                1
        ));

        pdPrdCoordiList.add(new PdPrdCoordi(
                PRD_NO1,
                PRD_NO4,
                2
        ));


        int insertCnt = coordiProductService.insertCoordiProductList(pdPrdCoordiList);

        Assert.assertEquals(3, insertCnt);

        List<PdPrdCoordi> selectList = coordiProductServiceTestMapper.getCoordiProductList(PRD_NO1);


        boolean[] checkList = {
                false, false, false
        };

        for(PdPrdCoordi selected : selectList) {

            if(selected.getCoordiPrdNo() == PRD_NO2) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(0, selected.getCreateNo());
                checkList[0] = true;
            }
            else if (selected.getCoordiPrdNo() == PRD_NO3) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(1, selected.getCreateNo());
                checkList[1] = true;
            }
            else if (selected.getCoordiPrdNo() == PRD_NO4) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(2, selected.getCreateNo());
                checkList[2] = true;
            }

        }

        for(boolean ck : checkList) {
            Assert.assertEquals(true, ck);
        }

    }



    @Test
    public void t020_insertCoordiProductList_prdNo_createNo_pdPrdCoordiList () {
        int deleteCnt = coordiProductServiceTestMapper.deleteCoordiProduct(PRD_NO1);

        Assert.assertEquals(3, deleteCnt);

        List<PdPrdCoordi> pdPrdCoordiList = new ArrayList<>();


        pdPrdCoordiList.add(new PdPrdCoordi(
                0,
                PRD_NO2,
                1
        ));

        pdPrdCoordiList.add(new PdPrdCoordi(
                0,
                PRD_NO3,
                1
        ));

        pdPrdCoordiList.add(new PdPrdCoordi(
                0,
                PRD_NO4,
                1
        ));


        int insertCnt = coordiProductService.insertCoordiProductList(PRD_NO1,0, pdPrdCoordiList);

        Assert.assertEquals(3,insertCnt);


        List<PdPrdCoordi> selectList = coordiProductServiceTestMapper.getCoordiProductList(PRD_NO1);


        boolean[] checkList = {
                false, false, false
        };

        for(PdPrdCoordi selected : selectList) {

            if(selected.getCoordiPrdNo() == PRD_NO2) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(0, selected.getCreateNo());
                checkList[0] = true;
            }
            else if (selected.getCoordiPrdNo() == PRD_NO3) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(0, selected.getCreateNo());
                checkList[1] = true;
            }
            else if (selected.getCoordiPrdNo() == PRD_NO4) {
                Assert.assertEquals(PRD_NO1, selected.getPrdNo());
                Assert.assertEquals(0, selected.getCreateNo());
                checkList[2] = true;
            }

        }

        for(boolean ck : checkList) {
            Assert.assertEquals(true, ck);
        }

    }

    @Test
    public void t030_deleteCoordiProduct() {


        List<PdPrdCoordi> pdPrdCoordiList = coordiProductServiceTestMapper.getCoordiProductList(PRD_NO1);

        Assert.assertEquals(3, pdPrdCoordiList.size());

        coordiProductService.deleteCoordiProduct(PRD_NO1);

        pdPrdCoordiList = coordiProductServiceTestMapper.getCoordiProductList(PRD_NO1);

        Assert.assertEquals(0, pdPrdCoordiList.size());


    }


    @Test
    public void t040_getCoordiPrdNoList() {

        List<CoordiProductVO> coordiVOList = coordiProductService.getCoordiPrdNoList(PRD_NO1);

        Assert.assertEquals(true, coordiVOList.containsAll(Arrays.asList(PRD_NO2,PRD_NO3,PRD_NO4)));


    }







    @After
    public void after() {

        coordiProductServiceTestMapper.deleteCoordiProduct(PRD_NO1);

    }




}
