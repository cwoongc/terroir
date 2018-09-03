package com.elevenst.terroir.product.registration.lifeplus.mapper;

import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanPlcy;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailExctVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailWoffVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtMasterVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdSvcCanRgnVO;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvLmtQty;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvSchdlInfo;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanRgn;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvDlv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvTmtr;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvSchdlInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface LifePlusMapper {
    long getPdSvcCanPlcySeq(PdSvcCanPlcy pdSvcCanPlcy);
    List<PdSvcCanRgnVO> getPdSvcCanRgnList(long svcCanPlcySeq);
    long insertPdSvcCanPlcy(PdSvcCanPlcy pdSvcCanPlcy);
    void updatePdSvcCanPlcy(PdSvcCanPlcy pdSvcCanPlcy);
    void deletePdSvcCanRgn(long svcCanPlcySeq);
    void insertPdSvcCanRgn(PdSvcCanRgn pdSvcCanRgn);
    void insertBsnsHrMgtMaster(BsnsHrMgtMasterVO bsnsHrMgtMasterVO);
    void insertBsnsHrMgtDetailExct(BsnsHrMgtDetailExctVO bsnsHrMgtDetailExctVO);
    void insertBsnsHrMgtDetailWoff(BsnsHrMgtDetailWoffVO bsnsHrMgtDetailWoffVO);
    void deleteRsvLmtQtyCountForDay(BsnsHrMgtMasterVO bsnsHrMgtMasterVO);
    void deleteRsvLmtQtyCountForHoliDay(BsnsHrMgtMasterVO bsnsHrMgtMasterVO);
    void deleteRsvLmtQtyCountForDate(BsnsHrMgtDetailWoffVO bsnsHrMgtDetailWoffVO);
    void deleteBsnsHrMgtDetailExct(long strNo);
    void deleteBsnsHrMgtDetailWoff(long strNo);
    void modBsnsHrMgtMaster(BsnsHrMgtMasterVO bsnsHrMgtMasterVO);

    List<PdRsvSchdlInfoVO> getPrdRsvSchdlInfoList(PdRsvSchdlInfoVO pdRsvSchdlInfoVO);
    List<PdRsvSchdlInfoVO> getPrdRsvLmtQtyList(PdRsvSchdlInfoVO pdRsvSchdlInfoVO);
    List<HashMap> getHolidayList();
    HashMap getPdStrSaleTimeList(HashMap hashMap);
    List<HashMap> getPdStrWoffList(HashMap hashMap);
    void insertProductRsvSchedule(PdRsvSchdlInfo pdRsvSchdlInfo);
    void insertProductRsvLmtQty(PdRsvLmtQty pdRsvLmtQty);
    void deleteProductRsvScheduleList(PdRsvSchdlInfo pdRsvSchdlInfo);
    void deleteProductRsvLmtQtyList(PdRsvLmtQty pdRsvLmtQty);
    PdRsvLmtQty getPrdRsvLmtQty(PdRsvLmtQty pdRsvLmtQty);

    BsnsHrMgtMasterVO getBsnsHrMgtMaster(long strNo);
    List<BsnsHrMgtDetailExctVO> getBsnsHrMgtDetailExct(long strNo);
    List<BsnsHrMgtDetailWoffVO> getBsnsHrMgtDetailWoff(long strNo);

    int updateProductRsvLmtQty(PdRsvLmtQty pdRsvLmtQty);
    int updateOrdRsv(HashMap hashMap);
    int updateRsvTmtr(TrOrdRsvTmtr trOrdRsvTmtr);
    int updateRsvDlv(TrOrdRsvDlv trOrdRsvDlv);
    int updateRsvPrdCont(TrOrdRsv trOrdRsv);
    int updateRsvTmtrStat(TrOrdRsvTmtr trOrdRsvTmtr);
    int updateRsvDlvStat(TrOrdRsvDlv trOrdRsvDlv);

    String selectRgnAddrCd(PdSvcCanRgnVO pdSvcCanRgnVO);
}
