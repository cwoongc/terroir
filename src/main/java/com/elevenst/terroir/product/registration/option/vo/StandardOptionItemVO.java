package com.elevenst.terroir.product.registration.option.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class StandardOptionItemVO implements Serializable {
    private static final long serialVersionUID = 3509359393506445217L;

    private long stdOptNo;
    private String stdOptNm;
    private String useYn;
    private long createNo;
    private Date createDt;
    private long updateNo;
    private Date updateDt;

    private PdOptItemVO productOptItemVO;
    private StandardOptionGroupMappingVO stdOptGrpMappVO;
    private List<StandardOptionValueVO> stdOptValList;
}
