package com.elevenst.terroir.product.registration.template.validate;

import com.elevenst.terroir.product.registration.product.code.ProductConstDef;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.template.exception.TemplateValidateException;
import com.elevenst.terroir.product.registration.template.vo.ProductInformationTemplateVO;
import com.elevenst.terroir.product.registration.template.vo.SellInformationVO;
import org.springframework.stereotype.Component;

@Component
public class TemplateValidate {

    public void checkTemplateParameter(ProductInformationTemplateVO paramVO) {
        SellInformationVO sellInformationVO = paramVO.getSellInformationVO();

        // 해외취소배송비 / 상품무게 체크
        long abrdCnDlvCst	= sellInformationVO.getAbrdCnDlvCst();
        long prdWght		= sellInformationVO.getPrdWght();
        if(ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(sellInformationVO.getDlvClf())){	//11번가 해외배송여부 확인
            //해외취소배송비 체크
            if(ProductConstDef.ABRD_IN_CD_FREE.equals(sellInformationVO.getAbrdInCd()))		//11번가 무료픽업인 경우
                sellInformationVO.setAbrdCnDlvCst(0);		//무조건 해외취소배송비는 0원
            else{	//무료픽업 아님
                if(abrdCnDlvCst<= 0)
                    throw new TemplateValidateException("11번가 해외배송. 해외취소배송비 오류");
            }

            //상품무게 체크
            if(prdWght<= 0)
                throw new TemplateValidateException("11번가 해외배송. 상품무게 오류");
        }else{
            sellInformationVO.setAbrdCnDlvCst(0);
            sellInformationVO.setPrdWght(0);
        }
        //배송비와 배송주체 체크
        if ((ProductConstDef.DLV_CST_INST_BASI_CD_GLB_ITG_ADDR.equals(sellInformationVO.getDlvCstInstBasiCd())) &&
            !ProductConstDef.DLV_CLF_GLOBAL_DELIVERY.equals(sellInformationVO.getDlvClf()))//11번가 해외배송여부 확인
        {
            throw new TemplateValidateException("통합출고지 조건부 배송비. 배송주체 오류");
        }
    }
}
