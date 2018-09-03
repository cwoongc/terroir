package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddTemplateVO implements Serializable {
    ProductInformationTemplateVO productInformationTemplateVO;
    List listAddProd = new ArrayList();
}
