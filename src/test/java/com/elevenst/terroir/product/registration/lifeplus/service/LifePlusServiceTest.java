package com.elevenst.terroir.product.registration.lifeplus.service;

import com.elevenst.terroir.product.registration.lifeplus.code.WoffWkdyListCd;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvLmtQty;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvSchdlInfo;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanPlcy;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanRgn;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvDlv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvTmtr;
import com.elevenst.terroir.product.registration.lifeplus.mapper.LifePlusMapper;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailExctVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailWoffVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtMasterVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvSchdlInfoVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class LifePlusServiceTest {

    @Autowired
    LifePlusServiceImpl lifePlusServiceImpl;

    @Autowired
    LifePlusMapper lifePlusMapper;

    @Test
    public void getPdSvcCanPlcySeq() {
        PdSvcCanPlcy pdSvcCanPlcy = new PdSvcCanPlcy();
        pdSvcCanPlcy.setSellerMemNo(11349581);
        pdSvcCanPlcy.setSvcCanPlcyClfCd("02");
        pdSvcCanPlcy.setSvcCanPlcyObjNo(1244955662);
        lifePlusServiceImpl.getPdSvcCanPlcySeq(pdSvcCanPlcy);
    }

    @Test
    public void getPdSvcCanRgnList() {
        lifePlusServiceImpl.getPdSvcCanRgnList(336);
    }

    @Test
    public void insertPdSvcCanPlcy() {
        PdSvcCanPlcy pdSvcCanPlcy = new PdSvcCanPlcy();
        pdSvcCanPlcy.setSellerMemNo(99);
        pdSvcCanPlcy.setSvcCanPlcyClfCd("01");
        pdSvcCanPlcy.setSvcCanPlcyObjNo(11);
        pdSvcCanPlcy.setRgnAddrCdList("aaaa");
        pdSvcCanPlcy.setCreateNo(99);
        pdSvcCanPlcy.setUpdateNo(99);
        lifePlusServiceImpl.insertPdSvcCanPlcy(pdSvcCanPlcy);
    }

    @Test
    public void updatePdSvcCanPlcy() {
        PdSvcCanPlcy pdSvcCanPlcy = new PdSvcCanPlcy();
        pdSvcCanPlcy.setSvcCanPlcySeq(1680);
        pdSvcCanPlcy.setRgnAddrCdList("bbbb");
        pdSvcCanPlcy.setUpdateNo(99);
        lifePlusServiceImpl.updatePdSvcCanPlcy(pdSvcCanPlcy);
    }

    @Test
    public void deletePdSvcCanRgn() {
        lifePlusServiceImpl.deletePdSvcCanRgn(1680);
    }

    @Test
    public void insertPdSvcCanRgn() {
        PdSvcCanRgn pdSvcCanRgn = new PdSvcCanRgn();
        pdSvcCanRgn.setSvcCanPlcySeq(11);
        pdSvcCanRgn.setRgnClfCd("01");
        pdSvcCanRgn.setRgnAddrCd("00");
        pdSvcCanRgn.setSidoNm("aa");
        pdSvcCanRgn.setSigunguNm("bb");
        pdSvcCanRgn.setUeupmyonNm("cc");
        pdSvcCanRgn.setUeupmyonCd("00");
        pdSvcCanRgn.setCreateNo(99);
        pdSvcCanRgn.setUpdateNo(99);
        lifePlusServiceImpl.insertPdSvcCanRgn(pdSvcCanRgn);
    }

    @Test
    public void getPrdRsvSchdlInfoList() {
        PdRsvSchdlInfoVO pdRsvSchdlInfoVO = new PdRsvSchdlInfoVO();
//        pdRsvSchdlInfoVO.setPrdNo(11);
        lifePlusServiceImpl.getPrdRsvSchdlInfoList(pdRsvSchdlInfoVO);
    }

    @Test
    public void getPrdRsvLmtQtyList() {
        PdRsvSchdlInfoVO pdRsvSchdlInfoVO = new PdRsvSchdlInfoVO();
//        pdRsvSchdlInfoVO.setPrdNo(11);
        pdRsvSchdlInfoVO.setBgnDt("20170101");
        lifePlusServiceImpl.getPrdRsvLmtQtyList(pdRsvSchdlInfoVO);
    }

    @Test
    public void getHolidayList() {
        lifePlusServiceImpl.getHolidayList();
    }

    @Test
    public void getPdStrSaleTimeList() {
        HashMap hashMap = new HashMap();
        hashMap.put("strNo", 11);
        lifePlusServiceImpl.getPdStrSaleTimeList(hashMap);
    }

    @Test
    public void getPdStrWoffList() {
        HashMap hashMap = new HashMap();
        hashMap.put("strNo", 11);
        lifePlusServiceImpl.getPdStrWoffList(hashMap);
    }

    @Test
    public void insertProductRsvSchedule() {
        PdRsvSchdlInfo pdRsvSchdlInfo = new PdRsvSchdlInfo();
        //#{prdNo}, #{bgnDt}, #{endDt}, #{dtlDesc}, SYSDATE, #{createNo}, SYSDATE, #{updateNo}
        pdRsvSchdlInfo.setPrdNo(11);
        pdRsvSchdlInfo.setBgnDt("20170101");
        pdRsvSchdlInfo.setEndDt("20171231");
        pdRsvSchdlInfo.setDtlDesc("aa");
        pdRsvSchdlInfo.setCreateNo(99);
        pdRsvSchdlInfo.setUpdateNo(99);
        lifePlusServiceImpl.insertProductRsvSchedule(pdRsvSchdlInfo);

        lifePlusServiceImpl.deleteProductRsvScheduleList(pdRsvSchdlInfo);
    }

    @Test
    public void insertProductRsvLmtQty() {
        //#{prdNo}, #{bgnDt}, #{wkdyCd}, #{hhmm}, #{rsvLmtQty}, SYSDATE, #{createNo}, SYSDATE, #{updateNo}
        PdRsvLmtQty pdRsvLmtQty = new PdRsvLmtQty();
        pdRsvLmtQty.setPrdNo(99);
        pdRsvLmtQty.setBgnDt("20170101");
        pdRsvLmtQty.setWkdyCd("01");
        pdRsvLmtQty.setHhmm("11");
        pdRsvLmtQty.setRsvLmtQty(1);
        pdRsvLmtQty.setCreateNo(99);
        pdRsvLmtQty.setUpdateNo(99);
        lifePlusServiceImpl.insertProductRsvLmtQty(pdRsvLmtQty);

        lifePlusServiceImpl.deleteProductRsvLmtQtyList(pdRsvLmtQty);
    }

    @Test
    public void getPrdRsvLmtQty() {
        PdRsvLmtQty pdRsvLmtQty = new PdRsvLmtQty();
        pdRsvLmtQty.setPrdNo(11);
        pdRsvLmtQty.setBgnDt("20170101");
        pdRsvLmtQty.setWkdyCd("01");
        pdRsvLmtQty.setHhmm("11");
        lifePlusServiceImpl.getPrdRsvLmtQty(pdRsvLmtQty);
    }

    @Test
    public void updateProductRsvLmtQty() {
        PdRsvLmtQty pdRsvLmtQty = new PdRsvLmtQty();
        pdRsvLmtQty.setRsvLmtQty(99999999);
        pdRsvLmtQty.setPrdNo(11);
        pdRsvLmtQty.setBgnDt("20170101");
        pdRsvLmtQty.setWkdyCd("01");
        pdRsvLmtQty.setHhmm("11");
        lifePlusServiceImpl.updateProductRsvLmtQty(pdRsvLmtQty);
    }

    @Test
    public void isUpdateOrdRsv() {
        HashMap hashMap = new HashMap();
        hashMap.put("ordNo", 999999999);
        hashMap.put("rsvNo", 999999999);
        lifePlusServiceImpl.isUpdateOrdRsv(hashMap);
    }

    @Test
    public void updateRsvTmtr() {
        TrOrdRsvTmtr trOrdRsvTmtr = new TrOrdRsvTmtr();
        trOrdRsvTmtr.setRsvNo(99999999);
        trOrdRsvTmtr.setUpdateNo(9999);
        trOrdRsvTmtr.setRsvTmtr(9999);
        trOrdRsvTmtr.setRsvCnfrmDt("20170101");
        trOrdRsvTmtr.setPtnrEmpNo(999999);

        lifePlusServiceImpl.updateRsvTmtr(trOrdRsvTmtr);
    }

    @Test
    public void updateRsvDlv() {
        TrOrdRsvDlv trOrdRsvDlv = new TrOrdRsvDlv();
        trOrdRsvDlv.setRsvNo(99999999);
        trOrdRsvDlv.setUpdateNo(9999);
        trOrdRsvDlv.setRsvTmtr(9999);
        trOrdRsvDlv.setRsvDlvCnfrmDt("20170101");
        trOrdRsvDlv.setDlvPtnrEmpNo("999999");

        lifePlusServiceImpl.updateRsvDlv(trOrdRsvDlv);
    }

    @Test
    public void updateRsvPrdCont() {
        TrOrdRsv trOrdRsv = new TrOrdRsv();
        trOrdRsv.setPrdAddInfoCont("aa");
        trOrdRsv.setUpdateNo(99);
        trOrdRsv.setRsvNo(99999999);
        lifePlusServiceImpl.updateRsvPrdCont(trOrdRsv);
    }

    @Test
    public void updateRsvTmtrStat() {
        TrOrdRsvTmtr trOrdRsvTmtr = new TrOrdRsvTmtr();
        trOrdRsvTmtr.setUpdateNo(99);
        trOrdRsvTmtr.setRsvNo(9999999);
        trOrdRsvTmtr.setRsvTmtr(9999999);
        trOrdRsvTmtr.setRsvCnfrmDt("20170101");
        trOrdRsvTmtr.setRsvTmtr(999999);
        trOrdRsvTmtr.setPtnrEmpNo(99999);
        lifePlusServiceImpl.updateRsvTmtrStat(trOrdRsvTmtr);
    }

    @Test
    public void updateRsvDlvStat() {
        TrOrdRsvDlv trOrdRsvDlv = new TrOrdRsvDlv();
        trOrdRsvDlv.setUpdateNo(999);
        trOrdRsvDlv.setRsvNo(999999999);
        trOrdRsvDlv.setRsvTmtr(99999999);
        trOrdRsvDlv.setRsvDlvStatCd("01");
        trOrdRsvDlv.setRsvDlvCnfrmDt("20170101");
        trOrdRsvDlv.setDlvPtnrEmpNo("999999");

        lifePlusServiceImpl.updateRsvDlvStat(trOrdRsvDlv);
    }

    @Test
    public void getBsnsHrMgtMaster() {
        long strNo = 20028212;
        lifePlusServiceImpl.getBsnsHrMgtMaster(strNo);
    }

    @Test
    public void WoffWkdyListCd() {
        WoffWkdyListCd[] retStrs = WoffWkdyListCd.values();
        //for(int i=0; i<retStrs.length; i++) {
            //System.out.println("aa= " + retStrs[i].getGrpCd() + " : " + retStrs[i].getGrpCdNm() + " : " + retStrs[i].getDtlsCd() + " : " + retStrs[i].getDtlsComNm() + " : " + retStrs[i].getCdVal1() + " : " + retStrs[i].getCdVal2());
        //}
        Assert.assertSame(7, retStrs.length);
    }

    @Test
    public void insertBsnsHrMgtMaster() {
        BsnsHrMgtMasterVO bsnsHrMgtMasterVO = new BsnsHrMgtMasterVO();
        bsnsHrMgtMasterVO.setStrNo(1);
        bsnsHrMgtMasterVO.setSaleBgnHm("0000");
        bsnsHrMgtMasterVO.setSaleEndHm("1200");
        bsnsHrMgtMasterVO.setWoffWkdyList("5,7,1");
        bsnsHrMgtMasterVO.setPhldyWoffYn("N");
        bsnsHrMgtMasterVO.setRsvCanWk(2);
        bsnsHrMgtMasterVO.setRsvDisabCndtCd("02");
        bsnsHrMgtMasterVO.setRsvDisabHhCd(null);
        bsnsHrMgtMasterVO.setSaleEndOrdCanHhCd("N");
        bsnsHrMgtMasterVO.setSaleEndOrdCanHhCd(null);
        bsnsHrMgtMasterVO.setCreateNo(1);
        bsnsHrMgtMasterVO.setUpdateNo(1);
        lifePlusMapper.insertBsnsHrMgtMaster(bsnsHrMgtMasterVO);
    }

    @Test
    public void insertBsnsHrMgtDetailExct() {
        BsnsHrMgtDetailExctVO bsnsHrMgtDetailExctVO = new BsnsHrMgtDetailExctVO();
        bsnsHrMgtDetailExctVO.setStrNo(1);
        bsnsHrMgtDetailExctVO.setWkdyCd("2");
        bsnsHrMgtDetailExctVO.setExctBgnHm("0000");
        bsnsHrMgtDetailExctVO.setExctEndHm("1200");
        bsnsHrMgtDetailExctVO.setCreateNo(1);
        bsnsHrMgtDetailExctVO.setUpdateNo(1);
        lifePlusMapper.insertBsnsHrMgtDetailExct(bsnsHrMgtDetailExctVO);
    }

    @Test
    public void insertBsnsHrMgtDetailWoff() {
        BsnsHrMgtDetailWoffVO bsnsHrMgtDetailWoffVO = new BsnsHrMgtDetailWoffVO();
        bsnsHrMgtDetailWoffVO.setStrNo(1);
        bsnsHrMgtDetailWoffVO.setWoffDt("20170101");
        bsnsHrMgtDetailWoffVO.setCreateNo(1);
        bsnsHrMgtDetailWoffVO.setUpdateNo(1);
        lifePlusMapper.insertBsnsHrMgtDetailWoff(bsnsHrMgtDetailWoffVO);
    }

    @Test
    public void deleteRsvLmtQtyCountForDay() {
        BsnsHrMgtMasterVO bsnsHrMgtMasterVO = new BsnsHrMgtMasterVO();
        bsnsHrMgtMasterVO.setShopNo(1);
        bsnsHrMgtMasterVO.setStrNo(1);
        String[] woffWkdyArr = "5,1,2".split(",");
        bsnsHrMgtMasterVO.setWoffWkdyArr(woffWkdyArr);
        lifePlusMapper.deleteRsvLmtQtyCountForDay(bsnsHrMgtMasterVO);
    }

    @Test
    public void deleteRsvLmtQtyCountForHoliDay() {
        BsnsHrMgtMasterVO bsnsHrMgtMasterVO = new BsnsHrMgtMasterVO();
        bsnsHrMgtMasterVO.setShopNo(1);
        bsnsHrMgtMasterVO.setStrNo(1);
        lifePlusMapper.deleteRsvLmtQtyCountForHoliDay(bsnsHrMgtMasterVO);
    }

    @Test
    public void deleteRsvLmtQtyCountForDate() {
        BsnsHrMgtDetailWoffVO bsnsHrMgtDetailWoffVO = new BsnsHrMgtDetailWoffVO();
        bsnsHrMgtDetailWoffVO.setShopNo(1);
        bsnsHrMgtDetailWoffVO.setStrNo(1);
        bsnsHrMgtDetailWoffVO.setWoffDt("20170101");
        lifePlusMapper.deleteRsvLmtQtyCountForDate(bsnsHrMgtDetailWoffVO);
    }

    @Test
    public void modBsnsHrMgtMaster() {
        BsnsHrMgtMasterVO bsnsHrMgtMasterVO = new BsnsHrMgtMasterVO();
        bsnsHrMgtMasterVO.setStrNo(1);
        bsnsHrMgtMasterVO.setSaleBgnHm("0000");
        bsnsHrMgtMasterVO.setSaleEndHm("1200");
        bsnsHrMgtMasterVO.setWoffWkdyList("5,7,1");
        bsnsHrMgtMasterVO.setPhldyWoffYn("N");
        bsnsHrMgtMasterVO.setRsvCanWk(2);
        bsnsHrMgtMasterVO.setRsvDisabCndtCd("02");
        bsnsHrMgtMasterVO.setRsvDisabHhCd(null);
        bsnsHrMgtMasterVO.setSaleEndOrdCanHhCd("N");
        bsnsHrMgtMasterVO.setSaleEndOrdCanHhCd(null);
        bsnsHrMgtMasterVO.setCreateNo(1);
        bsnsHrMgtMasterVO.setUpdateNo(1);
        lifePlusMapper.modBsnsHrMgtMaster(bsnsHrMgtMasterVO);
    }

    @Test
    public void deleteBsnsHrMgtDetailExct() {
        long strNo = 1;
        lifePlusMapper.deleteBsnsHrMgtDetailExct(strNo);
    }

    @Test
    public void deleteBsnsHrMgtDetailWoff() {
        long strNo = 1;
        lifePlusMapper.deleteBsnsHrMgtDetailWoff(strNo);
    }

}
