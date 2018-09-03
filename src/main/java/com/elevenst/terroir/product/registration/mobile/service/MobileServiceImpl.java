package com.elevenst.terroir.product.registration.mobile.service;

import com.elevenst.terroir.product.registration.mobile.entity.PdPrdMobileFee;
import com.elevenst.terroir.product.registration.mobile.exception.MobileServiceException;
import com.elevenst.terroir.product.registration.mobile.mapper.MobileMapper;
import com.elevenst.terroir.product.registration.mobile.validate.MobileValidateion;
import com.elevenst.terroir.product.registration.mobile.vo.MobileFeeSearchParamVO;
import com.elevenst.terroir.product.registration.mobile.vo.PdPrdMobileFeeVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MobileServiceImpl {

    @Autowired
    MobileMapper mobileMapper;

    @Autowired
    MobileValidateion mobileValidate;

    public void updateMobileProcess(ProductVO productVO) throws MobileServiceException{
        this.updateProductMobileFeeInfo(productVO);
    }

    public void insertMobileProcess(ProductVO productVO) throws MobileServiceException{

        this.insertProductMobileFeeInfo(productVO);
    }

    public void setMobileInfoProcess(ProductVO preProductVO, ProductVO productVO) throws MobileServiceException{
        mobileValidate.checkMobileFeeInfo(productVO.getPriceVO().getMpContractTypCd(), productVO.getProductMobileFeeVOList(), productVO.getDispCtgrNo());
    }

    private void updateProductMobileFeeInfo(ProductVO productVO) throws MobileServiceException {
        try {

            PdPrdMobileFee param = new PdPrdMobileFee();
            param.setPrdNo(productVO.getPrdNo());

            mobileMapper.deleteProductMobileFee(param);

            this.insertProductMobileFeeInfo(productVO);
        } catch (Exception e) {
            throw new MobileServiceException("상품 휴대폰 요금제 정보 수정 오류", e);
        }
    }

    private void insertProductMobileFeeInfo(ProductVO productVO) {

        if(!mobileValidate.isMobileCtgr(productVO)) {
            return;
        }
        List<PdPrdMobileFeeVO> productMobileFeeVOList = productVO.getProductMobileFeeVOList();

        if(productMobileFeeVOList == null || productMobileFeeVOList.isEmpty()) return;

        try {

            for (PdPrdMobileFeeVO productMobileFeeVO : productMobileFeeVOList) {

                PdPrdMobileFee pdPrdMobileFee = new PdPrdMobileFee();
                pdPrdMobileFee.setPrdNo(productVO.getPrdNo());
                pdPrdMobileFee.setMpFeeNo(productMobileFeeVO.getMpFeeNo());
                pdPrdMobileFee.setContractTermCd(productMobileFeeVO.getContractTermCd());
                pdPrdMobileFee.setMaktPrc(productMobileFeeVO.getMaktPrc());
                pdPrdMobileFee.setRepFeeYn(productMobileFeeVO.getRepFeeYn());
                pdPrdMobileFee.setPrrtRnk(productMobileFeeVO.getPrrtRnk());
                pdPrdMobileFee.setCreateNo(productVO.getCreateNo());
                pdPrdMobileFee.setUpdateNo(productVO.getUpdateNo());

                mobileMapper.insertProductMobileFee(pdPrdMobileFee);
            }

            PdPrdMobileFee histParm = new PdPrdMobileFee();
            histParm.setPrdNo(productVO.getPrdNo());
            histParm.setHistAplBgnDt(productVO.getHistAplEndDt());

            mobileMapper.insertProductMobileFeeHistory(histParm);
        } catch (Exception e) {
            throw new MobileServiceException("상품 휴대폰 요금제 정보 등록 오류", e);
        }

    }

    public List<PdPrdMobileFee> searchMobileFee(MobileFeeSearchParamVO param) {
        return mobileMapper.searchMobileFee(param);
    }

    public List<HashMap> getGroupList(long dispCtgrNo) throws MobileServiceException {
        return mobileMapper.mobileGetGroupList(dispCtgrNo);
    }

    public List<HashMap> getMobileFeeNameList(long dispCtgrNo, String grpNm) throws MobileServiceException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("dispCtgrNo", dispCtgrNo);
        hashMap.put("grpNm", grpNm);
        return mobileMapper.getMobileFeeNameList(hashMap);
    }
}
