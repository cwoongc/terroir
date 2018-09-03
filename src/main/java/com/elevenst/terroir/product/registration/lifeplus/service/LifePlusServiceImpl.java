package com.elevenst.terroir.product.registration.lifeplus.service;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.common.util.StripHTMLTag;
import com.elevenst.terroir.product.registration.lifeplus.code.SvcCnAreaTypCd;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvLmtQty;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdRsvSchdlInfo;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanPlcy;
import com.elevenst.terroir.product.registration.lifeplus.entity.PdSvcCanRgn;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvDlv;
import com.elevenst.terroir.product.registration.lifeplus.entity.TrOrdRsvTmtr;
import com.elevenst.terroir.product.registration.lifeplus.exception.LifePlusException;
import com.elevenst.terroir.product.registration.lifeplus.mapper.LifePlusMapper;
import com.elevenst.terroir.product.registration.lifeplus.message.LifePlusExceptionMessage;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailExctVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtDetailWoffVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.BsnsHrMgtMasterVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvLmtQtyVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdSvcCanPlcyVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdSvcCanRgnVO;
import com.elevenst.terroir.product.registration.lifeplus.vo.PdRsvSchdlInfoVO;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
public class LifePlusServiceImpl {

    @Autowired
    LifePlusMapper lifePlusMapper;

    public long getPdSvcCanPlcySeq(PdSvcCanPlcy pdSvcCanPlcy) throws LifePlusException {
        try {
            return lifePlusMapper.getPdSvcCanPlcySeq(pdSvcCanPlcy);
        } catch (LifePlusException ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_SERVICE_PLCY_ERROR, ex);
        } catch (Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_SERVICE_PLCY_ERROR, ex);
        }
    }

    public List<PdSvcCanRgnVO> getPdSvcCanRgnList(long svcCanPlcySeq) throws LifePlusException {
        try {
            return lifePlusMapper.getPdSvcCanRgnList(svcCanPlcySeq);
        } catch (LifePlusException e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_SERVICE_RGN_ERROR, e);
        }
    }

    public long insertPdSvcCanPlcy(PdSvcCanPlcy pdSvcCanPlcy) throws LifePlusException {
        try {
            return lifePlusMapper.insertPdSvcCanPlcy(pdSvcCanPlcy);
        } catch (LifePlusException e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_INSERT_SERVICE_PLCY_ERROR,e);
        }
    }

    public void updatePdSvcCanPlcy(PdSvcCanPlcy pdSvcCanPlcy) throws LifePlusException {
        try {
            lifePlusMapper.updatePdSvcCanPlcy(pdSvcCanPlcy);
        } catch (LifePlusException e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_SERVICE_PLCY_ERROR,e);
        }
    }

    public void deletePdSvcCanRgn(long svcCanPlcySeq) throws LifePlusException {
        try {
            lifePlusMapper.deletePdSvcCanRgn(svcCanPlcySeq);
        } catch (LifePlusException e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_DELETE_SERVICE_PLCY_ERROR, e);
        }
    }

    public void insertPdSvcCanRgn(PdSvcCanRgn pdSvcCanRgn) throws LifePlusException {
        try {
            lifePlusMapper.insertPdSvcCanRgn(pdSvcCanRgn);
        } catch (Exception e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_INSERT_SERVICE_RGN_ERROR, e);
        }
    }

    //상품예약일정정보목록 조회
    public List<PdRsvSchdlInfoVO> getPrdRsvSchdlInfoList(PdRsvSchdlInfoVO pdRsvSchdlInfoVO) throws LifePlusException {
        try{
            return (List<PdRsvSchdlInfoVO>) lifePlusMapper.getPrdRsvSchdlInfoList(pdRsvSchdlInfoVO);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_SCHDL_INFO_ERROR, ex);
        }
    }

    //상품예약제한수량목록 조회
    public List<PdRsvSchdlInfoVO> getPrdRsvLmtQtyList(PdRsvSchdlInfoVO pdRsvSchdlInfoVO) throws LifePlusException {
        try{
            return (List<PdRsvSchdlInfoVO>) lifePlusMapper.getPrdRsvLmtQtyList(pdRsvSchdlInfoVO);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_LMT_QTY_LIST_ERROR, ex);
        }
    }

    //공휴일 목록 조회
    public List<HashMap> getHolidayList() throws LifePlusException {
        try{
            return (List<HashMap>) lifePlusMapper.getHolidayList();
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_HOLIDAY_ERROR, ex);
        }
    }
    //영업시간 목록 조회
    public HashMap getPdStrSaleTimeList(HashMap hashMap) throws LifePlusException {
        try{
            return lifePlusMapper.getPdStrSaleTimeList(hashMap);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_SALE_TIME_ERROR, ex);
        }
    }
    //영업시간 휴무일 조회
    public List<HashMap> getPdStrWoffList(HashMap hashMap) throws LifePlusException {
        try{
            return lifePlusMapper.getPdStrWoffList(hashMap);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_STR_WOFF_ERROR, ex);
        }
    }

    //상품예약일정정보 등록
    public void insertProductRsvSchedule(PdRsvSchdlInfo pdRsvSchdlInfo) throws LifePlusException {
        try {
            lifePlusMapper.insertProductRsvSchedule(pdRsvSchdlInfo);
        } catch (Exception e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_INSERT_SCHDL_ERROR, e);
        }

    }
    //상품예약제한수량 등록
    public void insertProductRsvLmtQty(PdRsvLmtQty pdRsvLmtQty) throws LifePlusException {
        try {
            lifePlusMapper.insertProductRsvLmtQty(pdRsvLmtQty);
        } catch (Exception e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_INSERT_LMT_QTY_ERROR, e);
        }

    }
    //상품예약일정정보 삭제
    public void deleteProductRsvScheduleList(PdRsvSchdlInfo pdRsvSchdlInfo) throws LifePlusException {
        try {
            lifePlusMapper.deleteProductRsvScheduleList(pdRsvSchdlInfo);
        } catch (Exception e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_DELETE_SCHDL_ERROR, e);
        }

    }
    //상품예약제한수량 삭제
    public void deleteProductRsvLmtQtyList(PdRsvLmtQty pdRsvLmtQty) throws LifePlusException {
        try {
            lifePlusMapper.deleteProductRsvLmtQtyList(pdRsvLmtQty);
        } catch (Exception e) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_DELETE_LMT_QTY_ERROR, e);
        }

    }

    /**
     *상품예약제한수량목록 조회
     */
    public PdRsvLmtQty getPrdRsvLmtQty(PdRsvLmtQty pdRsvLmtQty) throws LifePlusException {
        try{
            return lifePlusMapper.getPrdRsvLmtQty(pdRsvLmtQty);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_GET_LMT_QTY_INFO_ERROR, ex);
        }
    }

    /**
     * 상품 예약제한 수량 차감
     */
    public int updateProductRsvLmtQty(PdRsvLmtQty pdRsvLmtQty) throws LifePlusException {
        try{
            return lifePlusMapper.updateProductRsvLmtQty(pdRsvLmtQty);
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_LMT_QTY_ERROR, ex);
        }
    }

    /**
     * 예약정보 업데이트
     * @param hashMap
     * @return
     * @throws Exception
     */
    public boolean isUpdateOrdRsv(HashMap hashMap) throws LifePlusException {
        boolean isResult = false;

        try {
            if ( lifePlusMapper.updateOrdRsv(hashMap) == 1 ) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_ERROR, ex);
        }

        return isResult;
    }
    public boolean updateRsvTmtr(TrOrdRsvTmtr trOrdRsvTmtr) throws LifePlusException{
        boolean isResult = false;

        try {
            if ( lifePlusMapper.updateRsvTmtr(trOrdRsvTmtr) == 1 ) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_TMTR_ERROR, ex);
        }
        return isResult;
    }


    public boolean updateRsvDlv(TrOrdRsvDlv trOrdRsvDlv) throws LifePlusException{
        boolean isResult = false;
        try {
            if ( lifePlusMapper.updateRsvDlv(trOrdRsvDlv) == 1 ) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_DLV_ERROR, ex);
        }
        return isResult;
    }


    public boolean updateRsvPrdCont(TrOrdRsv trOrdRsv) throws LifePlusException{
        boolean isResult = false;
        try {
            if(lifePlusMapper.updateRsvPrdCont(trOrdRsv) == 1) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_ADD_CONT_ERROR, ex);
        }
        return isResult;
    }


    public boolean updateRsvTmtrStat(TrOrdRsvTmtr trOrdRsvTmtr) throws LifePlusException{
        boolean isResult = false;
        try {
            if(lifePlusMapper.updateRsvTmtrStat(trOrdRsvTmtr) == 1) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_TMTR_STAT_ERROR, ex);
        }
        return isResult;
    }


    public boolean updateRsvDlvStat(TrOrdRsvDlv trOrdRsvDlv)  throws LifePlusException{
        boolean isResult = false;
        try {
            if ( lifePlusMapper.updateRsvDlvStat(trOrdRsvDlv) == 1 ) {
                isResult = true;
            }
        } catch(Exception ex) {
            throw new LifePlusException(LifePlusExceptionMessage.LIFE_PLUS_UPDATE_RSV_DLV_STAT_ERROR, ex);
        }
        return isResult;
    }

    public BsnsHrMgtMasterVO getBsnsHrMgtMaster(long strNo) throws LifePlusException {
        try {
            BsnsHrMgtMasterVO bsnsHrMgtMaster = lifePlusMapper.getBsnsHrMgtMaster(strNo);

            if(bsnsHrMgtMaster != null){
                List<BsnsHrMgtDetailExctVO> bsnsHrMgtDetailExctVOList = lifePlusMapper.getBsnsHrMgtDetailExct(strNo);
                if(bsnsHrMgtDetailExctVOList != null && bsnsHrMgtDetailExctVOList.size() > 0){
                    bsnsHrMgtMaster.setExceptionAdd("Y");
                    bsnsHrMgtMaster.setBsnsHrMgtDetailExctVO(bsnsHrMgtDetailExctVOList);
                }

                List<BsnsHrMgtDetailWoffVO> bsnsHrMgtDetailWoffVOList = lifePlusMapper.getBsnsHrMgtDetailWoff(strNo);
                if(bsnsHrMgtDetailWoffVOList != null && bsnsHrMgtDetailWoffVOList.size() > 0){
                    bsnsHrMgtMaster.setSpecifiedDate("Y");
                    bsnsHrMgtMaster.setBsnsHrMgtDetailWoffVO(bsnsHrMgtDetailWoffVOList);
                }
            }

            return bsnsHrMgtMaster;
        } catch (Exception e) {
            throw new LifePlusException("");
        }
    }

    /**
     * HTML tag 제거 유틸
     * @param paramMap
     * @param noRemoveKeys(제거안할 키값 리스트 ex.에디터 사용)
     * @return
     */
    public static Map<String, Object> removeHtmlInParams(Map<String, Object> paramMap, String[] noRemoveKeys) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            for (String key : paramMap.keySet()) {
                if (paramMap.get(key) instanceof String) {
                    boolean isNoRemoveKey = false;
                    if (noRemoveKeys != null && noRemoveKeys.length > 0) {
                        for (String noRemoveKey : noRemoveKeys) {
                            if (StringUtils.equals(key, noRemoveKey)) {
                                isNoRemoveKey = true;
                                break;
                            }
                        }
                    }
                    if (isNoRemoveKey) {
                        resultMap.put(key, paramMap.get(key).toString());
                    } else {
                        String result = StringUtil.toXssHtml2(StripHTMLTag.removeInvalidTag(paramMap.get(key).toString()));
                        resultMap.put(key, result);
                    }
                } else {
                    resultMap.put(key, paramMap.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public void mergeLifePlusInfoProcess(ProductVO productVO) throws LifePlusException{
        this.mergeVisitingSvcProduct(productVO);
        this.mergeRsvSchdlPrdouct(productVO);
    }

    public void mergeRsvSchdlPrdouct(ProductVO productVO) throws LifePlusException{

        //예약스케쥴 등록
        if ("Y".equals(productVO.getBaseVO().getRsvSchdlModifyYn())){

            PdRsvLmtQty pdRsvLmtQty = new PdRsvLmtQty();
            PdRsvSchdlInfo pdRsvSchdlInfo = new PdRsvSchdlInfo();

            pdRsvLmtQty.setPrdNo(productVO.getPrdNo());
            pdRsvSchdlInfo.setPrdNo(productVO.getPrdNo());

            this.deleteProductRsvLmtQtyList(pdRsvLmtQty);
            this.deleteProductRsvScheduleList(pdRsvSchdlInfo);

            for(PdRsvSchdlInfoVO pdRsvSchdlInfoVO : productVO.getBaseVO().getPdRsvSchdlInfoVOList()){
                pdRsvSchdlInfo = new PdRsvSchdlInfo();
                copyProperties(pdRsvSchdlInfoVO, pdRsvSchdlInfo);
                pdRsvSchdlInfo.setPrdNo(productVO.getPrdNo());
                pdRsvSchdlInfo.setCreateNo(productVO.getUpdateNo());
                pdRsvSchdlInfo.setUpdateNo(productVO.getUpdateNo());
                this.insertProductRsvSchedule(pdRsvSchdlInfo);
            }

            for(PdRsvLmtQtyVO pdRsvLmtQtyVO : productVO.getBaseVO().getPdRsvLmtQtyVOList()){
                pdRsvLmtQty = new PdRsvLmtQty();
                copyProperties(pdRsvLmtQtyVO, pdRsvLmtQty);
                pdRsvLmtQty.setPrdNo(productVO.getPrdNo());
                pdRsvLmtQty.setCreateNo(productVO.getUpdateNo());
                pdRsvLmtQty.setUpdateNo(productVO.getUpdateNo());
                this.insertProductRsvLmtQty(pdRsvLmtQty);
            }

        }
    }

    public void mergeVisitingSvcProduct(ProductVO productVO) throws LifePlusException{
        boolean isVisitingSvc = GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.VISITING_SERVICE);
        boolean isVisitingPickDlv = GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.VISITING_PICKUP_DELIVERY);
        boolean hasSvcCnArea = StringUtil.isNotEmpty(productVO.getBaseVO().getSvcCnAreaRgnClfCd());
        boolean isSvcCnAreaSameAsShop = GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSvcCnAreaRgnClfCd(), SvcCnAreaTypCd.HEADSHOP);


        if(isVisitingSvc || isVisitingPickDlv){
            //1. 본점정보 미등록인 경우 등록 실패
            if(productVO.getBaseVO().getShopNo() <= 0){
                throw new LifePlusException("서비스가능지역을 설정하세요.");
            }


            PdSvcCanPlcy pdSvcCanPlcy = new PdSvcCanPlcy();
            pdSvcCanPlcy.setSellerMemNo(productVO.getSelMnbdNo());
            pdSvcCanPlcy.setSvcCanPlcyObjNo(productVO.getBaseVO().getShopNo());
            pdSvcCanPlcy.setSvcCanPlcyClfCd(SvcCnAreaTypCd.HEADSHOP.getDtlsCd());
            long shopPlcySeq = this.getPdSvcCanPlcySeq(pdSvcCanPlcy);
            List<PdSvcCanRgnVO> shopSvcCanRgnList;

            //2. 본점 서비스가능지역 미등록인 경우 등록 실패
            if (shopPlcySeq <= 0) {
                throw new LifePlusException("본점의 서비스가능지역을 설정하세요.");
            } else {
                shopSvcCanRgnList = this.getPdSvcCanRgnList(shopPlcySeq);
                if (shopSvcCanRgnList == null || shopSvcCanRgnList.size() <= 0) {
                    throw new LifePlusException("본점의 서비스가능지역을 설정하세요.");
                }
            }

            StringBuffer rgnAddrCdList = new StringBuffer();

            //3. 본점정보 및 본점 서비스가능지역 등록시에만 상품 서비스가능지역 등록
            if (isSvcCnAreaSameAsShop) { //본점 정보와 동일
                if (shopPlcySeq > 0) {
                    pdSvcCanPlcy.setSvcCanPlcyObjNo(productVO.getPrdNo());
                    pdSvcCanPlcy.setSvcCanPlcyClfCd(SvcCnAreaTypCd.PRODUCT.getDtlsCd());
                    long productPlcySeq = this.getPdSvcCanPlcySeq(pdSvcCanPlcy);

                    Collections.sort(shopSvcCanRgnList, new Comparator<PdSvcCanRgnVO>() {
                        @Override
                        public int compare(PdSvcCanRgnVO first, PdSvcCanRgnVO second) {
                            int firstRgnAddrCd = Integer.parseInt(first.getRgnAddrCd().trim());
                            int secondRgnAddrCd = Integer.parseInt(second.getRgnAddrCd().trim());

                            if (firstRgnAddrCd > secondRgnAddrCd) {
                                return 1;
                            } else if (firstRgnAddrCd < secondRgnAddrCd) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });

                    for (int i = 0; i < shopSvcCanRgnList.size(); i++) {
                        if (i == 0) rgnAddrCdList.append(shopSvcCanRgnList.get(i).getRgnAddrCd());
                        else rgnAddrCdList.append(",").append(shopSvcCanRgnList.get(i).getRgnAddrCd());
                    }
                    pdSvcCanPlcy.setRgnAddrCdList(rgnAddrCdList.toString());

                    if (productPlcySeq <= 0) {
                        pdSvcCanPlcy.setCreateNo(productVO.getUpdateNo());
                        pdSvcCanPlcy.setUpdateNo(productVO.getUpdateNo());
                        productPlcySeq = this.insertPdSvcCanPlcy(pdSvcCanPlcy);
                    } else {
                        pdSvcCanPlcy.setSvcCanPlcySeq(productPlcySeq);
                        this.updatePdSvcCanPlcy(pdSvcCanPlcy);
                    }

                    this.deletePdSvcCanRgn(productPlcySeq);
                    for (PdSvcCanRgnVO shopSvcCanRgn : shopSvcCanRgnList) {

                        PdSvcCanRgn pdSvcCanRgn = new PdSvcCanRgn();
                        copyProperties(shopSvcCanRgn, pdSvcCanRgn);
                        pdSvcCanRgn.setSvcCanPlcySeq(productPlcySeq);
                        pdSvcCanRgn.setUeupmyonCd(shopSvcCanRgn.getLawDongCd());
                        pdSvcCanRgn.setCreateNo(productVO.getUpdateNo());
                        pdSvcCanRgn.setUpdateNo(productVO.getUpdateNo());

                        this.insertPdSvcCanRgn(pdSvcCanRgn);
                    }
                }
            } else if (hasSvcCnArea) { //직접 설정하여 등록
                //1. 파라미터 세팅
                //1-1. 서비스가능정책
                pdSvcCanPlcy.setSvcCanPlcyClfCd(SvcCnAreaTypCd.PRODUCT.getDtlsCd());
                pdSvcCanPlcy.setSvcCanPlcyObjNo(productVO.getPrdNo());
                pdSvcCanPlcy.setCreateNo(productVO.getUpdateNo());
                pdSvcCanPlcy.setUpdateNo(productVO.getUpdateNo());

                //1-2. 서비스가능지역
                List<PdSvcCanRgnVO> svcCanRgnVOList = productVO.getBaseVO().getSvcCanRgnVOList();
                List<PdSvcCanRgn> pdSvcCanRgnList = new ArrayList<PdSvcCanRgn>();

                if (svcCanRgnVOList != null
                    && svcCanRgnVOList.size() > ProductConstDef.MAX_SVC_CN_AREA_CNT){
                    throw new LifePlusException("서비스가능지역 설정은 최대 "+ProductConstDef.MAX_SVC_CN_AREA_CNT+"개까지 가능합니다.");
                }


                for (PdSvcCanRgnVO pdSvcCanRgnVO : svcCanRgnVOList) {
                    PdSvcCanRgn pdSvcCanRgn = new PdSvcCanRgn();

                    String rgnAddrCd = StringUtil.isNotEmpty(pdSvcCanRgnVO.getRgnAddrCd()) ? pdSvcCanRgnVO.getRgnAddrCd() : lifePlusMapper.selectRgnAddrCd(pdSvcCanRgnVO);
                    pdSvcCanRgn.setRgnClfCd(pdSvcCanRgnVO.getRgnClfCd());
                    pdSvcCanRgn.setSidoNm(pdSvcCanRgnVO.getSidoNm());
                    pdSvcCanRgn.setSigunguNm(pdSvcCanRgnVO.getSigunguNm());
                    pdSvcCanRgn.setRgnAddrCd(rgnAddrCd);
                    pdSvcCanRgn.setUeupmyonNm(pdSvcCanRgnVO.getUeupmyonNm().trim());
                    pdSvcCanRgn.setUeupmyonCd(pdSvcCanRgnVO.getUeupmyonCd());
                    pdSvcCanRgn.setCreateNo(productVO.getUpdateNo());
                    pdSvcCanRgn.setUpdateNo(productVO.getUpdateNo());

                    pdSvcCanRgnList.add(pdSvcCanRgn);
                }
                Collections.sort(pdSvcCanRgnList, new Comparator<PdSvcCanRgn>() {
                    @Override
                    public int compare(PdSvcCanRgn first, PdSvcCanRgn second) {
                        int firstRgnAddrCd = Integer.parseInt(first.getRgnAddrCd().trim());
                        int secondRgnAddrCd = Integer.parseInt(second.getRgnAddrCd().trim());

                        if (firstRgnAddrCd > secondRgnAddrCd) {
                            return 1;
                        } else if (firstRgnAddrCd < secondRgnAddrCd) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                for (int i = 0; i < pdSvcCanRgnList.size(); i++) {
                    if (i == 0) rgnAddrCdList.append(pdSvcCanRgnList.get(i).getRgnAddrCd());
                    else rgnAddrCdList.append(",").append(pdSvcCanRgnList.get(i).getRgnAddrCd());
                }
                pdSvcCanPlcy.setRgnAddrCdList(rgnAddrCdList.toString());
//                pdSvcCanPlcy.setSvcCnAreaDetailBOList(svcCnAreaDetailBOList);

                //2. DB 저장
                //2-1. 서비스가능정책
                long svcCanPlcySeq = this.getPdSvcCanPlcySeq(pdSvcCanPlcy);
                if (svcCanPlcySeq <= 0) {
                    svcCanPlcySeq = this.insertPdSvcCanPlcy(pdSvcCanPlcy);
                } else {
                    pdSvcCanPlcy.setSvcCanPlcySeq(svcCanPlcySeq);
                    this.updatePdSvcCanPlcy(pdSvcCanPlcy);
                }

                //2-2. 서비스가능지역
                this.deletePdSvcCanRgn(svcCanPlcySeq);
                for (PdSvcCanRgn pdSvcCanRgn : pdSvcCanRgnList) {
                    pdSvcCanRgn.setSvcCanPlcySeq(svcCanPlcySeq);
                    this.insertPdSvcCanRgn(pdSvcCanRgn);
                }
            } else {
                throw new LifePlusException("서비스가능지역을 설정하세요.");
            }
        }

    }
}
