package com.elevenst.terroir.product.registration.editor.vo;

import com.elevenst.terroir.product.registration.editor.entity.PdRecmEdtrTmplt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendEditorTemplateVO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * EditorTemplate 번호
     */
    @ApiModelProperty(value = "추천 에디터 템플릿 번호")
    private Long editorTemplateNo;

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
    @ApiModelProperty(value = "섬네일 이미지 URL", required = true, example = "http://atariage.com/forums/uploads/monthly_04_2012/post-5757-0-30426700-1333983257_thumb.jpg")
    private String thumbnailImgUrl;

    /**
     * 사용 여부
     */
    @ApiModelProperty(value = "추천 에디터 템플릿 사용여부", required = true, example = "Y")
    private String useYn;

    /**
     * 생성일시
     */
    @ApiModelProperty(value = "생성일시")
    private String createDt;

    /**
     * 생성자
     */
    @ApiModelProperty(value = "생성자")
    private Long createNo;

    /**
     * 수정일시
     */
    @ApiModelProperty(value = "수정일시")
    private String updateDt;

    /**
     * 수정자
     */
    @ApiModelProperty(value = "수정자")
    private Long updateNo;


    public RecommendEditorTemplateVO() {
    }

    public RecommendEditorTemplateVO(Long editorTemplateNo, String editorTemplateName, String editorTemplateContent, String thumbnailImgUrl, String useYn, String createDt, Long createNo, String updateDt, Long updateNo) {
        this.editorTemplateNo = editorTemplateNo;
        this.editorTemplateName = editorTemplateName;
        this.editorTemplateContent = editorTemplateContent;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.useYn = useYn;
        this.createDt = createDt;
        this.createNo = createNo;
        this.updateDt = updateDt;
        this.updateNo = updateNo;
    }

    public PdRecmEdtrTmplt convertToPdRecmEdtrTmplt() {
        return new PdRecmEdtrTmplt(
                this.editorTemplateNo,
                this.editorTemplateName,
                this.editorTemplateContent,
                this.thumbnailImgUrl,
                this.useYn,
                this.createDt,
                this.createNo,
                this.updateDt,
                this.updateNo
        );
    }

    public static RecommendEditorTemplateVO convertFromPdRecmEdtrTmplt(PdRecmEdtrTmplt pdRecmEdtrTmplt) {
        return new RecommendEditorTemplateVO(
                pdRecmEdtrTmplt.getRecmEdtrTmpltNo(),
                pdRecmEdtrTmplt.getRecmEdtrTmpltNm(),
                pdRecmEdtrTmplt.getRecmEdtrTmpltCont(),
                pdRecmEdtrTmplt.getThumImgUrl(),
                pdRecmEdtrTmplt.getUseYn(),
                pdRecmEdtrTmplt.getCreateDt(),
                pdRecmEdtrTmplt.getCreateNo(),
                pdRecmEdtrTmplt.getUpdateDt(),
                pdRecmEdtrTmplt.getUpdateNo()
        );
    }

}
