package com.elevenst.terroir.product.registration.delivery.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.category.mapper.CategoryMapper;
import com.elevenst.terroir.product.registration.category.service.CategoryServiceImpl;
import com.elevenst.terroir.product.registration.category.vo.CategoryVO;
import com.elevenst.terroir.product.registration.common.code.MbAddrLocation;
import com.elevenst.terroir.product.registration.delivery.code.DlvClfCdTypes;
import com.elevenst.terroir.product.registration.delivery.code.PrdAddrClfCd;
import com.elevenst.terroir.product.registration.delivery.entity.PdPrdTgowPlcExchRtngd;
import com.elevenst.terroir.product.registration.delivery.entity.PdReglDlvPrd;
import com.elevenst.terroir.product.registration.delivery.entity.PdReglDlvPrdHist;
import com.elevenst.terroir.product.registration.delivery.entity.TrGlobalPrdAvgDeli;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryException;
import com.elevenst.terroir.product.registration.delivery.exception.DeliveryServiceException;
import com.elevenst.terroir.product.registration.delivery.mapper.DeliveryMapper;
import com.elevenst.terroir.product.registration.delivery.message.DeliveryServiceExceptionMessage;
import com.elevenst.terroir.product.registration.delivery.validate.DeliveryValidate;
import com.elevenst.terroir.product.registration.delivery.vo.*;
import com.elevenst.terroir.product.registration.option.validate.OptionValidate;
import com.elevenst.terroir.product.registration.product.code.DlvCstInstBasiCd;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.validate.ProductValidate;
import com.elevenst.terroir.product.registration.option.code.OptTypCdTypes;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.wms.service.WmsServiceImpl;
import com.elevenst.terroir.product.registration.wms.vo.WmChrgVO;
import com.elevenst.terroir.product.registration.wms.vo.WmCustomsResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class DeliveryServiceImpl implements RegistrationInfoConverter<ProductVO>{

    @Autowired
    DeliveryMapper deliveryMapper;

    @Autowired
    DeliveryValidate deliveryValidate;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    WmsServiceImpl wmsServiceImpl;

    @Autowired
    ProductValidate productValidate;

    @Autowired
    OptionValidate optionValidate;

    public boolean isExistUserAddressSeq(ClaimAddressInfoVO claimAddressInfoVO) {
        long resultCount = deliveryMapper.isExistUserAddressSeq(claimAddressInfoVO);
        return resultCount > 0 ? true : false;
    }

    public List<ProductSellerBasiDeliveryCostVO> getSellerBasiDlvCstList(long memNo) {
        return deliveryMapper.getSellerBasiDlvCstList(memNo);
    }


    /**
     * productRegVO에 전세계배송 기존정보 있을 시 값 세팅 그렇지 않을 경우 초기화
     * */
    public ProductVO setPrdRegVOIfHaveGlobalDeliveryInfo(ProductVO productRegVO, ProductVO existedProductVO) {

        if(productRegVO != null) { //기존상품정보 존재 시
            productRegVO.getBaseVO().setGblHsCode(existedProductVO.getBaseVO().getGblHsCode());
            productRegVO.getBaseVO().setNtNo(existedProductVO.getBaseVO().getNtNo());

            //11번가 해외배송일 경우에는 제외
            if(GroupCodeUtil.notEqualsDtlsCd(productRegVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {
                productRegVO.getBaseVO().setPrdWght(existedProductVO.getBaseVO().getPrdWght());
            }
            //전세계배송 HsCode 값 넣기
            if(!StringUtil.isEmpty(productRegVO.getBaseVO().getGblHsCode())) {
                WmGlobalHsCodeVO paramVO = new WmGlobalHsCodeVO();
                paramVO.setGblHsCode(productRegVO.getBaseVO().getGblHsCode());
                WmGlobalHsCodeVO wmGlobalHsCodeVO = deliveryMapper.getWmGblHsCode(paramVO);

                productRegVO.getBaseVO().setGblHsCodeNo(wmGlobalHsCodeVO.getGblHsCodeNo());
            }
            /*
            else {
                //입력값으로 들어온 HsCode값 없을 경우 카테고에 기본 등록되어있는 HsCode 삽입
                CategoryVO ctgrVO = categoryService.getServiceDisplayCategoryInfo(productRegVO.getDispCtgrNo());
                productRegVO.getBaseVO().setGblHsCodeNo(ctgrVO.getGblHsCodeNo());
            }
            */
        }
        else {
            productRegVO.getBaseVO().setGblHsCode("");
            productRegVO.getBaseVO().setNtNo(0);
            //11번가 해외배송일 경우에는 제외
            if(GroupCodeUtil.notEqualsDtlsCd(productRegVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {
                productRegVO.getBaseVO().setPrdWght(0);
            }
        }
        return productRegVO;
    }

    public TrGlobalProductAverageDeliveryVO getProductAvgDeliInfo(long prdNo){
        return deliveryMapper.getProductAvgDeliInfo(prdNo);
    }

    public void insertProductAvgDeli(TrGlobalPrdAvgDeli trGlobalPrdAvgDeli) {
        deliveryMapper.insertTrGlobalPrdAvgDeli(trGlobalPrdAvgDeli);
        deliveryMapper.insertTrGlobalPrdAvgDeliHist(trGlobalPrdAvgDeli);
        return;
    }

    public void updateProductAvgDeli(TrGlobalPrdAvgDeli trGlobalPrdAvgDeli) {
        deliveryMapper.updateTrGlobalPrdAvgDeli(trGlobalPrdAvgDeli);
        deliveryMapper.insertTrGlobalPrdAvgDeliHist(trGlobalPrdAvgDeli);
        return;
    }

    public void deletePdPrdTgowPlcExchRtngdUsingClaimAddressVO(ClaimAddressInfoVO claimAddressInfoVO) {
        PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd = new PdPrdTgowPlcExchRtngd();
        pdPrdTgowPlcExchRtngd.setPrdNo(claimAddressInfoVO.getPrdNo());
        pdPrdTgowPlcExchRtngd.setPrdAddrClfCd(claimAddressInfoVO.getPrdAddrClfCd());
        deliveryMapper.deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd(pdPrdTgowPlcExchRtngd);
        //deliveryMapper.deletePdPrdTgowPlcExchRtngdUsingClaimAddressVO(claimAddressInfoVO);
    }

    public void insertPdPrdTgowPlcExchRtngd(PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd) {
        deliveryMapper.insertPdPrdTgowPlcExchRtngd(pdPrdTgowPlcExchRtngd);
    }

    public void updateProductReglDeliveryInfo(ProductVO productVO) {
        if(productVO == null || productVO.getPrdNo() <= 0 ) {
            return;
        }

        CategoryVO categoryVO = categoryService.getServiceDisplayCategoryInfo(productVO.getDispCtgrNo());
        boolean isRegularDeliveryYn = false;
        if(StringUtil.equals("Y",categoryVO.getReglDlvYn())) {
            isRegularDeliveryYn = true;
        }

        if(isRegularDeliveryYn &&
            GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getPrdTypCd(), PrdTypCd.NORMAL, PrdTypCd.MART, PrdTypCd.SOHO) &&
            GroupCodeUtil.isContainsDtlsCd(productVO.getBaseVO().getOptTypCd(), OptTypCdTypes.DATE, OptTypCdTypes.CALC)) {
            return;
        }

        PrdReglDlvVO prdReglDlvVO = deliveryMapper.selectPdReglDlvPrdUseY(productVO.getPrdNo());

        if(prdReglDlvVO != null) {
            ProductDeliveryVO paramVO = new ProductDeliveryVO();
            paramVO.setPrdNo(productVO.getPrdNo());
            paramVO.setUseYn("N");
            paramVO.setUpdateNo(productVO.getUpdateNo());
            updateProductReglUseYn(paramVO);
            insertProductReglDeliveryInfoHist(paramVO);
        }

    }


    public void updateProductReglUseYn(ProductDeliveryVO paramBO) throws DeliveryException {
        try {
            PdReglDlvPrd pdReglDlvPrd = new PdReglDlvPrd();
            pdReglDlvPrd.setPrdNo(paramBO.getPrdNo());
            pdReglDlvPrd.setUseYn(paramBO.getUseYn());
            pdReglDlvPrd.setUpdateNo(paramBO.getUpdateNo());
            deliveryMapper.updateProductReglUseYn(pdReglDlvPrd);
        } catch(Exception ex) {
            throw new DeliveryException(ex);
        }
    }

    public void insertProductReglDeliveryInfoHist(ProductDeliveryVO paramBO) throws DeliveryException {
        try{
            PdReglDlvPrdHist pdReglDlvPrdHist = new PdReglDlvPrdHist();
            pdReglDlvPrdHist.setPrdNo(paramBO.getPrdNo());
            deliveryMapper.insertProductReglDeliveryInfoHist(pdReglDlvPrdHist);
        } catch(Exception ex) {
            throw new DeliveryException(ex);
        }
    }

    /**
     *  상품수정시
     *  배송유형에 따른 출고반품지 주소 삭제
     */
    public void deleteClaimAddressInfoViaDeliveryType(ProductVO productVO) {

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {

            PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd = new PdPrdTgowPlcExchRtngd();
            pdPrdTgowPlcExchRtngd.setPrdNo(productVO.getPrdNo());
            pdPrdTgowPlcExchRtngd.setPrdAddrClfCd(PrdAddrClfCd.SELLER_GLOBAL_EX_WAREHOUSE.getDtlsCd());
            deliveryMapper.deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd(pdPrdTgowPlcExchRtngd);
            pdPrdTgowPlcExchRtngd.setPrdAddrClfCd(PrdAddrClfCd.SELLER_EXCHANGE_RETURN.getDtlsCd());
            deliveryMapper.deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd(pdPrdTgowPlcExchRtngd);
        }
        else {
            PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd = new PdPrdTgowPlcExchRtngd();
            pdPrdTgowPlcExchRtngd.setPrdNo(productVO.getPrdNo());
            deliveryMapper.deletePdPrdTgowPlcExchRtngdUsingPrdAddrClfCd(pdPrdTgowPlcExchRtngd);
        }

    }

    /**
     *  상품수정시
     *  ProductVO 내의 ClaimAddressInfoVO list 데이터를 PD_PRD_TGOW_PLC_EXCH_RTNGD 테이블에 순차적 insert
     * */
    public void insertPdPrdTgowPlcExchRtngdViaClaimAddressInfoVO(ProductVO productVO) {

        List<ClaimAddressInfoVO> claimAddressInfoVOList = productVO.getClaimAddressInfoVOList();

        for(ClaimAddressInfoVO elem : claimAddressInfoVOList) {
            PdPrdTgowPlcExchRtngd pdPrdTgowPlcExchRtngd = new PdPrdTgowPlcExchRtngd();
            BeanUtils.copyProperties(elem, pdPrdTgowPlcExchRtngd);
            pdPrdTgowPlcExchRtngd.setPrdNo(productVO.getPrdNo());
            pdPrdTgowPlcExchRtngd.setCreateNo(productVO.getCreateNo());
            pdPrdTgowPlcExchRtngd.setUpdateNo(productVO.getUpdateNo());

            // TODO : bulk insert 필요. 반복호출로 DB에 부담이 갈 수도 있음.
            deliveryMapper.insertPdPrdTgowPlcExchRtngd(pdPrdTgowPlcExchRtngd);
        }
    }

    /**
     * 상품수정시
     * 해외배송일 경우 배송보장일 등록및 수정.
     */
    public void updateAverageDeliveryInfoIfDataExist(ProductVO productVO) {

        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {

            TrGlobalProductAverageDeliveryVO trGlobalProductAverageDeliveryVO = deliveryMapper.getProductAvgDeliInfo(productVO.getPrdNo());
            TrGlobalPrdAvgDeli trGlobalPrdAvgDeli = new TrGlobalPrdAvgDeli();
            BeanUtils.copyProperties(trGlobalProductAverageDeliveryVO,trGlobalPrdAvgDeli);
            if(trGlobalProductAverageDeliveryVO == null) { //기존 배송보장일 데이터 미존재
                trGlobalPrdAvgDeli = this.setTrGlobalPrdAvgDeli(productVO);
                insertProductAvgDeli(trGlobalPrdAvgDeli);
            }
            else if( trGlobalProductAverageDeliveryVO.getAvgDeliDays() != productVO.getBaseVO().getAvgDlvDy()){ //기존 배송보장일 데이터 존재
                updateProductAvgDeli(trGlobalPrdAvgDeli);
            }
        }
    }

    private TrGlobalPrdAvgDeli setTrGlobalPrdAvgDeli(ProductVO productVO) throws DeliveryException{
        TrGlobalPrdAvgDeli trGlobalPrdAvgDeli = new TrGlobalPrdAvgDeli();
        trGlobalPrdAvgDeli.setPrdNo(productVO.getPrdNo());
        trGlobalPrdAvgDeli.setAvgDeliDays(productVO.getBaseVO().getAvgDeliDays());

        return trGlobalPrdAvgDeli;
    }

    /**
     * 상품등록시
     * 해외배송일 경우 배송보장일 등록
     * */
    public void insertAverageDeliveryInfoIfDataExist(ProductVO productVO) {
        if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getDlvClf(), DlvClfCdTypes.ELEVENST_ABROAD_DELIVERY)) {
            TrGlobalPrdAvgDeli trGlobalPrdAvgDeli = this.setTrGlobalPrdAvgDeli(productVO);
            insertProductAvgDeli(trGlobalPrdAvgDeli);
        }
    }

    /**
     * 제주/도서산간 배송비 가져오기.
     *
     * @param selMnbdNo 판매자번호.
     * @return
     * @throws DeliveryException
     */
    public PdSellerIslandDlvCstVO getSellerIslandDlvCst(long selMnbdNo) throws DeliveryException {
        try {
            return deliveryMapper.getSellerIslandDlvCst(selMnbdNo);
        } catch (Exception ex) {
            throw new DeliveryException(ex);
        }
    }

    /**
     * 상품의 해외 통합출고지의 배송비를 구하기 위해 필요한 정보 조회
     *
     * @param prdNo(상품번호)
     * @return long
     * @throws DeliveryException
     */
    public HashMap getGlobalItgInfoForPrdDeliveryCost(long prdNo) throws DeliveryException {
        try {
            return deliveryMapper.getGlobalItgInfoForPrdDeliveryCost(prdNo);
        } catch (Exception ex) {
            throw new DeliveryException("상품의 해외 통합출고지의 배송비를 구하기 위해 필요한 정보 조회 오류", ex);
        }
    }

    /**
     * 해외 통합 출고지에 따른 무게별 상품 배송비조회(관세/부가세 포함)
     *
     * @param itgNo(통합아이디번호)
     * @param finalDscPrc(할인모음가)
     * @param weight(무게g)
     * @param HSCode(관세류코드)
     * @return long
     * @throws DeliveryException
     */
    public long getGlobalItgDeliveryCost(long itgNo, long finalDscPrc, long weight, String HSCode) throws DeliveryException {
        try {
            String curDate = DateUtil.getFormatString("yyyyMMdd");
            double weightKg = ((double) (weight / 10)) / 100;   // kg 으로 변경 소수점 두자리까지

            long dlvCst = wmsServiceImpl.getSellerWghtDlvCst(itgNo, weightKg, curDate); //통합배송셀러, 무게(Kg), 날짜 - 배송비
            long insrCst = wmsServiceImpl.getSellerInsrCst(itgNo, finalDscPrc, curDate); //셀러, 상품가, 날짜  - 보험료
            long totalCstVAT = 0; // 관부가세
            long HCValue = 0; // 핸들링촤지금액
            long cscValue = 0; // 통관서비스 수수료

            WmCustomsResultVO wmCustomsResultBO = new WmCustomsResultVO();
            //[관세 계산용 파라미터 셋팅]
            wmCustomsResultBO.setHsCode(HSCode); //HSCode 관세류
            wmCustomsResultBO.setApplyDt(curDate); //적용일(주문일)
            wmCustomsResultBO.setPrdDscPrc(finalDscPrc); //할인모음가
            wmCustomsResultBO.setDlvCst(dlvCst); //배송비
            wmCustomsResultBO.setInsrCst(insrCst); //보험료

            wmCustomsResultBO = wmsServiceImpl.getWmCustoms(wmCustomsResultBO);

            WmChrgVO wmChrgBO = wmsServiceImpl.getSellerCharge(itgNo, curDate); //셀러, 날짜
            long cscSt = wmChrgBO.getCscSt(); //통관수수료 기준금액
            long cstSt = wmChrgBO.getCstSt(); //관부과세 기준 금액

            long taxableValue = 0;
            String cscAplYn = "";
            if (wmCustomsResultBO != null) {
                wmCustomsResultBO.getTaxableValue(); // 과세가격
                cscAplYn = wmCustomsResultBO.getCscAplYn(); // 통관수수료여부
            }

            // 관부가세와 핸들링차지 금액
            if (wmCustomsResultBO != null && taxableValue >= cstSt) {
                totalCstVAT = wmCustomsResultBO.getTotalCstVAT(); // 관부가세
                HCValue = (((long) (totalCstVAT * wmChrgBO.getHcr() * 0.001)) * 10); // 핸들링촤지금액
            }

            // 통관서비스 수수료
            if ("Y".equals(cscAplYn) || taxableValue >= cscSt) {
                cscValue = (long) wmChrgBO.getCsc(); // 통관서비스수수료
            }
            return dlvCst + insrCst + totalCstVAT + cscValue + HCValue;
        } catch (Exception ex) {
            throw new DeliveryException("해외 통합 출고지에 따른 무게별 상품 배송비조회(관세/부가세 포함) 오류", ex);
        }
    }

    public void setDeliveryInfoProcess(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{
        deliveryValidate.checkClaimAddressVO(productVO);
    }

    public void convertBaseAddrInfo(ProductVO productVO) throws DeliveryException{

        if(productVO.getClaimAddressInfoVOList() == null){

            throw new DeliveryException("출고지 / 교환지 주소지가 올바르지 않습니다.");
//            List<ClaimAddressInfoVO> claimAddressInfoVOList = new ArrayList<ClaimAddressInfoVO>();
//            ClaimAddressInfoVO claimAddressInfoVO;
//
//
//            // 출고지
//            if (productVO.getBaseVO().getOutAddrSeq() > 0) {
//                claimAddressInfoVO = new ClaimAddressInfoVO();
//                claimAddressInfoVO.setAddrSeq(productVO.getBaseVO().getOutAddrSeq());
//                claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_OUT);
//                claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
//                claimAddressInfoVO.setCreateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setUpdateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setMbAddrLocation(productVO.getBaseVO().getOutMbAddrLocation());
//                claimAddressInfoVO.setFrCtrCd(productVO.getBaseVO().getFrCtrCd());
//
//                claimAddressInfoVOList.add(claimAddressInfoVO);
//            }
//
//            // 교환/반품지
//            if (productVO.getBaseVO().getInAddrSeq() > 0) {
//                claimAddressInfoVO = new ClaimAddressInfoVO();
//                claimAddressInfoVO.setAddrSeq(productVO.getBaseVO().getInAddrSeq());
//                claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_IN);
//                claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
//                claimAddressInfoVO.setCreateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setUpdateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setMbAddrLocation(productVO.getBaseVO().getInMbAddrLocation());
//
//                claimAddressInfoVOList.add(claimAddressInfoVO);
//            }
//
//            // 방문수령지
//            if ("Y".equals(productVO.getBaseVO().getVisitDlvYn()) && productVO.getBaseVO().getOutAddrSeq() > 0) {
//                claimAddressInfoVO = new ClaimAddressInfoVO();
//                claimAddressInfoVO.setAddrSeq(productVO.getBaseVO().getOutAddrSeq());
//                claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_VISIT);
//                claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
//                claimAddressInfoVO.setCreateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setUpdateNo(productVO.getUpdateNo());
//                claimAddressInfoVO.setMbAddrLocation(productVO.getBaseVO().getOutMbAddrLocation());
//
//                claimAddressInfoVOList.add(claimAddressInfoVO);
//            }
//
//            // 11번가 해외배송일 경우
//            if (ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())) {
//                // 판매자 해외출고지
//                if (productVO.getBaseVO().getGlobalOutAddrSeq() > 0) {
//                    claimAddressInfoVO = new ClaimAddressInfoVO();
//                    claimAddressInfoVO.setAddrSeq(productVO.getBaseVO().getGlobalOutAddrSeq());
//                    claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_GLB_ADDR_CLF_CD_OUT);
//                    claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
//                    claimAddressInfoVO.setCreateNo(productVO.getUpdateNo());
//                    claimAddressInfoVO.setUpdateNo(productVO.getUpdateNo());
//                    claimAddressInfoVO.setMbAddrLocation("02");
//
//                    claimAddressInfoVOList.add(claimAddressInfoVO);
//                }
//
//                // 판매자 해외 반품/교환지
//                if (productVO.getBaseVO().getGlobalInAddrSeq() > 0) {
//                    claimAddressInfoVO = new ClaimAddressInfoVO();
//                    claimAddressInfoVO.setAddrSeq(productVO.getBaseVO().getGlobalInAddrSeq());
//                    claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_GLB_ADDR_CLF_CD_IN);
//                    claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
//                    claimAddressInfoVO.setCreateNo(productVO.getUpdateNo());
//                    claimAddressInfoVO.setUpdateNo(productVO.getUpdateNo());
//                    claimAddressInfoVO.setMbAddrLocation("02");
//
//                    claimAddressInfoVOList.add(claimAddressInfoVO);
//                }
//            }
//
//
//            productVO.setClaimAddressInfoVOList(claimAddressInfoVOList);
        }else{
            for(ClaimAddressInfoVO claimAddressInfoVO : productVO.getClaimAddressInfoVOList()){
                if(ProductConstDef.PRD_ADDR_CLF_CD_OUT.equals(claimAddressInfoVO.getPrdAddrClfCd())){
                    productVO.getBaseVO().setOutAddrSeq(claimAddressInfoVO.getAddrSeq());
                    productVO.getBaseVO().setOutMbAddrLocation(claimAddressInfoVO.getMbAddrLocation());
                    productVO.getBaseVO().setOutMemNo(productVO.getSelMnbdNo());
                }else if(ProductConstDef.PRD_ADDR_CLF_CD_IN.equals(claimAddressInfoVO.getPrdAddrClfCd())){
                    productVO.getBaseVO().setInAddrSeq(claimAddressInfoVO.getAddrSeq());
                    productVO.getBaseVO().setInMbAddrLocation(claimAddressInfoVO.getMbAddrLocation());
                    productVO.getBaseVO().setInMemNo(productVO.getSelMnbdNo());
                }else if(ProductConstDef.PRD_ADDR_CLF_CD_VISIT.equals(claimAddressInfoVO.getPrdAddrClfCd())){
                    productVO.getBaseVO().setVisitAddrSeq(claimAddressInfoVO.getAddrSeq());
                    productVO.getBaseVO().setVisitMbAddrLocation(claimAddressInfoVO.getMbAddrLocation());
                }else if(ProductConstDef.PRD_GLB_ADDR_CLF_CD_OUT.equals(claimAddressInfoVO.getPrdAddrClfCd())){

                }else if(ProductConstDef.PRD_GLB_ADDR_CLF_CD_IN.equals(claimAddressInfoVO.getPrdAddrClfCd())){

                }
            }
        }
    }


    @Deprecated
    public void getBaseAddrInfoOld(ProductVO productVO) throws DeliveryException{
        //TODO CYB 방법을 좀 더 고민해볼껏(잘 사용할 방법이 있을뜻.)

        List<ClaimAddressInfoVO> claimAddressInfoVOList = new ArrayList<ClaimAddressInfoVO>();
        ClaimAddressInfoVO claimAddressInfoVO = new ClaimAddressInfoVO();
        claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
        claimAddressInfoVO.setAddrCd(ProductConstDef.MEM_PRD_ADDR_CD_OUT);
        claimAddressInfoVO = deliveryMapper.getBaseAddrInfo(claimAddressInfoVO);
        claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_OUT);

        claimAddressInfoVOList.add(claimAddressInfoVO);

        claimAddressInfoVO = new ClaimAddressInfoVO();
        claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
        claimAddressInfoVO.setAddrCd(ProductConstDef.MEM_PRD_ADDR_CD_IN);
        claimAddressInfoVO = deliveryMapper.getBaseAddrInfo(claimAddressInfoVO);
        claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_IN);

        claimAddressInfoVOList.add(claimAddressInfoVO);

        if("Y".equals(productVO.getBaseVO().getVisitDlvYn())){

            claimAddressInfoVO = new ClaimAddressInfoVO();
            claimAddressInfoVO.setMemNo(productVO.getSelMnbdNo());
            claimAddressInfoVO.setAddrCd(ProductConstDef.MEM_PRD_ADDR_CD_IN);
            claimAddressInfoVO = deliveryMapper.getBaseAddrInfo(claimAddressInfoVO);
            claimAddressInfoVO.setPrdAddrClfCd(ProductConstDef.PRD_ADDR_CLF_CD_IN);

            claimAddressInfoVOList.add(claimAddressInfoVO);
        }

        productVO.setClaimAddressInfoVOList(claimAddressInfoVOList);

    }

    public void insertDeliveryInfoProcess(ProductVO productVO) throws DeliveryException{
        this.insertPdPrdTgowPlcExchRtngdViaClaimAddressInfoVO(productVO);
        this.insertAverageDeliveryInfoIfDataExist(productVO);
    }

    public void updateDeliveryInfoProcess(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{
        this.deleteClaimAddressInfoViaDeliveryType(productVO);
        this.insertPdPrdTgowPlcExchRtngdViaClaimAddressInfoVO(productVO);
        this.updateAverageDeliveryInfoIfDataExist(productVO);
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertBaseAddrInfo(productVO);
        this.convertDeliveryInfo(preProductVO, productVO);
        this.checkDeliveryInfo(preProductVO, productVO);
    }

    private void checkDeliveryInfo(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{
        deliveryValidate.checkDeliveryInfo(preProductVO, productVO);
    }


    private void convertDeliveryInfo(ProductVO preProductVO, ProductVO productVO) throws DeliveryException{


        DlvCstInstBasiCd dlvCstInstBasiCd = GroupCodeUtil.fromString(DlvCstInstBasiCd.class, productVO.getBaseVO().getDlvCstInstBasiCd());
        String dlvCstText = "";
        if(dlvCstInstBasiCd == null) throw new DeliveryException("배송비 설정을 선택해 주십시오.");
        switch (dlvCstInstBasiCd) {
            case FREE:  //무료 01
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setDlvCstPayTypCd(ProductConstDef.DLV_CST_PAY_TYP_CD_PRE_PAY);
                break;
            case FIXED_DELIVERY:    //고정배송비/착불 02
                productVO.getBaseVO().setDlvCst(productVO.getBaseVO().getDlvCst());//getDlvCst1
                break;
            case PRODUCT_CONDITIONAL_FREE:  //상품조건부무료 03
                productVO.getBaseVO().setDlvCst(productVO.getBaseVO().getDlvCst());//getDlvCst5
                productVO.getBaseVO().setPrdFrDlvBasiAmt(productVO.getBaseVO().getPrdFrDlvBasiAmt());
                productVO.getBaseVO().setBndlDlvCnYn("N");
                break;
            case QUANTITY_GRADE:    //수량별 차등 배송비 04
                StringBuilder stbDlvCustInstBasiNm = new StringBuilder();
                if(productVO.getPrdOrderCountBaseDeliveryVOList() == null || productVO.getPrdOrderCountBaseDeliveryVOList().size() == 0){
                    throw new DeliveryException("수량별 차등 배송비 선택시 배송비와 수량을 입력하셔야 합니다.");
                }

                for(int i=0,size=productVO.getPrdOrderCountBaseDeliveryVOList().size();i<size;i++){
                    PrdOrderCountBaseDeliveryVO deliveryVO = productVO.getPrdOrderCountBaseDeliveryVOList().get(i);
                    stbDlvCustInstBasiNm.append(deliveryVO.getDlvCst()).append("원 (");
                    stbDlvCustInstBasiNm.append(deliveryVO.getOrdBgnQty()).append("개 ~");
                    if(i == (size-1)){
                        stbDlvCustInstBasiNm.append(deliveryVO.getOrdEndQty()).append("이상)");
                    }else{
                        stbDlvCustInstBasiNm.append(deliveryVO.getOrdEndQty()).append("개)");
                        stbDlvCustInstBasiNm.append("/");
                    }
                }
                productVO.getBaseVO().setDlvCstInstBasiNm(stbDlvCustInstBasiNm.toString());
                //TODO CYB 다시 할것
//                String[] dlvCst3 = prdRegBO.getArrDlvCst3();
//                String[] dlvCnt1 = prdRegBO.getArrDlvCnt1();
//                String[] dlvCnt2 = prdRegBO.getArrDlvCnt2();

//                List<OrderQunatityBasisDeliveryCostBO> ordQtyBasiDlvCstBOList = new ArrayList<OrderQunatityBasisDeliveryCostBO>();
//                StringBuilder stbDlvCustInstBasiNm = new StringBuilder();
//                if (dlvCst3 != null && dlvCst3.length > 0) {
//                    for (int i = 0; i < dlvCst3.length; i ++) {
//                        OrderQunatityBasisDeliveryCostBO ordQtyBasiBO = (OrderQunatityBasisDeliveryCostBO)BOFactory.createBO(OrderQunatityBasisDeliveryCostBO.class);
//
//                        ordQtyBasiBO.setOrdBgnQty(ProductUtil.getLong(dlvCnt1[i]));
//                        ordQtyBasiBO.setOrdEndQty(ProductUtil.getLong(dlvCnt2[i]));
//                        ordQtyBasiBO.setDlvCst(ProductUtil.getLong(dlvCst3[i]));
//                        ordQtyBasiBO.setCreateNo(userNo);
//                        ordQtyBasiBO.setUpdateNo(userNo);
//
//                        ordQtyBasiDlvCstBOList.add(ordQtyBasiBO);
//
//                        if (i > 0) stbDlvCustInstBasiNm.append("/");
//                        if (StringUtil.isNotEmpty(dlvCst3[i]))	stbDlvCustInstBasiNm.append(ordQtyBasiBO.getDlvCst()).append("원 (");
//                        if (StringUtil.isNotEmpty(dlvCnt1[i]))	stbDlvCustInstBasiNm.append(ordQtyBasiBO.getOrdBgnQty()).append("개 ~");
//                        if (StringUtil.isNotEmpty(dlvCnt2[i]))
//                            stbDlvCustInstBasiNm.append(ordQtyBasiBO.getOrdBgnQty()).append("개)");
//                        else
//                            stbDlvCustInstBasiNm.append(ordQtyBasiBO.getOrdBgnQty()).append("이상)");
//                    }
//                }
//                productBO.setOrdQtyBasDlvcstBOList(ordQtyBasiDlvCstBOList);
//                productVO.setDlvCstInstBasiNm(stbDlvCustInstBasiNm.toString());
                break;
            case DELIVERY_COST_PER_ONE: //상품1개당 과금 05
                productVO.getBaseVO().setDlvCst(productVO.getBaseVO().getDlvCst());//getDlvCst4
                productVO.getBaseVO().setBndlDlvCnYn("N");
                break;
            case SELLER_CONDITIONAL_FREE:   //판매자 조건부 무료 06
                productVO.getBaseVO().setDlvCst(productVO.getBaseVO().getDlvCst());//getDlvCst2
                productVO.getBaseVO().setBndlDlvCnYn("Y");
                break;
            case SELLER_CONDITIONAL_STANDARD:   //판매자 조건부 기준(구매금액별) 07
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setBndlDlvCnYn("Y");
                break;
            case EX_WAREHOUSE_CONDITIONAL_STANDARD:   //출고지 조건부 기준 (구매금액별) 08
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setBndlDlvCnYn("Y");

                for(ClaimAddressInfoVO claimAddressInfoVO : productVO.getClaimAddressInfoVOList()){
                    if(ProductConstDef.PRD_ADDR_CLF_CD_OUT.equals(claimAddressInfoVO.getPrdAddrClfCd())){

                        dlvCstText = deliveryMapper.getAddrBasiDlvCstText(claimAddressInfoVO);
                        if(dlvCstText == null || "".equals(dlvCstText)){
                            throw new DeliveryException("출고지 조건부 배송비가 해당 출고지에 설정되어있지 않습니다.");
                        }
                    }
                }

                break;
            case UNITED_EX_WAREHOUSE_CONDITIONAL_STANDARD:   //통합출고지 조건부 기준 (구매금액별) 09
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setBndlDlvCnYn("Y");
                for(ClaimAddressInfoVO claimAddressInfoVO : productVO.getClaimAddressInfoVOList()){
                    if(ProductConstDef.PRD_ADDR_CLF_CD_OUT.equals(claimAddressInfoVO.getPrdAddrClfCd())){

                        dlvCstText = deliveryMapper.getAddrBasiDlvCstText(claimAddressInfoVO);
                        if(dlvCstText == null || "".equals(dlvCstText)){
                            throw new DeliveryException("통합출고지 조건부 배송비가 해당 출고지에 설정되어있지 않습니다.");
                        }
                    }
                }
                break;
            case ELEVENST_GLOBAL_DELIVERY_CONDITIONAL_STANDARD:   //해외 통합출고지조건부기준(무게별: 11번가 해외배송(03)일 경우에만 가능) 10
                productVO.getBaseVO().setDlvCst(0);
                productVO.getBaseVO().setBndlDlvCnYn("Y");
                if(!ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(productVO.getBaseVO().getDlvClf())){
                    throw new DeliveryException("해외 통합출고지 조건부 배송비는 11번가 해외배송만 가능합니다.");
                }
                break;
            case NOW_DELIVERY:   //Now배송 12
                productVO.getBaseVO().setDlvCst(productVO.getBaseVO().getDlvCst());//getDlvCst6
                productVO.getBaseVO().setPrdFrDlvBasiAmt(productVO.getBaseVO().getPrdFrDlvBasiAmt());//dlvBasiAmt2
                productVO.getBaseVO().setBndlDlvCnYn("Y");
                break;

            default:
                break;
        }
    }

    /**
     * 상품 주문수량기준 배송비 조회
     *
     * @param prdNo
     * @return List
     */
    public List<PrdOrderCountBaseDeliveryVO> getOrdQtyBasDlvcstList(long prdNo) throws DeliveryException {
        List<PrdOrderCountBaseDeliveryVO> dataList = null;
        try {
            dataList = deliveryMapper.getProductOrdDlvCstList(prdNo);
        } catch (Exception ex) {
            throw new DeliveryException("상품 주문수량기준 배송비  조회 오류", ex);
        }
        return dataList;
    }

    public String getSellerBasiDlvCstTxt(long memNo) throws DeliveryException {

        String sellerDlvInfoTxt = "";

        try {
            sellerDlvInfoTxt = deliveryMapper.getSellerBasiDlvCstTxt(memNo);
        } catch (Exception ex) {
            throw ex;
        }
        return sellerDlvInfoTxt;
    }

    public String getNowDlvCstTxt(String dlvCstInstBasiCd) throws DeliveryException {

        String nowDlvInfoTxt = "";

        try {

            nowDlvInfoTxt = deliveryMapper.getNowDlvCstTxt(dlvCstInstBasiCd);
        } catch (Exception ex) {
            throw ex;
        }
        return nowDlvInfoTxt;
    }

    /**
     * 회원의 통합 출고지 기본주소 조회
     *
     * @param claimAddressInfoVO
     * @return ClaimAddressInfoVO
     * @throws DeliveryException
     */
    public ClaimAddressInfoVO getItgBaseOutAddrInfo(ClaimAddressInfoVO claimAddressInfoVO) throws DeliveryException {
        ClaimAddressInfoVO retClaimAddressInfoVO = new ClaimAddressInfoVO();
        try {
            retClaimAddressInfoVO = deliveryMapper.getItgBaseOutAddrInfo(claimAddressInfoVO);
        } catch (Exception ex) {
            throw new DeliveryException(" 회원의 통합 출고지 기본주소 조회 오류", ex);
        }
        return retClaimAddressInfoVO;
    }

    /**
     * 회원의 통합 교환/반품지 기본주소 조회
     *
     * @param claimAddressInfoVO
     * @return ClaimAddressInfoVO
     * @throws DeliveryException
     */
    public ClaimAddressInfoVO getItgBaseInAddrInfo(ClaimAddressInfoVO claimAddressInfoVO) throws DeliveryException {
        ClaimAddressInfoVO retClaimAddressInfoVO = new ClaimAddressInfoVO();
        try {
            retClaimAddressInfoVO = deliveryMapper.getItgBaseInAddrInfo(claimAddressInfoVO);
        } catch (Exception ex) {
            throw new DeliveryException(" 회원의 통합 교환/반품지 기본주소 조회 오류", ex);
        }
        return retClaimAddressInfoVO;
    }

    public ClaimAddressInfoVO getIntegratedInOutAddrInfo(long userNo, String prdAddrClfCd, String memTyp, boolean isAbrdSubIntegId, boolean isOut) throws DeliveryException {
        ClaimAddressInfoVO paramBO = new ClaimAddressInfoVO();
        paramBO.setMemNo(userNo);
        paramBO.setAddrCd(prdAddrClfCd);

        ClaimAddressInfoVO inoutBasicAddrBO = null;
        //공급업체 또는 해외통합아이디의 해외셀러일 경우 통합반품지 주소가 있는지 먼저 조회 후 기본출고지/반품지 조회함
        if(ProductConstDef.PRD_MEM_TYPE_SUPPLIER.equals(memTyp) || (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memTyp) && isAbrdSubIntegId)) {
            if (isOut) {
                inoutBasicAddrBO = this.getItgBaseOutAddrInfo(paramBO);
            } else {
                inoutBasicAddrBO = this.getItgBaseInAddrInfo(paramBO);
            }
        }
        if(inoutBasicAddrBO == null) {
            inoutBasicAddrBO = deliveryMapper.getBaseAddrInfo(paramBO);
        }

        return inoutBasicAddrBO;
    }

    /**
     * 회원 기본 해외 출고지 조회
     *
     * @param claimAddressInfoVO
     * @return AddressBO
     * @throws DeliveryException
     */
    public ClaimAddressInfoVO getSellerBasicInOutAddrInfo(ClaimAddressInfoVO claimAddressInfoVO) throws DeliveryException {
        ClaimAddressInfoVO retClaimAddressInfoVO = new ClaimAddressInfoVO();
        try {
            retClaimAddressInfoVO = deliveryMapper.getSellerBasicInOutAddrInfo(claimAddressInfoVO);
        } catch (Exception ex) {
            throw new DeliveryException(" 회원의 해외 출고지 기본주소 조회 오류", ex);
        }
        return retClaimAddressInfoVO;
    }

    public ClaimAddressInfoVO getSellerBasicInOutAddrInfo(long userNo, String prdAddrClfCd, String memTyp, boolean isAbrdSubIntegId) throws DeliveryException {
        ClaimAddressInfoVO inOutBasicAddr = new ClaimAddressInfoVO();
        inOutBasicAddr.setMemNo(userNo);
        inOutBasicAddr.setAddrCd(prdAddrClfCd);

        ClaimAddressInfoVO baseAddrBO = null;
        //통합출고지 주소가 기본이고 해외셀러일 경우 기본 해외 출고지 주소 조회
        if(ProductConstDef.PRD_MEM_TYPE_SUPPLIER.equals(memTyp) ||
            (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memTyp) && isAbrdSubIntegId) &&
                (ProductConstDef.PRD_MEM_TYPE_GLOBAL.equals(memTyp) && isAbrdSubIntegId)) {
            baseAddrBO = this.getSellerBasicInOutAddrInfo(inOutBasicAddr);
        }

        return baseAddrBO;
    }

    /**
     * 전세계배송 이용 여부 - (구) ProductValidate.checkUseGlobalDeliver(userNo)
     *
     * @return
     */
    public boolean isUseGlobalDeliver(long memNo) throws DeliveryException {
        boolean result = false;
        MemberGlobalDeliverVO resultVO = new MemberGlobalDeliverVO();
        try {
            resultVO = deliveryMapper.getGlobalDeliverSeller(memNo);
            if (resultVO != null) {
                result = "Y".equals(resultVO.getGblDlvYn()) ? true : false;
            }
        } catch (Exception ex) {
            throw new DeliveryException("전세계배송 이용 여부 조회 오류", ex);
        }
        return result;
    }


    public void sellerBasiDlvCstSetOK(List<ProductSellerBasiDeliveryCostVO> sellerBasiDlvCstList, long memNo) {

        this.deleteSellerBasiDlvCst(memNo);

        if(sellerBasiDlvCstList != null && sellerBasiDlvCstList.size() > 0) {
            for (int i=0; i<sellerBasiDlvCstList.size(); i++)
            {

                ProductSellerBasiDeliveryCostVO dlvCstStdDtlsVO = (ProductSellerBasiDeliveryCostVO) sellerBasiDlvCstList.get(i);
                this.insertSellerBasiDlvCst(dlvCstStdDtlsVO);
            }
        }
    }

    private void insertSellerBasiDlvCst(ProductSellerBasiDeliveryCostVO dlvCstStdDtlsVO) {
        long dlvCstNo = this.geSellerBasiDlvCstSeq();
        dlvCstStdDtlsVO.setDlvCstInstNo(dlvCstNo);

        deliveryMapper.insertSellerBasiDlvCst(dlvCstStdDtlsVO);
        deliveryMapper.insertSellerBasiDlvCstHist(dlvCstStdDtlsVO);
    }

    private long geSellerBasiDlvCstSeq() {
        return deliveryMapper.geSellerBasiDlvCstSeq();
    }

    private void deleteSellerBasiDlvCst(long memNo) {
        deliveryMapper.deleteSellerBasiDlvCst(memNo);
        deliveryMapper.updateSellerBasiDlvCstHist(memNo);
    }

    public String getAddrBasiDlvCstTxt(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO) {
        return deliveryMapper.getAddrBasiDlvCstTxt(addrBasiDlvCstVO);
    }

    public String getAddrBasiDlvCstTxt2(ProductSellerBasiDeliveryCostVO addrBasiDlvCstVO) {
        return deliveryMapper.getAddrBasiDlvCstTxt2(addrBasiDlvCstVO);
    }

    public List<AddressVO> getProductAddressList(long prdNo) throws DeliveryServiceException {
        List<AddressVO> dataList = new ArrayList();

        try {

            AddressVO outAddressVO = null;
            AddressVO inAddressVO = null;
            AddressVO visitAddressVO = null;
            AddressVO globalOutAddressVO = null;
            AddressVO globalInAddressVO = null;
            String mbAddrLocation = "";

            // 출고지
            // 입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = deliveryMapper.getAddrLocation(prdNo, PrdAddrClfCd.EX_WAREHOUSE.getDtlsCd());


            if (mbAddrLocation.equals(MbAddrLocation.DOMESTIC.code)) { // 국내
                outAddressVO = deliveryMapper.getDomesticAddress(prdNo, PrdAddrClfCd.EX_WAREHOUSE.getDtlsCd());
            } else if (mbAddrLocation.equals(MbAddrLocation.OVERSEA.code)) { // 해외
                outAddressVO = deliveryMapper.getOverseaAddress(prdNo, PrdAddrClfCd.EX_WAREHOUSE.getDtlsCd());
            }

            // 교환/반품지
            // 입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = deliveryMapper.getAddrLocation(prdNo, PrdAddrClfCd.EXCHANGE_RETURN.getDtlsCd());
            if (mbAddrLocation.equals(MbAddrLocation.DOMESTIC.code)) { // 국내
                inAddressVO = deliveryMapper.getDomesticAddress(prdNo, PrdAddrClfCd.EXCHANGE_RETURN.getDtlsCd());
            } else if (mbAddrLocation.equals(MbAddrLocation.OVERSEA.code)) { // 해외
                inAddressVO = deliveryMapper.getOverseaAddress(prdNo, PrdAddrClfCd.EXCHANGE_RETURN.getDtlsCd());
            }

            // 방문수령지
            // 입력된 출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = deliveryMapper.getAddrLocation(prdNo, PrdAddrClfCd.VISIT_RECEIVE.getDtlsCd());
            if (mbAddrLocation != null) {
                if (mbAddrLocation.equals(MbAddrLocation.DOMESTIC.code)) { // 국내
                    visitAddressVO = deliveryMapper.getDomesticAddress(prdNo, PrdAddrClfCd.VISIT_RECEIVE.getDtlsCd());
                } else if (mbAddrLocation.equals(MbAddrLocation.OVERSEA.code)) { // 해외
                    visitAddressVO = deliveryMapper.getOverseaAddress(prdNo, PrdAddrClfCd.VISIT_RECEIVE.getDtlsCd());
                }
            }

            // 판매자 해외출고지
            // 입력된 판매자 해외출고지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = deliveryMapper.getAddrLocation(prdNo, PrdAddrClfCd.SELLER_GLOBAL_EX_WAREHOUSE.getDtlsCd());
            if (mbAddrLocation != null) {
                if (mbAddrLocation.equals(MbAddrLocation.DOMESTIC.code)) { // 국내
                    globalOutAddressVO = deliveryMapper.getDomesticAddress(prdNo, PrdAddrClfCd.SELLER_GLOBAL_EX_WAREHOUSE.getDtlsCd());
                } else if (mbAddrLocation.equals(MbAddrLocation.OVERSEA.code)) { // 해외
                    globalOutAddressVO = deliveryMapper.getGlobalOutAddress(prdNo, PrdAddrClfCd.SELLER_GLOBAL_EX_WAREHOUSE.getDtlsCd());
                }
            }

            // 판매자 반품/교환지
            // 입력된 판매자 반품/교환지 주소가 국내주소인지 해외주소인지 조회
            mbAddrLocation = (String) deliveryMapper.getAddrLocation(prdNo, PrdAddrClfCd.SELLER_EXCHANGE_RETURN.getDtlsCd());
            if (mbAddrLocation != null) {
                if (mbAddrLocation.equals(MbAddrLocation.DOMESTIC.code)) { // 국내
                    globalInAddressVO = deliveryMapper.getDomesticAddress(prdNo, PrdAddrClfCd.SELLER_EXCHANGE_RETURN.getDtlsCd());
                } else if (mbAddrLocation.equals(MbAddrLocation.OVERSEA.code)) { // 해외
                    globalInAddressVO = deliveryMapper.getOverseaAddress(prdNo, PrdAddrClfCd.SELLER_EXCHANGE_RETURN.getDtlsCd());
                }
            }

            if(outAddressVO != null) dataList.add(outAddressVO);
            if(inAddressVO != null) dataList.add(inAddressVO);

            if (visitAddressVO != null) {
                dataList.add(visitAddressVO);
            }
            if (globalOutAddressVO != null) {
                dataList.add(globalOutAddressVO);
            }
            if (globalInAddressVO != null) {
                dataList.add(globalInAddressVO);
            }


        } catch (Exception ex) {
            DeliveryServiceException threx = new DeliveryServiceException(DeliveryServiceExceptionMessage.PRODUCT_ADDRESS_LIST_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return dataList;
    }

}