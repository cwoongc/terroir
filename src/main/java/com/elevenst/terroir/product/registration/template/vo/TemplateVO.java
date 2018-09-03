package com.elevenst.terroir.product.registration.template.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateVO implements Serializable {
    ProductInformationTemplateVO productInformationTemplateVO;
    InventoryVO inventoryVO;
}
