package com.elevenst.terroir.product.registration.editor.mapper;

import com.elevenst.terroir.product.registration.editor.entity.DpCpeditorFile;
import com.elevenst.terroir.product.registration.editor.entity.PdRecmEdtrTmplt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EditorMapper {





    /**
     *DP_CPEDITOR_FILE.FID 채번
     * @return 채번된 FID (sequence)
     */
    long selectFid();

    /**
     * 상품상세 에디터 이미지 정보 저장
     * @param dpCpeditorFile
     * @return
     */
    int insertUploadedEditorImageFileInfo(DpCpeditorFile dpCpeditorFile);


    /**
     * 상품상세 추천 에디터 템플릿 목록 조회
     * @return
     */
    List<PdRecmEdtrTmplt> selectRecommandEditorTemplates();

    /**
     * 상품상세 추천 에디터 템플릿 조회
     * @param recommendEditorTemplateNo 추천 에디터 템플릿 번호
     * @return
     */
    PdRecmEdtrTmplt selectRecommandEditorTemplate(long recommendEditorTemplateNo);


    /**
     * 상품상세 추천 에디터 템플릿 등록
     * @param pdRecmEdtrTmplt
     * @return
     */
    int insertRecommandEditorTemplate(PdRecmEdtrTmplt pdRecmEdtrTmplt);



    /**
     * 추천 에디터 템플릿 번호 채번 (PK, 시퀀스)
     * PD_RECM_EDTR_TMPLT.RECM_EDTR_TMPLT_NO
     * @return 채번된 추천 에디터 템플릿 번호
     */
    long createRecommandEditorTemplateNo();
}
