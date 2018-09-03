package com.elevenst.terroir.product.registration.coordi_prd.service;

import com.elevenst.terroir.product.registration.coordi_prd.entity.PdPrdCoordi;
import com.elevenst.terroir.product.registration.coordi_prd.exception.CoordiProductServiceException;
import com.elevenst.terroir.product.registration.coordi_prd.mapper.CoordiProductMapper;
import com.elevenst.terroir.product.registration.coordi_prd.message.CoordiProductServiceExceptionMessage;
import com.elevenst.terroir.product.registration.coordi_prd.validate.CoordiProductValidate;
import com.elevenst.terroir.product.registration.coordi_prd.vo.CoordiProductVO;
import com.elevenst.terroir.product.registration.coordi_prd.vo.DisplayCoordiProductVO;
import com.elevenst.terroir.product.registration.desc.entity.PdPrdDesc;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CoordiProductServiceImpl {


    @Autowired
    CoordiProductValidate coordiProductValidate;

    @Autowired
    CoordiProductMapper coordiProductMapper;


    /**
     * 코디상품 목록 등록
     *
     * @param pdPrdCoordiList 등록할 PdPrdCoordi 목록
     * @return 등록된 PdPrdCoordi 수
     * @throws CoordiProductServiceException
     * @author wcchoi
     */
    public int insertCoordiProductList(List<PdPrdCoordi> pdPrdCoordiList) throws CoordiProductServiceException {

        int insertCnt = 0;

        try {
            if (pdPrdCoordiList != null) {

                for (PdPrdCoordi pdPrdCoordi : pdPrdCoordiList) {

                    coordiProductValidate.validateInsert(pdPrdCoordi);

                    insertCnt += coordiProductMapper.insertCoordiProduct(pdPrdCoordi);
                }
            }

        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.COORDI_PRODUCT_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }

    /**
     * 코디상품 목록 등록. pdPrdCoordiList에 코디상품번호(coordiPrdNo)는 저장한 채로 넘겨고, 상품번호와 수정자는 별도 파라메터로 전달한다.
     *
     * @param prdNo 상품번호
     * @param createNo 생성자번호
     * @param pdPrdCoordiList 등록할 PdPrdCoordi 목록
     *
     * @return 등록된 PdPrdCoordi 수
     * @throws CoordiProductServiceException
     * @author wcchoi
     */
    public int insertCoordiProductList(long prdNo, long createNo, List<PdPrdCoordi> pdPrdCoordiList) throws CoordiProductServiceException {
        int insertCnt = 0;

        try {
            if (pdPrdCoordiList != null) {

                for (PdPrdCoordi pdPrdCoordi : pdPrdCoordiList) {

                    pdPrdCoordi.setPrdNo(prdNo);
                    pdPrdCoordi.setCreateNo(createNo);

                    coordiProductValidate.validateInsert(pdPrdCoordi);

                    insertCnt += coordiProductMapper.insertCoordiProduct(pdPrdCoordi);
                }
            }

        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.COORDI_PRODUCT_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }

    /**
     * 코디상품 목록 교체. 매개변수로 넘기는 상품의 코디상품을 먼저 모두 삭제하고 매개변수로 넘기는 코디상품 값객체 목록으로 해당 상품의 새로운 코디상품들을 등록한다.
     *
     * @param productVO 상품정보
     *
     * @return 등록된 코디상품 수
     * @throws CoordiProductServiceException
     * @author wcchoi
     */
    public int replaceCoordiProductList(ProductVO productVO) throws CoordiProductServiceException {
        int insertCnt = 0;

        try {

            deleteCoordiProduct(productVO.getPrdNo());
            List<CoordiProductVO> coordiProductVOList = productVO.getCoordiProductVOList();

            if (coordiProductVOList != null && coordiProductVOList.size() > 0) {

                List<PdPrdCoordi> pdPrdCoordiList = new ArrayList<>();

                for (CoordiProductVO coordiProductVO : coordiProductVOList) {

                    pdPrdCoordiList.add(new PdPrdCoordi(
                            productVO.getPrdNo(),
                            coordiProductVO.getCoordiPrdNo(),
                            productVO.getUpdateNo()
                    ));
                }

                insertCnt += insertCoordiProductList(pdPrdCoordiList);
            }

        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.COORDI_PRODUCT_REPLACE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }






    /**
     * 코디상품 삭제.
     * 전달된 상품번호에 해당하는 코디상품정보를 삭제한다.
     *
     * @param prdNo 상품번호
     * @return 처리 레코드 수
     * @throws CoordiProductServiceException
     * @author wcchoi
     */
    public int deleteCoordiProduct(long prdNo) throws CoordiProductServiceException {
        int deleteCnt = 0;

        try {


            deleteCnt = coordiProductMapper.deleteCoordiProduct(prdNo);
        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.COORDI_PRODUCT_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return deleteCnt;
    }

    /**
     * 코디상품번호 목록 조회.
     * 상품 번호로 해당 상품에 맵핑되는 코디상품번호 목록을 조회해 온다.
     *
     * 구) ProductRegistrationServiceImpl.getMyCoordiPrdList(long prdNo)
     *
     * @param prdNo 상품번호
     * @throws CoordiProductServiceException
     * @return 코디상품번호 목록
     * @author wcchoi
     */
    public List<CoordiProductVO> getCoordiPrdNoList(long prdNo) throws CoordiProductServiceException {

        List<CoordiProductVO> coordiPrdNoList = null;

        try {

            coordiPrdNoList = coordiProductMapper.getCoordiPrdNoList(prdNo);


        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.COORDI_PRODUCT_NO_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return coordiPrdNoList;
    }


    /**
     * 전시 코디 상품 목록 조회.
     * 상품 번호로 해당 상품에 맵핑되는 코디상품중 전시되고 있는 코디 상품 목록을 조회해 온다.
     *
     * 구) SellerProductDetailServiceImpl.getSohoCoordiPrdList(Map<String, Object> map)
     *
     * @param prdNo 상품번호
     * @throws CoordiProductServiceException
     * @return 전시 코디상품 목록
     * @author wcchoi
     */
    public List<DisplayCoordiProductVO> getDisplayCoordiProductList(long prdNo) throws CoordiProductServiceException {

        List<DisplayCoordiProductVO> displayCoordiProductList = null;

        try {

            displayCoordiProductList = coordiProductMapper.getDisplayCoordiProductList(prdNo);


        } catch (Exception ex) {
            CoordiProductServiceException threx = new CoordiProductServiceException(CoordiProductServiceExceptionMessage.DISPLAY_COORDI_PRODUCT_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return displayCoordiProductList;
    }


    public void insertCoordiInfoProcess(ProductVO productVO) throws CoordiProductServiceException{
        this.replaceCoordiProductList(productVO);
    }

    public void updateCoordiInfoProcess(ProductVO preProductVO, ProductVO productVO) throws CoordiProductServiceException{
        this.replaceCoordiProductList(productVO);
    }

    public void setCoodiProductInfoProcess(ProductVO preProductVO, ProductVO productVO) throws CoordiProductServiceException{
        coordiProductValidate.checkCoodiProductInfo(productVO);
    }
}
