package com.elevenst.terroir.product.registration.help.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HelpVO implements Serializable {
    private static final long serialVersionUID = -1362330877922613888L;

    private String subject;
    private String content;
    private String code;
    private String menuNo;			// 메뉴번호
    private String hememoNo;		// 번호
    private String heFilename;		// 업로드파일명
}
