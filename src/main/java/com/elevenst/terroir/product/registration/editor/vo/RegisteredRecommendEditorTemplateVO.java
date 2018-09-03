package com.elevenst.terroir.product.registration.editor.vo;

import com.elevenst.terroir.product.registration.editor.entity.PdRecmEdtrTmplt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisteredRecommendEditorTemplateVO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * EditorTemplate 번호
     */
    @ApiModelProperty(value = "등록된 추천 에디터 템플릿 번호", required = true)
    private Long editorTemplateNo;

    public RegisteredRecommendEditorTemplateVO() {
    }

    public RegisteredRecommendEditorTemplateVO(Long editorTemplateNo) {
        this.editorTemplateNo = editorTemplateNo;
    }
}
