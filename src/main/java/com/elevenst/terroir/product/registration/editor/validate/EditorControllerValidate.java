package com.elevenst.terroir.product.registration.editor.validate;

import com.elevenst.terroir.product.registration.editor.exception.EditorControllerValidateException;
import com.elevenst.terroir.product.registration.editor.message.EditorControllerValidateExceptionMessage;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateRegisterVO;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EditorControllerValidate {

    public void validateRegisterRecommendEditorTemplate(RecommendEditorTemplateRegisterVO recommendEditorTemplateRegisterVO) {

        isRecommendEditorTemplateNameEmpty(recommendEditorTemplateRegisterVO.getEditorTemplateName());
        isRecommendEditorTemplateContentEmpty(recommendEditorTemplateRegisterVO.getEditorTemplateContent());
        isThumnailImgUrlEmpty(recommendEditorTemplateRegisterVO.getThumbnailImgUrl());
        isRecommendEditorTemplateUseYnEmpty(recommendEditorTemplateRegisterVO.getUseYn());
        isRecommendEditorTemplateUseYnValid(recommendEditorTemplateRegisterVO.getUseYn());
    }

    private void isThumnailImgUrlEmpty(String thumbnailImgUrl) {
        if(thumbnailImgUrl == null || thumbnailImgUrl.isEmpty())
            throwException(EditorControllerValidateExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_THUMBNAIL_IMG_URL_IS_EMPTY);

    }

    private void isRecommendEditorTemplateUseYnValid(String useYn) {
        if(useYn != null && (useYn.equalsIgnoreCase("Y") || useYn.equalsIgnoreCase("N") )) return;
        else throwException(EditorControllerValidateExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_USE_YN_IS_INVALID);
    }

    private void isRecommendEditorTemplateUseYnEmpty(String useYn) {
        if(useYn == null || useYn.isEmpty())
            throwException(EditorControllerValidateExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_USE_YN_IS_EMPTY);
    }

    private void isRecommendEditorTemplateContentEmpty(String editorTemplateContent) {
        if(editorTemplateContent == null || editorTemplateContent.isEmpty())
            throwException(EditorControllerValidateExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_CONTENT_IS_EMPTY);
    }

    private void isRecommendEditorTemplateNameEmpty(String editorTemplateName) {
        if(editorTemplateName == null || editorTemplateName.isEmpty())
            throwException(EditorControllerValidateExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_NAME_IS_EMPTY);
    }

    private void throwException(String message) {
        EditorControllerValidateException threx = new EditorControllerValidateException(message);
        if(log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }



}
