package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StandardOptionInfoVO implements Serializable {
    private static final long serialVersionUID = 7073165902895322296L;

    List<StandardOptionItemVO> stdOptItemList;
    List<PdOptItemVO> prdOptItemList;
    List<PdOptItemVO> prdOptCustList;
}
