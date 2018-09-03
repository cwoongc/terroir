package com.elevenst.terroir.product.registration.editor.vo;

import com.elevenst.common.properties.CommonUploadProperties;
import com.elevenst.terroir.product.registration.editor.entity.DpCpeditorFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
public class EditorImageVO implements Serializable {

    private static final long serialVersionUID = -1L;


    /**
     * 이미지 파일번호
     */
    @ApiModelProperty(value = "이미지 파일 번호 (DP_CPEDITOR_FILE.FID, PK)", required = true)
    private long fid;

    /**
     * 이미지 파일명
     */
    @ApiModelProperty(value = "이미지 파일 명", required = true)
    private String fileNm;

    /**
     * 파일링크
     */
    @ApiModelProperty(value = "이미지 파일 URL (URL Encoded)", required = true)
    private String fileLink;

    /**
     * 이미지 파일 사이즈
     */
    @ApiModelProperty(value = "파일 사이즈 (bytes)", required = true)
    private String fileSize;

    /**
     * 유효여부
     */
    @ApiModelProperty(value = "유효여부", required = true)
    private boolean valid;


    public EditorImageVO() {
    }

    public EditorImageVO(long fid, String fileNm, String fileLink, String fileSize, boolean valid) {
        this.fid = fid;
        this.fileNm = fileNm;
        this.fileLink = fileLink;
        this.fileSize = fileSize;
        this.valid = valid;
    }



    public static EditorImageVO convertFromDpCpeditorFile(DpCpeditorFile entity, boolean valid, CommonUploadProperties commonUploadProperties) throws UnsupportedEncodingException{
        return new EditorImageVO(
                entity.getFid(),
                entity.getFileNm(),
                URLEncoder.encode((commonUploadProperties.getUrl() + entity.getFileUrl() ).trim(), "UTF-8"),
                entity.getFileSize(),
                valid
        );
        /*
        this.fid = fid;
        this.fileNm = fileNm;
        this.fileLink = fileLink;
        this.fileSize = fileSize;
        this.valid = valid;
        */
    }

}
