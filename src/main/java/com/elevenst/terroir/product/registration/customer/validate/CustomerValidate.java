package com.elevenst.terroir.product.registration.customer.validate;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.customer.service.CustomerServiceImpl;
import com.elevenst.terroir.product.registration.customer.vo.MemberVO;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerValidate {

    @Autowired
    CustomerServiceImpl customerService;

    // 통합아이디 체크  ==================================================================
    public boolean checkItgMemNo(long userNo, long selMnbdNo) throws ProductException {
        String msg = checkMsgItgMemNo(userNo, selMnbdNo);

        if (StringUtil.isNotEmpty(msg))  {
            throw new ProductException(msg);
        }

        return true;
    }

    private String checkMsgItgMemNo(long userNo, long selMnbdNo) {
        String msg = "";
        boolean flag = false;
        List<MemberVO> suplMemList = customerService.getSellersByItgID(userNo);

        // 통합 ID 로그인 시
        if (StringUtil.isNotEmpty(suplMemList)){
            for (MemberVO memberVO : suplMemList) {
                if (selMnbdNo == memberVO.getMemNO()){
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) 	msg = "통합아이디에 해당하는 공급업체를 선택하셔야 합니다.";

        return msg;
    }
}
