package com.elevenst.terroir.product.registration.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductLdngTmGson implements Serializable {
    private static final long serialVersionUID = -757259459911563317L;
    public String browTyp;
    public String procTyp;
    public float ldngTm;
}
