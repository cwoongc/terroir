package com.elevenst.terroir.product.registration.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HelpPopVO implements Serializable{
        private static final long serialVersionUID = 5851546399886875440L;

        private String title;
        private String cont;
        private String hefilename;
        private String code;

}
