package com.elevenst.terroir.product.registration.editor.vo;

import com.elevenst.terroir.product.registration.editor.entity.PdRecmEdtrTmplt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendEditorTemplateRegisterVO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * EditorTemplate 이름
     */
    @ApiModelProperty(value = "추천 에디터 템플릿 명", required = true, example = "추천 에디터 템플릿 명")
    private String editorTemplateName;

    /**
     * EditorTemplate 컨텐츠
     */
    @ApiModelProperty(value = "추천 에디터 템플릿 콘텐츠", required = true, example = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>추천 템플릿</title></head><body><h1>추천템플릿</h1></body></html>")
    private String editorTemplateContent;


    /**
     * 섬네일 이미지 URL
     */
    @ApiModelProperty(value = "섬네일 이미지 URL", required = true)
    private String thumbnailImgUrl;

    /**
     * 사용 여부
     */
    @ApiModelProperty(value = "추천 에디터 템플릿 사용여부", required = true, example = "Y")
    private String useYn;


    /**
     * 생성자
     */
    @ApiModelProperty(value = "생성자", hidden = true)
    private Long createNo;

    /**
     * 수정자
     */
    @ApiModelProperty(value = "수정자", hidden = true)
    private Long updateNo;



    public RecommendEditorTemplateRegisterVO() {
    }

    public RecommendEditorTemplateRegisterVO(String editorTemplateName, String editorTemplateContent, String thumbnailImgUrl, String useYn) {
        this.editorTemplateName = editorTemplateName;
        this.editorTemplateContent = editorTemplateContent;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.useYn = useYn;
    }

    public PdRecmEdtrTmplt convertToPdRecmEdtrTmplt(long recmEdtrTmpltNo) {
        return new PdRecmEdtrTmplt(
                recmEdtrTmpltNo,
                this.editorTemplateName,
                this.editorTemplateContent,
                this.thumbnailImgUrl,
                this.useYn,
                null,
                this.createNo,
                null,
                this.updateNo
        );
    }



}
