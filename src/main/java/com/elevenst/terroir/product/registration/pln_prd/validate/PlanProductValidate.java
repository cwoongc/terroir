package com.elevenst.terroir.product.registration.pln_prd.validate;

import com.elevenst.terroir.product.registration.pln_prd.entity.PdPlnPrd;
import com.elevenst.terroir.product.registration.pln_prd.exception.PlanProductValidateException;
import com.elevenst.terroir.product.registration.pln_prd.message.PlanProductValidateExceptionMessage;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.code.SelMthdCd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PlanProductValidate {


    public void isPrdTypeCdEmpty(String prdTypeCd) {
        if(prdTypeCd == null || prdTypeCd.isEmpty())
            throwException(PlanProductValidateExceptionMessage.PRD_TYPE_CD_IS_EMPTY);
    }


    public void isServiceVoucherTypeProduct(String prdTypeCd) {
        if(prdTypeCd.equals(PrdTypCd.SERVICE_VOUCHER.getDtlsCd()))
            throwException(PlanProductValidateExceptionMessage.SERVICE_VOUCHER_TYPE_NOT_ALLOWED);

    }

    public void isSelMthdCdEmpty(String selMthdCd) {
        if(selMthdCd == null || selMthdCd.isEmpty())
            throwException(PlanProductValidateExceptionMessage.SEL_MTHD_CD_IS_EMPTY);

    }

    public void isNotPlanProduct(String selMthdCd) {
        if(!selMthdCd.equals(SelMthdCd.PLN.getDtlsCd()))
            throwException(PlanProductValidateExceptionMessage.SEL_MTHD_CD_IS_NOT_PLN);

    }



    public void isPrdNoEmpty(PdPlnPrd pdPlnPrd) {
        if(pdPlnPrd.getPrdNo() == 0)
            throwException(PlanProductValidateExceptionMessage.PRD_NO_IS_EMPTY);
    }

    public void isWrhsPlnDyEmpty(PdPlnPrd pdPlnPrd) {
        String wrhsPlnDy = pdPlnPrd.getWrhsPlnDy();
        if(wrhsPlnDy == null || wrhsPlnDy.isEmpty())
            throwException(PlanProductValidateExceptionMessage.WRHS_PLN_DY_IS_EMPTY);
    }

    public void isWrhsDyEmpty(PdPlnPrd pdPlnPrd) {
        String wrhsDy = pdPlnPrd.getWrhsDy();
        if(wrhsDy == null || wrhsDy.isEmpty())
            throwException(PlanProductValidateExceptionMessage.WRHS_DY_IS_EMPTY);
    }

    public void isUpdateDtEmpty(PdPlnPrd pdPlnPrd) {
        String updateDt = pdPlnPrd.getUpdateDt();
        if(updateDt == null || updateDt.isEmpty())
            throwException(PlanProductValidateExceptionMessage.UPDATE_DT_IS_EMPTY);
    }



    private void throwException(String message) {
        PlanProductValidateException threx = new PlanProductValidateException(message);
        if (log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }


    public void validateInsert(PdPlnPrd pdPlnPrd) {
        isWrhsPlnDyEmpty(pdPlnPrd);
    }

    public void validateUpdate(PdPlnPrd pdPlnPrd) {
        isWrhsPlnDyEmpty(pdPlnPrd);
        isUpdateDtEmpty(pdPlnPrd);
    }
}
