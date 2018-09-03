package com.elevenst.terroir.product.registration.help.mapper;

import com.elevenst.terroir.product.registration.help.vo.HelpVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface HelpMapper {
    HelpVO getSellerFaqs(String code);
}
