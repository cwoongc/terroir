package com.elevenst.terroir.product.registration.etc.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.etc.entity.PdPrdEtc;
import com.elevenst.terroir.product.registration.etc.entity.PdPrdOthers;
import com.elevenst.terroir.product.registration.etc.mapper.EtcMapper;
import com.elevenst.terroir.product.registration.etc.validate.EtcValidate;
import com.elevenst.terroir.product.registration.etc.vo.PrdEtcVO;
import com.elevenst.terroir.product.registration.etc.vo.PrdOthersVO;
import com.elevenst.terroir.product.registration.option.service.OptionServiceImpl;
import com.elevenst.terroir.product.registration.option.vo.OptionVO;
import com.elevenst.terroir.product.registration.option.vo.ProductStockVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.validate.SellerValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class EtcServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    EtcMapper etcMapper;

    @Autowired
    EtcValidate etcValidate;

    @Autowired
    OptionServiceImpl optionService;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    SellerValidate sellerValidate;

    @Autowired
    CategoryServiceImpl categoryService;

    public void setEtcProductVO(ProductVO productVO) {

        PrdOthersVO prdOthersVO = productVO.getPrdOthersVO();
        if(StringUtil.isNotEmpty(prdOthersVO.getBeefTraceStat())) {
            if(null != prdOthersVO) {
                String beefTraceStat = prdOthersVO.getBeefTraceStat();
                if("02".equals(beefTraceStat)) {
                    prdOthersVO.setBeefTraceNo("이력번호표시대상아님");
                }else if("03".equals(beefTraceStat)) {
                    prdOthersVO.setBeefTraceNo("상세설명 참조");
                }
            }
            prdOthersVO.setPrdNo(productVO.getPrdNo());
            productVO.setPrdOthersVO(prdOthersVO);
        }
    }

    public void checkEtcDataInsertProcess(ProductVO productVO) throws ProductException{

        this.setEtcProductVO(productVO);

        productVO.getPrdOthersVO().setPrdNo(productVO.getPrdNo());
        productVO.getOptionVO().setCreateNo(productVO.getCreateNo());
        productVO.getOptionVO().setUpdateNo(productVO.getUpdateNo());

        //구매 불가일 신규 등록
        if(StringUtil.isNotEmpty(productVO.getPrdOthersVO().getBuyDsblDyCd())) {
            this.mergeProductOthersBuyDsblDyInfo(productVO.getPrdOthersVO());
        }
        //제휴사상품구분코드
        if("Y".equals(productVO.getPrdCopyYn()) && sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo()))  {
            ProductStockVO productStockVO = new ProductStockVO();
            productStockVO.setPrdNo(productVO.getPrdNo());
            List<ProductStockVO> retProductStockList = optionService.getPdStockPtnrInfoByPrdNo(productStockVO);
            if(retProductStockList != null && retProductStockList.size() > 0) {
                mergeProductOthersPtnrInfo(productVO);
            }
        }

        //의료기기 정보 등록
        this.insertProdMedicalDeviceInfo(productVO.getPrdOthersVO());

        //축산물 이력 정보 존재 시
        if(StringUtil.isNotEmpty(productVO.getPrdOthersVO().getBeefTraceNo())) {
            this.insertBeefTraceInfo(productVO.getPrdOthersVO());
        }

        /*
            생활+ 와 지정배송일 주석 처리
        //생활+ 기타정보 등록/수정
        if(productVO.isLifeSvcTyp()) {
            setProductOthersForLife(productVO,productVO.getPrdNo());
        }else if(sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
            this.mergeProductOthersForPtnrPrdClf(productVO,productVO.getPrdNo());
        }

        if(sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
            this.updateProductOthersForPtnrPrdClf(productVO, productVO.getPrdNo());
        }
        */


        /*
         * 여행/디지털컨텐츠/소셜네트워크/티켓/e쿠폰(기프티콘시스템에서 API 인입) 상품일경우 상품 추가 정보 입력
         * 18:소셜유형상품 추가 2011.12.14 (여행유형은 카테고리의 상품 유형으로 비교(타운 상품추가로 인해 처리))
         * 13:카테고리 상품유형이 여행일 경우에 등록하는 상품 유형이 일반이 아닐 경우에만 입력
         */
        if ((ProductConstDef.PRD_TYP_CD_TOUR.equals(productVO.getCategoryVO().getDispCtgrPrdTypCd()) && !ProductConstDef.PRD_TYP_CD_NORMAL.equals(productVO.getBaseVO().getPrdTypCd()))
                || ProductConstDef.PRD_TYP_CD_DIGITAL.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_SOCIAL_NET.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_SOCIAL_TANGIBLE.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_TICKET.equals(productVO.getBaseVO().getPrdTypCd())
                || (ProductConstDef.PRD_TYP_CD_SMS_CUPN.equals(productVO.getBaseVO().getPrdTypCd()) && !StringUtil.isEmpty(productVO.getPrdEtcVO().getIssuCoLogoUrl()))
                || (ProductConstDef.PRD_TYP_CD_SMS.equals(productVO.getBaseVO().getPrdTypCd()) && !StringUtil.isEmpty(productVO.getPrdEtcVO().getIssuCoLogoUrl()))) {

            productVO.getPrdEtcVO().setPrdNo(productVO.getPrdNo());
            productVO.getPrdEtcVO().setCreateNo(productVO.getCreateNo());
            productVO.getPrdEtcVO().setUpdateNo(productVO.getCreateNo());
            this.insertProductEtc(productVO);
        }
        /*
         * Yes24 도서 직매입 입점 제휴
         */
        try {
            if(productVO.getPrdEtcVO() != null){
                //TODO CYB Yes24 입점 도서 체크 필요
                if(StringUtil.isNotEmpty(productVO.getPrdEtcVO().getBookClfCd())){

                    productVO.getPrdEtcVO().setPrdNo(productVO.getPrdNo());
                    productVO.getPrdEtcVO().setCreateNo(productVO.getCreateNo());
                    productVO.getPrdEtcVO().setUpdateNo(productVO.getCreateNo());

                    this.insertProductEtc(productVO);

                }
            }
        } catch (Exception ex) {
            throw new ProductException("Yes24 도서 직매입 입점 제휴 등록 오류", ex);
        }
        /*
         * HMALL 동영상 재생 관련
         */
        if(productVO.isHmallVod()) {
            productVO.getPrdEtcVO().setPrdNo(productVO.getPrdNo());
            productVO.getPrdEtcVO().setCreateNo(productVO.getCreateNo());
            productVO.getPrdEtcVO().setUpdateNo(productVO.getCreateNo());
            productVO.getPrdEtcVO().setVodBrowserTyp(productVO.getPrdEtcVO().getVodBrowserTyp());
            productVO.getPrdEtcVO().setVodUrl(productVO.getPrdEtcVO().getVodUrl());
            this.insertProductEtc(productVO);
        }
    }

    public void checkEtcDataUpdateProcess(ProductVO productVO) {

        this.setEtcProductVO(productVO);

        //축산물 이력 정보 존재 시
        if(StringUtil.isNotEmpty(productVO.getPrdOthersVO().getBeefTraceNo())) {
            this.insertBeefTraceInfo(productVO.getPrdOthersVO());
        }

        //생활+ 기타정보 등록/수정
        if(productVO.isLifeSvcTyp()) {
            setProductOthersForLife(productVO,productVO.getPrdNo());
        }else if(sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
            this.mergeProductOthersForPtnrPrdClf(productVO,productVO.getPrdNo());
        }

        if(sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
            this.updateProductOthersForPtnrPrdClf(productVO, productVO.getPrdNo());
        }


        /*
         * 디지털 컨텐츠 / 여행 / 소셜네트워크 / 티켓 상품일경우 상품 추가 정보 수정
         * 18:소셜유형상품 추가 2011.12.14(여행 카테고리에 타운 서비스유형 추가로 인해 카테고리 정보로 비교)
         * 13:카테고리 상품유형이 여행일 경우에 등록하는 상품 유형이 일반이 아닐 경우에만 입력
         */
        PrdEtcVO prdEtcVO = productVO.getPrdEtcVO();
        //TODO 유정매니져님 확인 부탁드려용
        if(prdEtcVO == null) return;

        if ((ProductConstDef.PRD_TYP_CD_TOUR.equals(productVO.getCategoryVO().getDispCtgrPrdTypCd()) && !ProductConstDef.PRD_TYP_CD_NORMAL.equals(productVO.getBaseVO().getPrdTypCd()))
                || ProductConstDef.PRD_TYP_CD_DIGITAL.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_SOCIAL_NET.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_SOCIAL_TANGIBLE.equals(productVO.getBaseVO().getPrdTypCd())
                || ProductConstDef.PRD_TYP_CD_TICKET.equals(productVO.getBaseVO().getPrdTypCd())
                || (ProductConstDef.PRD_TYP_CD_SMS_CUPN.equals(productVO.getBaseVO().getPrdTypCd()) && !StringUtil.isEmpty(prdEtcVO.getIssuCoLogoUrl()))
                || (ProductConstDef.PRD_TYP_CD_SMS.equals(productVO.getBaseVO().getPrdTypCd()) && !StringUtil.isEmpty(prdEtcVO.getIssuCoLogoUrl()))
                ) {
            prdEtcVO.setPrdNo(productVO.getPrdNo());
            prdEtcVO.setUpdateNo(productVO.getUpdateNo());
            this.updateProductEtc(productVO);
        }
        /*
         * Yes24 도서 직매입 입점 제휴
         */
        try {
            if(prdEtcVO.getBookClfCd() !=null && !prdEtcVO.getBookClfCd().equals("")){
                productVO.getPrdEtcVO().setPrdNo(productVO.getPrdNo());
                productVO.getPrdEtcVO().setCreateNo(productVO.getCreateNo());
                productVO.getPrdEtcVO().setUpdateNo(productVO.getUpdateNo());
                this.updateProductEtcYes24(productVO);
            }
        } catch (Exception ex) {
            throw new ProductException("Yes24 도서 직매입 입점 제휴 수정 오류", ex);
        }

        /*
    	 * HMALL 동영상 재생 관련
         */
        if(productVO.isHmallVod()){
            this.updateProductEtc(productVO);
        }

    }

    /**
     * 상품 추가 정보 등록
     *
     * @param productVO
     * @return
     * @throws ProductException
     */
    public int insertProductEtc(ProductVO productVO) throws ProductException {

        PdPrdEtc pdPrdEtc = new PdPrdEtc();

        int rtnInt = 0;
        try {

            PrdEtcVO prdEtcVO = productVO.getPrdEtcVO();
            pdPrdEtc.setPrdNo(productVO.getPrdNo());
            pdPrdEtc.setCreateNo(productVO.getCreateNo());
            pdPrdEtc.setUpdateNo(productVO.getCreateNo());

            if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_BOOK.equals(StringUtil.nvl(prdEtcVO.getBookClfCd())) ||
                    ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_FOREIGN_BOOK.equals(StringUtil.nvl(prdEtcVO.getBookClfCd()))) {

                pdPrdEtc.setAuthorInfo(StringUtil.nvl(prdEtcVO.getAuthorInfo()));
                pdPrdEtc.setTransInfo(StringUtil.nvl(prdEtcVO.getTransInfo()));
                pdPrdEtc.setPicInfo(StringUtil.nvl(prdEtcVO.getPicInfo()));
                pdPrdEtc.setIsbn13Cd(StringUtil.nvl(prdEtcVO.getIsbn13Cd()));
                pdPrdEtc.setIsbn10Cd(StringUtil.nvl(prdEtcVO.getIsbn10Cd()));
                pdPrdEtc.setBookSize(StringUtil.nvl(prdEtcVO.getBookSize()));
                pdPrdEtc.setBookPage(StringUtil.nvl(prdEtcVO.getBookPage()));
                pdPrdEtc.setCompInfo(StringUtil.nvl(prdEtcVO.getCompInfo()));
                pdPrdEtc.setPreVwCd(StringUtil.nvl(prdEtcVO.getPreVwCd()));
                pdPrdEtc.setMainTitle(StringUtil.nvl(prdEtcVO.getMainTitle()));
                pdPrdEtc.setFreeGiftTgNo1(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo1()));
                pdPrdEtc.setFreeGiftTgNo2(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo2()));

            } else if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_MUSIC.equals(StringUtil.nvl(prdEtcVO.getBookClfCd()))) {

                pdPrdEtc.setMudvdLabel(StringUtil.nvl(prdEtcVO.getMudvdLabel()));
                pdPrdEtc.setFreeGiftTgNo1(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo1()));
                pdPrdEtc.setFreeGiftTgNo2(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo2()));

            } else if(ProductConstDef.DP_DISP_CTGR_BOOK_CLF_CD_DVD.equals(StringUtil.nvl(prdEtcVO.getBookClfCd()))) {

                pdPrdEtc.setFreeGiftTgNo1(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo1()));
                pdPrdEtc.setFreeGiftTgNo2(StringUtil.nvl(prdEtcVO.getFreeGiftTgNo2()));
            }

            if("Y".equals(StringUtil.nvl(productVO.getIsOpenApi()))) {
                pdPrdEtc.setIssuCoLogoUrl(StringUtil.nvl(prdEtcVO.getIssuCoLogoUrl()));
            }


            rtnInt = etcMapper.insertProductEtc(pdPrdEtc);

        } catch (ProductException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".insertProductEtc] MSG : " + ex.getMessage(), ex);
            }
            throw new ProductException("상품 추가 정보 등록 오류", ex);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".insertProductEtc] MSG : " + ex.getMessage(), ex);
            }
            throw new ProductException("상품 추가 정보 등록 오류", ex);
        }
        return rtnInt;
    }

    /**
     * 상품 추가 정보 수정
     *
     * @param productVO
     * @return
     * @throws ProductException
     */
    public int updateProductEtc(ProductVO productVO) throws ProductException {

        int rtnInt = 0;
        PrdEtcVO orgProductEtcVO = new PrdEtcVO();
        PdPrdEtc pdPrdEtc = new PdPrdEtc();
        try {

            BeanUtils.copyProperties(productVO.getPrdEtcVO(),pdPrdEtc);
            orgProductEtcVO = etcMapper.getProductEtc(productVO.getPrdNo());

            pdPrdEtc.setPrdNo(productVO.getPrdNo());
            pdPrdEtc.setCreateNo(productVO.getCreateNo());
            pdPrdEtc.setUpdateNo(productVO.getUpdateNo());

            if(productVO.isHmallVod()) {

                pdPrdEtc.setVodBrowserTyp(productVO.getPrdEtcVO().getVodBrowserTyp());
                pdPrdEtc.setVodUrl(productVO.getPrdEtcVO().getVodUrl());
            }

            //ETC 정보가 있으면 등록 없으면 수정
            if (orgProductEtcVO == null){
                rtnInt = this.insertProductEtc(productVO);
            } else {
                rtnInt = etcMapper.updateProductEtc(pdPrdEtc);
            }
        } catch (ProductException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProductEtc] MSG : " + ex.getMessage(), ex);
            }
            throw new ProductException("상품   추가  정보 수정 오류", ex);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProductEtc] MSG : " + ex.getMessage(), ex);
            }
            throw new ProductException("상품   추가  정보 수정 오류", ex);
        }
        return rtnInt;
    }


    /**
     * 상품 예약일정 여부 조회
     */
    public HashMap getPrdRsvSchdlYn(PdPrdOthers pdPrdOthers) throws ProductException {
        try {
            return etcMapper.getPrdRsvSchdlYn(pdPrdOthers);
        } catch (Exception ex) {
            throw new ProductException("상품 예약일정 여부 조회 오류", ex);
        }
    }

    /**
     * 상품 축산물이력 조회
     */
    public PrdOthersVO getBeefTraceInfo(long prdNo) throws ProductException {
        try {
            return etcMapper.getBeefTraceInfo(prdNo);
        } catch (Exception ex) {
            throw new ProductException("상품 축산물이력 조회 오류", ex);
        }
    }

    /**
     * 축산물이력번호등록
     *
     * @param prdOthersVO
     * @return
     * @throws ProductException
     */
    public void insertBeefTraceInfo(PrdOthersVO prdOthersVO) throws ProductException {
        PrdOthersVO curData = null;
        PrdOthersVO newData = prdOthersVO;
        PdPrdOthers pdPrdOthers =  new PdPrdOthers();

        try {
            // 축산물이력번호 정보 조회
            if (prdOthersVO.getPrdNo() > 0) {
                curData = etcMapper.getBeefTraceInfo(prdOthersVO.getPrdNo());
            }

            BeanUtils.copyProperties(newData ,pdPrdOthers);

            // 현재 등록되어 있는 축산물 이력번호 정보가 있으면 수정 아니면 등록
            if (curData != null) {
                etcMapper.updateBeefTraceInfo(pdPrdOthers);
            } else if (newData != null && StringUtil.isNotEmpty(newData.getBeefTraceNo())) {
                etcMapper.insertBeefTraceInfo(pdPrdOthers);
            }
        } catch (Exception ex) {
            log.error("[EtcServiceImpl.insertBeefTraceInfo] MSG:" + ex.getMessage(), ex);
            throw new ProductException("축산물이력번호등록", ex);
        }
    }

    /**
     * 구매불가일 등록
     *
     * @param prdOthersVO
     * @return
     * @throws ProductException
     */
    public void mergeProductOthersBuyDsblDyInfo(PrdOthersVO prdOthersVO) throws ProductException {
        try {

            PrdOthersVO newData = prdOthersVO;
            PrdOthersVO curData = new PrdOthersVO();
            PdPrdOthers pdPrdOthers = new PdPrdOthers();

            if (prdOthersVO.getPrdNo() > 0) {
                curData = etcMapper.getProdMedicalDeviceInfo(prdOthersVO.getPrdNo());
            }

            BeanUtils.copyProperties(newData ,pdPrdOthers);

            if (curData != null) {
                etcMapper.updateProductOthersBuyDsblDyInfo(pdPrdOthers);
            } else if (newData != null && StringUtil.isNotEmpty(newData.getBuyDsblDyCd())) {
                etcMapper.insertProductOthersBuyDsblDyInfo(pdPrdOthers);
            }
        } catch (ProductException te) {
            if (log.isErrorEnabled()) {
                log.error(te.getMessage(), te);
            }
            throw te;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            throw new ProductException(e);

        }
    }

    /**
     * 상품 추가 정보 수정 (YES 24 전용)
     *
     * @param productVO
     * @return
     * @throws ProductException
     */
    public int updateProductEtcYes24(ProductVO productVO) throws ProductException {

        int rtnInt = 0;
        PrdEtcVO orgProductEtcVO = new PrdEtcVO();
        PdPrdEtc pdPrdEtc = new PdPrdEtc();
        try {
            orgProductEtcVO = etcMapper.getProductEtc(productVO.getPrdNo());

            BeanUtils.copyProperties(productVO.getPrdEtcVO(),pdPrdEtc);
            //ETC 정보가 있으면 등록 없으면 수정
            if(orgProductEtcVO == null)
                etcMapper.insertProductEtc(pdPrdEtc);
            else
                etcMapper.updateProductEtcYes24(pdPrdEtc);
        } catch (ProductException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProductEtcYes24] MSG : "+ ex.getMessage(), ex);
            }
            throw new ProductException("상품 추가 정보 수정 (yES 24 전용) 오류", ex);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + this.getClass().getName() + ".updateProductEtcYes24] MSG : "+ ex.getMessage(), ex);
            }
            throw new ProductException("상품 추가 정보 수정 (yES 24 전용) 오류", ex);
        }
        return rtnInt;
    }

    public void mergeProductOthersPtnrInfo( ProductVO productVO) throws ProductException {
        try {

            PrdOthersVO newData = productVO.getPrdOthersVO();
            PrdOthersVO curData = null;

            PdPrdOthers pdPrdOthers = new PdPrdOthers();

            if(productVO.isUpdate()) {
                curData = etcMapper.getProdMedicalDeviceInfo(productVO.getPrdNo());
            }

            BeanUtils.copyProperties(newData , pdPrdOthers);

            if(newData != null) productVO.setPtnrPrdClfCd(ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);
            if(curData != null){
                etcMapper.updateProductOthersPtnrInfo(pdPrdOthers);
            }else if(newData != null && StringUtil.isNotEmpty(newData.getPtnrPrdClfCd())){
                etcMapper.insertProductOthersPtnrInfo(pdPrdOthers);
            }
        } catch(ProductException te) {
            if( log.isErrorEnabled() ) {
                log.error(te.getMessage(), te);
            }
            throw te;
        } catch(Exception e) {
            if( log.isErrorEnabled() ) {
                log.error(e.getMessage(), e);
            }
            throw new ProductException(e);
        }
    }

    /**
     * 의료기기 품목허가번호 등록 및 수정
     *
     * @param prdOthersVO
     * @return
     * @throws ProductException
     */
    public void insertProdMedicalDeviceInfo(PrdOthersVO prdOthersVO) throws ProductException {
        PrdOthersVO newData = prdOthersVO;
        PrdOthersVO curData = null;
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        // 상품  의료기기 품목허가번호 정보 조회
        if(newData.getPrdNo() > 0) {
            curData = etcMapper.getProdMedicalDeviceInfo(newData.getPrdNo());
        }

        BeanUtils.copyProperties(newData,pdPrdOthers);

        // 현재 등록되어 있는 의료기기 정보가 있으면 수정 아니면 등록
        /*
        healthItmAuthNum = getMedNum1
        healthSaleCoNum =  getMedNum2
        preAdvChkNum = getMedNum3
         */
        if(curData != null){
            if(!prdOthersVO.isQCVerifiedStat()){
                String preMedNum1 = StringUtil.nvl(curData.getHealthItmAuthNum());
                String medNum1 = prdOthersVO == null ? "" : StringUtil.nvl(prdOthersVO.getHealthItmAuthNum());

                prdOthersVO.setQCVerifiedStat(!preMedNum1.equals(medNum1));//Update 안하는데 왜 세팅하는가..?
            }
            etcMapper.updateProdMedicalDeviceInfo(pdPrdOthers);
        }else if((newData != null && (StringUtil.isNotEmpty(newData.getHealthItmAuthNum()) || StringUtil.isNotEmpty(newData.getHealthSaleCoNum()) || StringUtil.isNotEmpty(newData.getPreAdvChkNum()) || StringUtil.isNotEmpty(newData.getSelPrdClfFpCd())))
                || prdOthersVO.getTemplatePrdNo() > 0){
            if(!prdOthersVO.isQCVerifiedStat()) prdOthersVO.setQCVerifiedStat(true); //Insert도 안하는데 왜 세팅하는가..?
            etcMapper.insertProdMedicalDeviceInfo(pdPrdOthers);
        }
    }

    /* 생활플러스 정보 등록 */
    public void setProductOthersForLife(ProductVO productVO, long productNo) throws ProductException{

        PdPrdOthers pdPrdOthers = new PdPrdOthers();
        PrdOthersVO prdOthersVO = productVO.getPrdOthersVO();

        prdOthersVO.setCreateNo(productVO.getSelMnbdNo());

        SellerValidate sellerValidate = new SellerValidate();

        pdPrdOthers.setPrdNo(productNo);
        pdPrdOthers.setPrdRiousQty(prdOthersVO.getSelPrdRiousQty());
        pdPrdOthers.setAddQueryCont(StringUtil.nvl(prdOthersVO.getAddQueryCont()));
        pdPrdOthers.setAddQueryHintCont(StringUtil.nvl(prdOthersVO.getAddQueryHintCont()));
        pdPrdOthers.setSvcCanRgnClfCd(StringUtil.nvl(prdOthersVO.getSvcCanRgnClfCd()));
        pdPrdOthers.setDlvProcCanDd(prdOthersVO.getDlvProcCanDd());
        pdPrdOthers.setRsvSchdlYn(StringUtil.nvl(prdOthersVO.getRsvSchdlYn()));
        pdPrdOthers.setRsvSchdlClfCd(StringUtil.nvl(prdOthersVO.getRsvSchdlClfCd()));
        pdPrdOthers.setCreateNo(productVO.getSelMnbdNo());

        if(!"".equals(productVO.getPtnrPrdClfCd()) && sellerValidate.isCertStockCdSeller(productVO.getSelMnbdNo())) {
            prdOthersVO.setPtnrPrdClfCd(productVO.getPtnrPrdClfCd());
        }
        etcMapper.insertProductOthersForLife(pdPrdOthers);
    }

    /* 지정일 상품코드 정보 등록 */
    public void mergeProductOthersForPtnrPrdClf(ProductVO productVO, long productNo) throws ProductException {

        ProductStockVO setProductStockVO = new ProductStockVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();

        // 지정일상품코드가 들어왔는지 여부 확인
        String prdSellerStockCd = productVO.getOptionVO().getPrdSellerStockCd();
        setProductStockVO.setPrdNo(productVO.getPrdNo());

        List<ProductStockVO> productStockList = optionService.getPdStockPtnrInfoByPrdNo(setProductStockVO);
        pdPrdOthers.setPrdNo(productNo);
        pdPrdOthers.setPtnrPrdClfCd("");
        if(StringUtil.isNotEmpty(prdSellerStockCd) || (productStockList != null && productStockList.size() > 0)) {
            pdPrdOthers.setPtnrPrdClfCd(ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV);
        }
        pdPrdOthers.setCreateNo(productVO.getUpdateNo());
        etcMapper.insertProductOthersForLife(pdPrdOthers);
    }

    /* 지정일 상품코드 정보 등록 */
    public void updateProductOthersForPtnrPrdClf(ProductVO productVO, long productNo) throws ProductException{
        OptionVO optionVO = productVO.getOptionVO();
        ProductStockVO setProductStockVO = new ProductStockVO();
        PdPrdOthers pdPrdOthers = new PdPrdOthers();
        PrdOthersVO prdOthersVO = productVO.getPrdOthersVO();

        // 지정일상품코드가 들어왔는지 여부 확인
        String prdSellerStockCd = optionVO.getPrdSellerStockCd();
        setProductStockVO.setPrdNo(productVO.getPrdNo());
        List<ProductStockVO> productStockList = optionService.getPdStockPtnrInfoByPrdNo(setProductStockVO);

        String ptnrPrdClfCd = "";

        setProductStockVO = new ProductStockVO();
        setProductStockVO.setOptItemNo1(0);
        setProductStockVO.setPrdNo(productVO.getPrdNo());

        int retVal = optionService.getSingleOptionCnt(setProductStockVO);
        // 옵션이 없을경우 상품의 지정일상품코드 정보가 있을 경우
        if((StringUtil.isNotEmpty(prdSellerStockCd) && retVal > 0) || (productStockList != null && productStockList.size() > 0)) {
            ptnrPrdClfCd = ProductConstDef.PTNR_PRD_CLF_CD_SPEC_DLV;
        }
        prdOthersVO.setPrdNo(productNo);
        prdOthersVO.setUpdateNo(productVO.getUpdateNo());
        prdOthersVO.setPtnrPrdClfCd(ptnrPrdClfCd);

        productVO.setPrdOthersVO(prdOthersVO);

        BeanUtils.copyProperties(productVO.getPrdOthersVO() ,pdPrdOthers );
        etcMapper.updateProductOthersPtnrInfo(pdPrdOthers);
    }


    public void setEtcInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        etcValidate.setEtcProductInfo(productVO);
    }

    public void mergeEtcInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        if(productVO.isUpdate()){
            this.checkEtcDataUpdateProcess(productVO);
        }else{
            this.checkEtcDataInsertProcess(productVO);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.convertOthersInfo(preProductVO, productVO);
    }

    private void convertOthersInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.convertMedicalInfo(productVO);
    }

    private void convertMedicalInfo(ProductVO productVO) throws ProductException{
        // 대카테고리별 의료기기 품목허가번호 가능 여부 체크
        if(!"".equals(productVO.getPrdOthersVO().getHealthItmAuthNum())
            || !"".equals(productVO.getPrdOthersVO().getHealthSaleCoNum())
            || !"".equals(productVO.getPrdOthersVO().getPreAdvChkNum())
        ){
            if(!categoryService.isProductMedicalPermissionYn(productVO.getCategoryVO().getLctgrNo())){
                throw new ProductException("의료기기 품목허가번호 등록에 유효하지 않은 대카테고리 입니다.");
            }
        }
    }
}
