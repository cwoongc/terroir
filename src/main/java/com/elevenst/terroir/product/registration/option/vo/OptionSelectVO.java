package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionSelectVO implements Serializable {
    private static final long serialVersionUID = -3984611985383483797L;

    private String name;
    private List<String> optionValueList;
}
