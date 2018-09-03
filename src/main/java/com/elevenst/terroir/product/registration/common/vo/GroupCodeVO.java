package com.elevenst.terroir.product.registration.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupCodeVO implements Serializable{
    private String groupCode;
    private String groupCodeName;
    private List<CodeVO> codes;
}
