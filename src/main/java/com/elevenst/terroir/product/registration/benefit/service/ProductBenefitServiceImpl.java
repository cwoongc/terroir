package com.elevenst.terroir.product.registration.benefit.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.FileUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.benefit.entity.PdCustBnftInfo;
import com.elevenst.terroir.product.registration.benefit.entity.PdPrdGift;
import com.elevenst.terroir.product.registration.benefit.mapper.BenefitMapper;
import com.elevenst.terroir.product.registration.benefit.validate.BenefitValidate;
import com.elevenst.terroir.product.registration.benefit.vo.CustomerBenefitVO;
import com.elevenst.terroir.product.registration.benefit.vo.ProductGiftVO;
import com.elevenst.terroir.product.registration.common.property.FileProperty;
import com.elevenst.terroir.product.registration.product.code.SelStatCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductBenefitServiceImpl implements RegistrationInfoConverter<ProductVO>{

    public final String FILE_SEPERATOR = System.getProperty("file.separator");
    public final String PREFIX_GIFT_PATH = "/gift";

    @Autowired
    private BenefitMapper benefitMapper;

    @Autowired
    BenefitValidate benefitValidate;

    @Autowired
    FileProperty fileProperty;


    /* ProductGift */
    public ProductGiftVO getProductGift(long prdNo) {
        return benefitMapper.getProductGift(prdNo);
    }

    public void regProductGift(PdPrdGift pdPrdGift) {
        benefitMapper.updateProductGiftHist(pdPrdGift);
        if (pdPrdGift.getImgUrlPath() == null && pdPrdGift.getImgNm() == null) {
            // 이미지 변경 없이 수정일때. 이미지 경로정보 복사
            PdPrdGift giftImage = benefitMapper.getProductGiftImage(pdPrdGift.getPrdNo());
            if (giftImage != null) {
                pdPrdGift.setImgUrlPath(StringUtil.nvl(giftImage.getImgUrlPath()));
                pdPrdGift.setImgNm(StringUtil.nvl(giftImage.getImgNm()));
            }
        }
        benefitMapper.deleteProductGift(pdPrdGift.getPrdNo());
        benefitMapper.insertProductGift(pdPrdGift);
        benefitMapper.insertProductGiftHist(pdPrdGift.getPrdNo());
    }

    public void deleteProductGift(PdPrdGift pdPrdGift) {
        benefitMapper.updateProductGiftHist(pdPrdGift);
        benefitMapper.deleteProductGift(pdPrdGift.getPrdNo());
    }


    //I,D,U at PD_CUST_BNFT_INFO
    public void insertPdCustBnftInfo(ProductVO productVO, PdCustBnftInfo pdCustBnftInfo) {
        long pdCustBnftInfoSeq = benefitMapper.getSeqPdCustBnftInfoNEXTVAL();
        pdCustBnftInfo.setCustBnftNo(pdCustBnftInfoSeq);
        pdCustBnftInfo.setAplBgnDy(productVO.getBaseVO().getSelBgnDy());
        pdCustBnftInfo.setAplEndDy(productVO.getBaseVO().getSelEndDy());
        pdCustBnftInfo.setCreateNo(productVO.getUpdateNo());
        pdCustBnftInfo.setUpdateNo(productVO.getUpdateNo());
        benefitMapper.insertPdCustBnftInfo(pdCustBnftInfo);
        benefitMapper.insertPdCustBnftInfoHist(pdCustBnftInfo);
    }
    public void updatePdCustBnftInfo(ProductVO productVO, PdCustBnftInfo pdCustBnftInfo) {
        pdCustBnftInfo.setAplBgnDy(productVO.getBaseVO().getSelBgnDy());
        pdCustBnftInfo.setAplEndDy(productVO.getBaseVO().getSelEndDy());
        pdCustBnftInfo.setUpdateNo(productVO.getUpdateNo());
        benefitMapper.updatePdCustBnftInfo(pdCustBnftInfo);
        benefitMapper.insertPdCustBnftInfoHist(pdCustBnftInfo);
    }
    public void deletePdCustBnftInfo(PdCustBnftInfo pdCustBnftInfo) {
        benefitMapper.deletePdCustBnftInfo(pdCustBnftInfo);
        //benefitMapper.insertPdCustBnftInfoHist(pdCustBnftInfo);
    }


    private void updateCustomerBenefit(ProductVO productVO) {

        String nowDateStr = DateUtil.getFormatString("yyyyMMddHHmmss");

        //vo init.
        productVO.getCustomerBenefitVO().setPrdNo(productVO.getPrdNo());
        productVO.getCustomerBenefitVO().setEvntNo(0);
        productVO.getCustomerBenefitVO().setBnftSelMnbdNo(productVO.getSelMnbdNo());
        CustomerBenefitVO customerBenefitVO = productVO.getCustomerBenefitVO();

        //판매강제종료된 상품 재등록시 고객혜택 등록을 위하여 (QC-3910)
        boolean isReSelAvailFlag = StringUtils.equals("Y", productVO.getReNewYn()) &&
                                    GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelStatCd(), SelStatCd.PRODUCT_FORCE_SELLING_END);
        String renewSelStatCd = isReSelAvailFlag ? SelStatCd.PRODUCT_SELLING.getDtlsCd() : productVO.getBaseVO().getSelStatCd();

        //현재의 고객혜택정보를 가져온다
        CustomerBenefitVO currentCustomerBenefitVO = benefitMapper.getCurrentCustomerBenefitInfo(customerBenefitVO);
        PdCustBnftInfo pdCustBnftInfo = new PdCustBnftInfo();
        BeanUtils.copyProperties(customerBenefitVO, pdCustBnftInfo);

        //판매중이나 과거에판매되었었던 상태
        if (GroupCodeUtil.isContainsDtlsCd(renewSelStatCd, SelStatCd.PRODUCT_SELLING, SelStatCd.AUCTION_SELLING,
                                                           SelStatCd.PRODUCT_SOLD_OUT, SelStatCd.PRODUCT_SELLING_STOP,
                                                           SelStatCd.PRODUCT_SELLING_END)) {

            if(currentCustomerBenefitVO != null) { //기존 고객혜택 정보가 있으면
                pdCustBnftInfo.setChpSpplQty(currentCustomerBenefitVO.getChpSpplQty());
                pdCustBnftInfo.setIntfreeAplQty(currentCustomerBenefitVO.getIntfreeAplQty());
                pdCustBnftInfo.setPntSpplQty(currentCustomerBenefitVO.getPntSpplQty());
                pdCustBnftInfo.setOcbSpplQty(currentCustomerBenefitVO.getOcbSpplQty());
                pdCustBnftInfo.setMileageSpplQty(currentCustomerBenefitVO.getMileageSpplQty());
                pdCustBnftInfo.setUseYn("Y");
                pdCustBnftInfo.setCustBnftNo(currentCustomerBenefitVO.getCustBnftNo());

                //기존 고객혜택정보 업데이트
                updatePdCustBnftInfo(productVO, pdCustBnftInfo);
            }
            else { //기존 고객혜택 정보가 없을 때
                pdCustBnftInfo.setAplBgnDy(nowDateStr);
                pdCustBnftInfo.setUseYn("Y");
                //고객혜택정보 신규등록
                insertPdCustBnftInfo(productVO, pdCustBnftInfo);
            }

        }
        else if (GroupCodeUtil.isContainsDtlsCd(renewSelStatCd, SelStatCd.PRODUCT_COMFIRM_WAIT, SelStatCd.PRODUCT_DISPLAY_BEFORE,
                                                                SelStatCd.AUCTION_COMFIRM_WAIT, SelStatCd.AUCTION_DISPLAY_BEFORE)) {

            if(currentCustomerBenefitVO != null) { //기존 고객혜택 정보가 있으면
                //삭제 후 재 insert
                deletePdCustBnftInfo(pdCustBnftInfo);
                pdCustBnftInfo.setUseYn("Y");
                insertPdCustBnftInfo(productVO, pdCustBnftInfo);
            }
            else { //기존 고객혜택 정보가 없을 떄
                //그냥 insert
                insertPdCustBnftInfo(productVO, pdCustBnftInfo);
            }
        }
    }


    public void insertCustomerBenefitAsRegType(ProductVO productVO) {

        if(productVO.getCustomerBenefitVO() == null) {
            return;
        }

        //기타 고객혜택을 적용시킬 수 있는 플래그 값들
        boolean useBenefit = benefitValidate.checkAddCustomerBenefitAvail(productVO.getCustomerBenefitVO());

        //자동 재등록 여부 && 기타 고객혜택을 적용시킬 수 있는 플래그 값들
        if(productVO.getBaseVO().getReRegPrdNo() > 0 || useBenefit)
        {
            PdCustBnftInfo pdCustBnftInfo = new PdCustBnftInfo();
            BeanUtils.copyProperties(productVO.getCustomerBenefitVO(), pdCustBnftInfo);

            pdCustBnftInfo.setPrdNo(productVO.getPrdNo());
            pdCustBnftInfo.setUseYn("Y");

            insertPdCustBnftInfo(productVO, pdCustBnftInfo);
        }

    }

    public void upateBenefitInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        this.updateCustomerBenefit(productVO);
    }

    public void insertBenefitInfoProcess(ProductVO productVO) throws ProductException{
        this.insertCustomerBenefitAsRegType(productVO);
    }

    public void mergeGiftInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductException{

        // TODO: yskim 사은품수정 로직
        if(productVO.getProductGiftVO() != null && "Y".equals(productVO.getProductGiftVO().getUseGiftYn())) {
            PdPrdGift pdPrdGift = new PdPrdGift();
            BeanUtils.copyProperties(productVO.getProductGiftVO(), pdPrdGift);
            pdPrdGift.setPrdNo(productVO.getPrdNo());
            this.regProductGift(pdPrdGift);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProductVO, ProductVO productVO) {
        this.convertBenefitInfo(preProductVO, productVO);

        this.convertGiftInfo(preProductVO, productVO);
    }

    private void convertGiftInfo(ProductVO preProductVO, ProductVO productVO) {
        // TODO: yskim 사은품 로직
        if("Y".equals(StringUtil.nvl(productVO.getUseGiftYn()))) {
            ProductGiftVO productGiftVO = productVO.getProductGiftVO();
            productGiftVO.setPrdNo(productVO.getPrdNo());
            productGiftVO.setCreateNo(productVO.getSelMnbdNo());
            productGiftVO.setUpdateNo(productVO.getSelMnbdNo());
            ProductGiftVO preProductGiftVO = benefitMapper.getProductGift(productGiftVO.getPrdNo());

            if("Y".equals(StringUtil.nvl(productGiftVO.getImgUploadYn(),"N")) || preProductGiftVO == null || !preProductGiftVO.compare(productGiftVO)) {
                benefitValidate.checkProductGiftLen(productGiftVO);
                if(preProductVO != null && preProductVO.getProductGiftVO() != null) {
                    benefitValidate.checkProductGift(productGiftVO, preProductVO.getProductGiftVO());
                }
                // 이미지 업로드
                //if("Y".equals(productGiftVO.getImgUploadYn()) && productGiftVO.getImgFile() != null) {
                if("Y".equals(productGiftVO.getImgUploadYn())) {
                    //benefitValidate.checkProductGiftImage(productGiftVO.getImgFile());

                    String giftImgUrl = saveFile(productGiftVO, "G");
                    int idx = giftImgUrl.lastIndexOf(FILE_SEPERATOR);
                    productGiftVO.setImgUrlPath(giftImgUrl.substring(0, idx+1).replace("\\", "/"));
                    productGiftVO.setImgNm(giftImgUrl.substring(idx+1));
                }
                //PdPrdGift pdPrdGift = new PdPrdGift();
                //BeanUtils.copyProperties(productGiftVO, pdPrdGift);
                //this.regProductGift(pdPrdGift);
            }
            productVO.setProductGiftVO(productGiftVO);

        } /*else if(preProductVO != null && "Y".equals(StringUtil.nvl(preProductVO.getUseGiftYn()))){
            benefitMapper.deleteProductGift(productVO.getPrdNo());
        }*/
    }

    /**
     * 사은품 이미지 파일 업로드 처리
     *
     * @return String
     * @throws ProductException
     */
    private String saveFile(ProductGiftVO productGiftVO, String imgIdx) throws ProductException {
        //String saveDirectory = fileProperty.getUploadPath()+fileProperty.getGiftPath();
        String prefixUploadPath = fileProperty.getGiftPath()+FILE_SEPERATOR+DateUtil.formatDate("yyyy/MM/dd")+FILE_SEPERATOR+productGiftVO.getUpdateNo();
        String dbUploadPath = PREFIX_GIFT_PATH+FILE_SEPERATOR+DateUtil.formatDate("yyyy/MM/dd")+FILE_SEPERATOR+productGiftVO.getUpdateNo();
        //String savedFileName = "";
        String savedFileName = productGiftVO.getImgNm();
        String dbRegFileName = "";

        try {
            if (productGiftVO != null) {
                // 파일명을 직접 지정해 주기 때문에 같은 파일이 있을 경우에는 overwrite 한다.
                //FileUploadUtil.doFileUpload(fileItem, saveDirectory, new TMallFileNamePolicy(userNO));
                //savedFileName = fileItem.getSavedFileName();

                //File oldFile = new File(fileItem.getSavedFilePath() + "/" + savedFileName);
                String curTime = DateUtil.formatDate("yyyyMMddHHmmddssS"); // 밀리세컨 단위로 현재시각을 얻는다.

                int idx = savedFileName.lastIndexOf(".");
                String extention = "";

                if(idx != -1) {
                    extention = savedFileName.substring(idx);
                }

                String newFileName = curTime + "_" + productGiftVO.getUpdateNo() + extention;

                //파일명에 시간이 연속적으로 같은경우가 간헐적으로 발생하여 이미지업로드별로 INDEX를 주어 중복을 제거한다
                if(imgIdx!=null&&!imgIdx.equals("")) {
                    newFileName = curTime + "_" + productGiftVO.getUpdateNo()+"_"+imgIdx + extention;
                }/*else {
                    newFileName = curTime + "_" + productGiftVO.getUpdateNo() + extention;
                }*/
                savedFileName = prefixUploadPath+FILE_SEPERATOR+newFileName;
                dbRegFileName = dbUploadPath+FILE_SEPERATOR+newFileName;

                String tempGifePath = fileProperty.getTempPath()+FILE_SEPERATOR+DateUtil.formatDate("yyyyMMdd")+FILE_SEPERATOR+productGiftVO.getUpdateNo()+FILE_SEPERATOR+productGiftVO.getImgNm();
                FileUtil.copy(tempGifePath, savedFileName);
            }//if
        }catch (Exception e) {
            throw new ProductException (fileProperty.getGiftPath() + "디렉토리에 파일 저장 오류", e);
        }
        return dbRegFileName;
    }

    private void convertBenefitInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        CustomerBenefitVO customerBenefitVO = productVO.getCustomerBenefitVO();
        if(customerBenefitVO == null){
            return;
        }
        this.checkBenefitInfo(preProductVO, productVO);
    }

    private void checkBenefitInfo(ProductVO preProductVO, ProductVO productVO) throws ProductException{
        benefitValidate.checkChipInfo(productVO);
        benefitValidate.checkPointInfo(productVO);
        benefitValidate.checkOCBInfo(productVO);
        benefitValidate.checkMileageInfo(productVO);
        benefitValidate.checkCardFreeMonInfo(productVO);

    }

    /**
     * 상품 고객 혜택 정보 조회
     * ; 상품 등록/수정 용
     *
     * @param prdNo
     * @return CustomerBenefitBO
     * @throws ProductException
     */
    public CustomerBenefitVO getCustomerBenefit(long prdNo) throws ProductException {
        CustomerBenefitVO custBenefitBO = null;
        try {
            custBenefitBO = benefitMapper.getCustomerBenefit(prdNo);
            if(custBenefitBO != null) {
                custBenefitBO.setOcbYn("N");
                if(StringUtil.isNotEmpty(custBenefitBO.getOcbWyCd())) {
                    custBenefitBO.setOcbYn("Y");
                }
            }
        } catch (Exception ex) {
            throw new ProductException("상품 고객 혜택 정보 조회 오류", ex);
        }
        return custBenefitBO;
    }

    /**
     * 복수구매 할인
     * @param prdNo
     * @return
     * @throws ProductException
     */
    public List<CustomerBenefitVO> getCustomerPrdPluDscList(long prdNo) throws ProductException {
        try {
            return benefitMapper.getCustomerPrdPluDscList(prdNo);
        } catch (Exception ex) {
            throw new ProductException("상품 고객 혜택 정보 조회 오류", ex);
        }
    }

    public void getCustomerPrdInfo(ProductVO productVO) throws ProductException{
        CustomerBenefitVO customerBenefitVO = benefitMapper.getCustomerBenefit(productVO.getPrdNo());
        if(customerBenefitVO != null){
            if(productVO.getCustomerBenefitVO() == null) productVO.setCustomerBenefitVO(new CustomerBenefitVO());
            this.setStdNotSupportInfo(productVO, customerBenefitVO);
        }
    }

    private void setStdNotSupportInfo(ProductVO productVO, CustomerBenefitVO custBnftVO) throws ProductException{
        CustomerBenefitVO vo = productVO.getCustomerBenefitVO();

        if(StringUtil.isNotEmpty(custBnftVO.getIntfreeMonClfCd())){
            if(StringUtil.isEmpty(vo.getIntfreeMonClfCd())) vo.setIntfreeMonClfCd(custBnftVO.getIntfreeMonClfCd());
            if(vo.getIntfreeAplLmtQty() <= 0) vo.setIntfreeAplLmtQty(custBnftVO.getIntfreeAplLmtQty());
            if(vo.getIntfreeAplQty() <= 0) vo.setIntfreeAplLmtQty(custBnftVO.getIntfreeAplQty());
            vo.setIntFreeYn("Y");
        }

        if(custBnftVO.getHopeShpPnt() > 0 || custBnftVO.getHopeShpPntRt() > 0){

            if(vo.getHopeShpPntRt() <= 0) vo.setHopeShpPntRt(custBnftVO.getHopeShpPntRt());
            if(vo.getHopeShpPnt() <= 0) vo.setHopeShpPnt(custBnftVO.getHopeShpPnt());
            if(vo.getHopeShpSpplLmtQty() <= 0) vo.setHopeShpSpplLmtQty(custBnftVO.getHopeShpSpplLmtQty());
            if(vo.getHopeShpSpplQty() <= 0) vo.setHopeShpSpplQty(custBnftVO.getHopeShpSpplQty());
            if(StringUtil.isEmpty(vo.getHopeShpWyCd())) vo.setHopeShpWyCd(custBnftVO.getHopeShpWyCd());
            vo.setHopeShpYn("Y");
        }

        productVO.setCustomerBenefitVO(vo);
    }
}
