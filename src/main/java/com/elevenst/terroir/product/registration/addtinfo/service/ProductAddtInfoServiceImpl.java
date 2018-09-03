package com.elevenst.terroir.product.registration.addtinfo.service;


import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.addtinfo.code.AddtInfoClfCd;
import com.elevenst.terroir.product.registration.addtinfo.entity.PdPrdAddtInfo;
import com.elevenst.terroir.product.registration.addtinfo.exception.ProductAddtInfoServiceException;
import com.elevenst.terroir.product.registration.addtinfo.mapper.ProductAddtInfoMapper;
import com.elevenst.terroir.product.registration.addtinfo.message.ProductAddtInfoServiceExceptionMessage;
import com.elevenst.terroir.product.registration.addtinfo.validate.ProductAddtInfoValidate;
import com.elevenst.terroir.product.registration.addtinfo.vo.ProductAddtInfoVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductAddtInfoServiceImpl {


    @Autowired
    ProductAddtInfoValidate productAddtInfoValidate;

    @Autowired
    ProductAddtInfoMapper productAddtInfoMapper;



    public void insertAddtInfoForGSShopSellerViaAPI(ProductVO productVO) {
        try {
            if("0100".equals(StringUtil.nvl(productVO.getBaseVO().getCreateCd())) && ("Y".equals(productVO.getBrodOnlyProductYn()) || "N".equals(productVO.getBrodOnlyProductYn()))) { //GS샵샐러 면서 API 일경우

                this.insertBrodOnlyProductYn(
                        productVO.getPrdNo(),
                        productVO.getBrodOnlyProductYn(),
                        productVO.getCreateNo(),
                        productVO.getUpdateNo()
                );
            }
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.GSSHOP_SELLER_VIA_API_ADDT_INFO_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

    }


    public void updateAddtInfoForGSShopSellerViaAPI(ProductVO productVO) {

        try {
            if ("0100".equals(StringUtil.nvl(productVO.getBaseVO().getCreateCd()))) { //GS샵샐러 면서 API 일경우

                List<PdPrdAddtInfo> pdPrdAddtInfoList = this.getProductAddtInfoList(productVO.getPrdNo());
                if (StringUtil.isNotEmpty(productVO.getBrodOnlyProductYn())) {
                    if (pdPrdAddtInfoList.size() > 0) {
                        this.updateBrodOnlyProductYn(productVO.getPrdNo(), productVO.getBrodOnlyProductYn(), productVO.getCreateNo(), productVO.getUpdateNo());
                    } else {
                        this.insertBrodOnlyProductYn(productVO.getPrdNo(), productVO.getBrodOnlyProductYn(), productVO.getCreateNo(), productVO.getUpdateNo());
                    }
                } else {
                    if (pdPrdAddtInfoList.size() > 0) {
                        this.deleteBrodOnlyProductYn(productVO.getPrdNo());
                    }
                }
            }
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.GSSHOP_SELLER_VIA_API_ADDT_INFO_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }



    /**
     * 상품 부가정보 목록 등록
     *
     * @param pdPrdAddtInfoList 등록할 상품 부가정보 목록
     * @return 등록된 상품 부가정보 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int insertProductAddtInfoList(List<PdPrdAddtInfo> pdPrdAddtInfoList) throws ProductAddtInfoServiceException {

        int insertCnt = 0;

        try {
            if (pdPrdAddtInfoList != null) {

                for (PdPrdAddtInfo pdPrdAddtInfo : pdPrdAddtInfoList) {

                    productAddtInfoValidate.validateInsert(pdPrdAddtInfo);

                    insertCnt += productAddtInfoMapper.insertProductAddtInfo(pdPrdAddtInfo);
                }
            }

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }

    /**
     * 상품 부가정보 목록 등록
     *
     * @param productAddtInfoVOList 등록할 상품 부가정보 목록
     * @return 등록된 상품 부가정보 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int insertProductAddtInfoListByVO(List<ProductAddtInfoVO> productAddtInfoVOList) throws ProductAddtInfoServiceException {

        int insertCnt = 0;

        try {
            if (productAddtInfoVOList != null) {

                List<PdPrdAddtInfo> pdPrdAddtInfoList = new ArrayList<>();

                for (ProductAddtInfoVO productAddtInfoVO : productAddtInfoVOList) {

                    PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                            productAddtInfoVO.getPrdNo(),
                            productAddtInfoVO.getAddtInfoClfCd(),
                            productAddtInfoVO.getAddtInfoCont(),
                            productAddtInfoVO.getCreateNo(),
                            productAddtInfoVO.getUpdateNo()
                    );

                    pdPrdAddtInfoList.add(pdPrdAddtInfo);
                }

                insertCnt = insertProductAddtInfoList(pdPrdAddtInfoList);
            }

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }


    /**
     * 상품 부가정보 목록 수정.
     * 매개변수로 전달된 상품 부가정보 목록내의 상품번호, 상품부가정보유형 코드를 기반으로 대상 상품 부가정보를 검색해 수정을 수행하고 해당 상품번호 상품부가정보유형에 해당하는 상품 부가정보가 없으면 전달된 상품 부가정보를 등록(INSERT)한다.
     *
     * @param pdPrdAddtInfoList 수정할 상품 부가정보 목록.
     * @return 수정된 상품 부가정보 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int updateProductAddtInfoList(List<PdPrdAddtInfo> pdPrdAddtInfoList) throws ProductAddtInfoServiceException {
        int updateCnt = 0;

        try {

            if (pdPrdAddtInfoList != null) {
                for (PdPrdAddtInfo pdPrdAddtInfo : pdPrdAddtInfoList) {

                    productAddtInfoValidate.validateUpdate(pdPrdAddtInfo);

                    int updateUnitCnt = 0;

                    if (0 == (updateUnitCnt = productAddtInfoMapper.updateProductAddtInfo(pdPrdAddtInfo))) {

                        productAddtInfoValidate.validateInsert(pdPrdAddtInfo);

                        productAddtInfoMapper.insertProductAddtInfo(pdPrdAddtInfo);
                    } else {
                        updateCnt += updateUnitCnt;
                    }
                }
            }

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return updateCnt;
    }


    /**
     * 상품 부가정보 목록 수정.
     * 매개변수로 전달된 상품 부가정보 목록내의 상품번호, 상품부가정보유형 코드를 기반으로 대상 상품 부가정보를 검색해 수정을 수행하고 해당 상품번호 상품부가정보유형에 해당하는 상품 부가정보가 없으면 전달된 상품 부가정보를 등록(INSERT)한다.
     *
     * @param productAddtInfoVOList 수정할 상품 부가정보 목록.
     * @return 수정된 상품 부가정보 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int updateProductAddtInfoListByVO(List<ProductAddtInfoVO> productAddtInfoVOList) throws ProductAddtInfoServiceException {
        int updateCnt = 0;

        try {

            if (productAddtInfoVOList != null) {


                List<PdPrdAddtInfo> pdPrdAddtInfoList = new ArrayList<>();

                for (ProductAddtInfoVO productAddtInfoVO : productAddtInfoVOList) {

                    PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                            productAddtInfoVO.getPrdNo(),
                            productAddtInfoVO.getAddtInfoClfCd(),
                            productAddtInfoVO.getAddtInfoCont(),
                            productAddtInfoVO.getCreateNo(),
                            productAddtInfoVO.getUpdateNo()
                    );

                    pdPrdAddtInfoList.add(pdPrdAddtInfo);
                }

                updateCnt = updateProductAddtInfoList(pdPrdAddtInfoList);
            }

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return updateCnt;
    }



    /**
     * 상품 부가정보 목록 조회.
     * 상품 번호로 해당 상품에 해당하는 부가정보를 조회해 온다.
     *
     *
     * @param prdNo 상품번호
     * @throws ProductAddtInfoServiceException
     */
    public List<PdPrdAddtInfo> getProductAddtInfoList(long prdNo) throws ProductAddtInfoServiceException {

        List<PdPrdAddtInfo> pdPrdAddtInfoList = null;

        try {

            pdPrdAddtInfoList = productAddtInfoMapper.getProductAddtInfoList(prdNo);

            /* 코드캐쉬에서 상품부가정보유형 코드를 검색하여 존재하는 상품부가 정보만 반환대상으로 하는 로직 코드 캐쉬 지속 사용유무에 따라 리팩토링한다 Fixme
            if (resultList != null) {
                for (ProductAddtInfoBO bo : resultList) {
                    String CommonDtlsComNm = TmallCommonCode.getCodeDetailName(ProductConstDef.COMMON_CODE_PRD_ADDT_INFO, bo.getAddtInfoClfCd());
                    if (CommonDtlsComNm == null || CommonDtlsComNm.length() == 0) {
                        resultList.remove(bo);
                    } else {
                        bo.setCommonDtlsComNm(CommonDtlsComNm);
                    }
                }
            }
            */
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return pdPrdAddtInfoList;
    }


    /**
     * 상품 부가정보 - 방송전용상품여부 등록.
     * 전달된 상품번호와 방송전용상품여부를 상품 부가정보 테이블에 기록한다
     *
     * @param prdNo 상품번호
     * @param brodOnlyProductYn 방송전용상품여부
     * @param createNo 생성자
     * @param updateNo 수정자
     * @return 처리 레코드 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int insertBrodOnlyProductYn(long prdNo, String brodOnlyProductYn, long createNo, long updateNo) throws ProductAddtInfoServiceException {

        int insertCnt = 0;

        try {
            PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                    prdNo,
                    AddtInfoClfCd.BROADCASTING_PRD_YN.code,
                    brodOnlyProductYn,
                    createNo,
                    updateNo
            );

            productAddtInfoValidate.validateInsert(pdPrdAddtInfo);

            insertCnt = productAddtInfoMapper.insertProductAddtInfo(pdPrdAddtInfo);

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.BROD_ONLY_ADDT_INFO_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }


    /**
     * 상품 부가정보 - 방송전용상품여부 수정.
     * 전달된 상품번호에 해당하는 상품 부가정보중 방송전용상품여부를 수정한다.
     *
     * @param prdNo 상품번호
     * @param brodOnlyProductYn 방송전용상품여부
     * @param createNo 생성자
     * @param updateNo 수정자
     * @return 처리 레코드 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int updateBrodOnlyProductYn(long prdNo, String brodOnlyProductYn, long createNo, long updateNo) throws ProductAddtInfoServiceException {

        int updateCnt = 0;

        try {
            PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                    prdNo,
                    AddtInfoClfCd.BROADCASTING_PRD_YN.code,
                    brodOnlyProductYn,
                    createNo,
                    updateNo
            );

            productAddtInfoValidate.validateUpdate(pdPrdAddtInfo);

            updateCnt = productAddtInfoMapper.updateProductAddtInfo(pdPrdAddtInfo);

        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.BROD_ONLY_ADDT_INFO_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return updateCnt;
    }


    /**
     * 상품 부가정보 - 방송전용상품여부 삭제.
     * 전달된 상품번호에 해당하는 상품 부가정보중 방송전용상품여부를 삭제한다.
     *
     * @param prdNo 상품번호
     * @return 처리 레코드 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int deleteBrodOnlyProductYn(long prdNo) throws ProductAddtInfoServiceException {
        int deleteCnt = 0;

        try {
            PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                    prdNo,
                    AddtInfoClfCd.BROADCASTING_PRD_YN.code
            );

            deleteCnt = productAddtInfoMapper.deleteProductAddtInfoWithClfCd(pdPrdAddtInfo);
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.BROD_ONLY_ADDT_INFO_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deleteCnt;
    }

    /**
     * 상품 부가정보 목록 삭제.
     * 전달된 상품번호에 해당하는 상품 부가정보를 삭제한다.
     *
     * @param prdNo 상품번호
     * @return 처리 레코드 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int deleteProductAddtInfo(long prdNo) throws ProductAddtInfoServiceException {
        int deleteCnt = 0;

        try {
            deleteCnt = productAddtInfoMapper.deleteProductAddtInfo(prdNo);
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deleteCnt;
    }


    /**
     * 상품 부가정보 - 방송전용상품여부 삭제.
     * 전달된 상품번호, 부가정보구분코드에 해당하는 상품 부가정보를 삭제한다.
     *
     * @param prdNo 상품번호
     * @return 처리 레코드 수
     * @throws ProductAddtInfoServiceException
     * @author wcchoi
     */
    public int deleteProductAddtInfo(long prdNo, AddtInfoClfCd addtInfoClfCd) throws ProductAddtInfoServiceException {
        int deleteCnt = 0;

        try {
            PdPrdAddtInfo pdPrdAddtInfo = new PdPrdAddtInfo(
                    prdNo,
                    addtInfoClfCd.code
            );

            deleteCnt = productAddtInfoMapper.deleteProductAddtInfoWithClfCd(pdPrdAddtInfo);
        } catch (Exception ex) {
            ProductAddtInfoServiceException threx = new ProductAddtInfoServiceException(ProductAddtInfoServiceExceptionMessage.ADDT_INFO_LIST_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deleteCnt;
    }



    public void insertAddtInfoProcess(ProductVO productVO) throws ProductAddtInfoServiceException{
        this.insertProductAddtInfoListByVO(productVO.getProductAddtInfoVOList());
        this.insertAddtInfoForGSShopSellerViaAPI(productVO);
    }

    public void updateAddtInfoProcess(ProductVO preProductVO, ProductVO productVO) throws ProductAddtInfoServiceException{
        this.updateProductAddtInfoListByVO(productVO.getProductAddtInfoVOList());
        this.updateAddtInfoForGSShopSellerViaAPI(productVO);
    }

}
