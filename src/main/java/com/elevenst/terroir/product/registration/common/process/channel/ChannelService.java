package com.elevenst.terroir.product.registration.common.process.channel;

import com.elevenst.exception.TerroirException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;

public interface ChannelService {

    public void setChannelInfo(ProductVO productVO) throws TerroirException;
}
