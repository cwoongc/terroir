package com.elevenst.terroir.product.registration.help.service;

import com.elevenst.terroir.product.registration.help.exception.HelpServiceException;
import com.elevenst.terroir.product.registration.help.mapper.HelpMapper;
import com.elevenst.terroir.product.registration.help.vo.HelpVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelpServiceImpl {

    @Autowired
    HelpMapper helpMapper;

    /**
     * code값에 해당하는 Seller_Faq의 subject, content를 가져온다.
     * @param code
     * @return HelpBO
     */
    @SuppressWarnings("unchecked")
    public HelpVO getSellerFaq(String code) throws HelpServiceException {
        HelpVO sellerFaq = helpMapper.getSellerFaqs(code);
        return sellerFaq;
    }
}
