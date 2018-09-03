package com.elevenst.terroir.product.registration.pln_prd.service;

import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.terroir.product.registration.addtinfo.vo.ProductAddtInfoVO;
import com.elevenst.terroir.product.registration.pln_prd.entity.PdPlnPrd;
import com.elevenst.terroir.product.registration.pln_prd.exception.PlanProductServiceException;
import com.elevenst.terroir.product.registration.pln_prd.mapper.PlanProductMapper;
import com.elevenst.terroir.product.registration.pln_prd.message.PlanProductServiceExceptionMessage;
import com.elevenst.terroir.product.registration.pln_prd.validate.PlanProductValidate;
import com.elevenst.terroir.product.registration.pln_prd.vo.PlanProductVO;
import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanProductServiceImpl {

    @Autowired
    PlanProductMapper planProductMapper;

    @Autowired
    PlanProductValidate planProductValidate;


    public PlanProductVO getPlanProduct(long prdNo) throws PlanProductServiceException {
        try {
            PdPlnPrd pdPlnPrd = planProductMapper.getPlanProduct(prdNo);

            PlanProductVO planProductVO = new PlanProductVO();
            planProductVO.setPrdNo(pdPlnPrd.getPrdNo());
            planProductVO.setWrhsPlnDy(pdPlnPrd.getWrhsPlnDy());
            planProductVO.setWrhsDy(pdPlnPrd.getWrhsDy());
            planProductVO.setCreateDt(pdPlnPrd.getCreateDt());
            planProductVO.setCreateNo(pdPlnPrd.getCreateNo());
            planProductVO.setUpdateDt(pdPlnPrd.getUpdateDt());
            planProductVO.setUpdateNo(pdPlnPrd.getUpdateNo());

            return planProductVO;

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

    }

    public void updatePlanProductInfo(ProductVO productVO) throws PlanProductServiceException {
        try {
            if(GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.PLN)){
                planProductValidate.isSelMthdCdEmpty(productVO.getBaseVO().getSelMthdCd());
                planProductValidate.isNotPlanProduct(productVO.getBaseVO().getSelMthdCd());
                this.deletePlanProduct(productVO.getPrdNo());
                productVO.getPlanProductVO().setPrdNo(productVO.getPrdNo());
                productVO.getPlanProductVO().setCreateNo(productVO.getCreateNo());
                productVO.getPlanProductVO().setUpdateNo(productVO.getCreateNo());
                this.insertPlanProduct(productVO.getPlanProductVO());
            }
        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_INFO_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
    }


    public int registerNoServiceVoucherTypePlanProuduct(String prdTypeCd, String selMthdCd, long prdNo, String wrhsPlnDy, String wrhsDy, long createNo, long updateNo) {
        int insertCnt = 0;


        try {

            planProductValidate.isPrdTypeCdEmpty(prdTypeCd);
            planProductValidate.isServiceVoucherTypeProduct(prdTypeCd);
            planProductValidate.isSelMthdCdEmpty(selMthdCd);
            planProductValidate.isNotPlanProduct(selMthdCd);


            PdPlnPrd pdPlnPrd = new PdPlnPrd(
                    prdNo,
                    wrhsPlnDy,
                    wrhsDy,
                    createNo,
                    updateNo
            );

            insertCnt = this.insertPlanProduct(pdPlnPrd);

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.NO_SERVICE_VOUCHER_TYPE_PLAN_PRODUCT_REGISTER_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }



    /**
     * 예약상품정보 등록
     *
     * @param pdPlnPrd 예약상품 정보
     * @return 등록 행 수
     * @throws PlanProductServiceException
     */
    public int insertPlanProduct(PdPlnPrd pdPlnPrd) throws PlanProductServiceException {
        int insertCnt = 0;

        try {

            planProductValidate.validateInsert(pdPlnPrd);

            insertCnt = planProductMapper.insertPlanProduct(pdPlnPrd);

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return insertCnt;
    }

    /**
     * 예약상품정보 등록
     *
     * @param planProductVO 예약상품 정보
     * @return 등록 행 수
     * @throws PlanProductServiceException
     */
    public int insertPlanProduct(PlanProductVO planProductVO) throws PlanProductServiceException {
        int insertCnt = 0;

        try {

            PdPlnPrd pdPlnPrd = new PdPlnPrd(
                    planProductVO.getPrdNo(),
                    planProductVO.getWrhsPlnDy(),
                    planProductVO.getWrhsDy(),
                    planProductVO.getCreateNo(),
                    planProductVO.getUpdateNo()
            );

            // 예약상품정보 입력
            insertCnt = planProductMapper.insertPlanProduct(pdPlnPrd);

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return insertCnt;
    }


    /**
     * 예약상품정보 수정
     * @param pdPlnPrd 수정할 예약상품의 상품번호, 입고예정일자, 수정일시를 담은 예약상품정보
     * @return 수정된 행 수
     * @throws PlanProductServiceException
     */
    public int updatePlanProduct(PdPlnPrd pdPlnPrd) throws PlanProductServiceException {
        int rows = 0;
        try {
            planProductValidate.validateUpdate(pdPlnPrd);

            rows = planProductMapper.updatePlanProduct(pdPlnPrd);

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return rows;
    }


    public int updatePlanProduct(PlanProductVO planProductVO) throws PlanProductServiceException {
        int rows = 0;
        try {

            PdPlnPrd pdPlnPrd = new PdPlnPrd(
                   planProductVO.getPrdNo(),
                   planProductVO.getWrhsPlnDy(),
                    planProductVO.getUpdateDt(),
                    planProductVO.getUpdateNo()
            );


            planProductValidate.validateUpdate(pdPlnPrd);

            rows = planProductMapper.updatePlanProduct(pdPlnPrd);

        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_UPDATE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return rows;
    }



    /**
     * 예약상품정보 삭제
     *
     * @param prdNo 예약상품정보를 삭제할 상품번호
     * @return 삭제된 예약상품정보 행 수
     * @throws PlanProductServiceException
     */
    public int deletePlanProduct(long prdNo) throws PlanProductServiceException {
        int rows = 0;

        try {
            rows = planProductMapper.deletePlanProduct(prdNo);
        } catch (Exception ex) {
            PlanProductServiceException threx = new PlanProductServiceException(PlanProductServiceExceptionMessage.PLN_PRD_DELETE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }
        return rows;
    }


    public void insertPlanInfoProcess(ProductVO productVO) throws ProductException{

        if(productVO.getPlanProductVO() != null
            && GroupCodeUtil.equalsDtlsCd(productVO.getBaseVO().getSelMthdCd(), SelMthdCd.PLN)){

            this.registerNoServiceVoucherTypePlanProuduct(
                productVO.getBaseVO().getPrdTypCd()
                ,productVO.getBaseVO().getSelMthdCd()
                ,productVO.getPrdNo()
                ,productVO.getPlanProductVO().getWrhsPlnDy()
                ,productVO.getPlanProductVO().getWrhsDy()
                ,productVO.getCreateNo()
                ,productVO.getUpdateNo());
        }

    }

}
