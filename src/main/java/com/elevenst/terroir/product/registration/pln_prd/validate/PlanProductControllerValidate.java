package com.elevenst.terroir.product.registration.pln_prd.validate;

import com.elevenst.terroir.product.registration.pln_prd.exception.PlanProductControllerValidateException;
import com.elevenst.terroir.product.registration.pln_prd.exception.PlanProductValidateException;
import com.elevenst.terroir.product.registration.pln_prd.message.PlanProductControllerValidateExceptionMessage;
import com.elevenst.terroir.product.registration.pln_prd.vo.PlanProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PlanProductControllerValidate {

    public void isPrdNoEmpty(PlanProductVO pdPlnPrd) {
        if(pdPlnPrd.getPrdNo() == 0)
            throwException(PlanProductControllerValidateExceptionMessage.PRD_NO_IS_EMPTY);
    }

    public void isWrhsPlnDyEmpty(PlanProductVO pdPlnPrd) {
        String wrhsPlnDy = pdPlnPrd.getWrhsPlnDy();
        if(wrhsPlnDy == null || wrhsPlnDy.isEmpty())
            throwException(PlanProductControllerValidateExceptionMessage.WRHS_PLN_DY_IS_EMPTY);
    }

    public void isWrhsDyEmpty(PlanProductVO pdPlnPrd) {
        String wrhsDy = pdPlnPrd.getWrhsDy();
        if(wrhsDy == null || wrhsDy.isEmpty())
            throwException(PlanProductControllerValidateExceptionMessage.WRHS_DY_IS_EMPTY);
    }

    public void isUpdateDtEmpty(PlanProductVO pdPlnPrd) {
        String updateDt = pdPlnPrd.getUpdateDt();
        if(updateDt == null || updateDt.isEmpty())
            throwException(PlanProductControllerValidateExceptionMessage.UPDATE_DT_IS_EMPTY);
    }



    private void throwException(String message) {
        PlanProductControllerValidateException threx = new PlanProductControllerValidateException(message);
        if (log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }
}
