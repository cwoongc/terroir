package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionWriteVO implements Serializable {
    private static final long serialVersionUID = 2307114804291174480L;

    private List<String> colOptName;
    private List<String> colOptStatCd;
}
