package com.elevenst.terroir.product.registration.editor.controller;

import com.elevenst.common.properties.CommonUploadProperties;
import com.elevenst.terroir.product.registration.editor.exception.EditorControllerException;
import com.elevenst.terroir.product.registration.editor.exception.EditorServiceException;
import com.elevenst.terroir.product.registration.editor.exception.EditorServiceValidateException;
import com.elevenst.terroir.product.registration.editor.message.EditorControllerExceptionMessage;
import com.elevenst.terroir.product.registration.editor.service.EditorServiceImpl;
import com.elevenst.terroir.product.registration.editor.validate.EditorControllerValidate;
import com.elevenst.terroir.product.registration.editor.vo.EditorImageVO;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateRegisterVO;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateVO;
import com.elevenst.terroir.product.registration.editor.vo.RegisteredRecommendEditorTemplateVO;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skt.tmall.auth.Auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(
        description = "상품상세 에디터 API"
)
@Slf4j
@RestController
@RequestMapping(value="/editor")
public class EditorController {

    @Autowired
    private EditorServiceImpl editorService;

    @Autowired
    private EditorControllerValidate editorControllerValidate;

    @Autowired
    ProductImageServiceImpl productImageService;




    @ApiOperation(
            value = "추천 에디터 템플릿 목록 조회",
            response = RecommendEditorTemplateVO.class,
            responseContainer = "List"
    )
    @GetMapping(value = "/template/recommended", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RecommendEditorTemplateVO>> getRecommendedTemplates() {

        List<RecommendEditorTemplateVO> recommendEditorTemplateVOS;

        try {

            recommendEditorTemplateVOS = editorService.selectRecommededTemplates();

        } catch (Exception ex) {
            EditorControllerException threx = new EditorControllerException(EditorControllerExceptionMessage.RECOMMENDED_EDITOR_TEMPLATES_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        ResponseEntity<List<RecommendEditorTemplateVO>> re;
        if(recommendEditorTemplateVOS == null)
            re = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            re = new ResponseEntity<>(recommendEditorTemplateVOS, HttpStatus.OK);

        return re;
    }

    @ApiOperation(

            value = "추천 에디터 템플릿 조회",
            response = RecommendEditorTemplateVO.class
    )
    @GetMapping(value = "/template/recommended/{editorTemplateNo}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RecommendEditorTemplateVO> getRecommendedTemplate(
            @ApiParam("추천 에디터 템플릿 번호") @PathVariable Long editorTemplateNo
    ) {

        RecommendEditorTemplateVO recommendEditorTemplateVO;

        try {

            recommendEditorTemplateVO = editorService.selectRecommededTemplate(editorTemplateNo);

        } catch (Exception ex) {
            EditorControllerException threx = new EditorControllerException(EditorControllerExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_GET_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        ResponseEntity<RecommendEditorTemplateVO> re;
        if(recommendEditorTemplateVO == null)
            re = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            re = new ResponseEntity<>(recommendEditorTemplateVO, HttpStatus.OK);

        return re;
    }



    @ApiOperation(
            value = "추천 에디터 템플릿 등록",
            response = RegisteredRecommendEditorTemplateVO.class
    )
    @ApiResponses(value={
            @ApiResponse(code = 201, message = "추천 에디터 템플릿 등록 성공")
    })
    @PostMapping(value = "/template/recommended", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RegisteredRecommendEditorTemplateVO> registerRecommendEditorTemplate(
            @RequestBody RecommendEditorTemplateRegisterVO recommendEditorTemplateRegisterVO,
            Auth auth) {
        Long recommendEditorTemplateNo = null;


        long memNo = 0;
        if(auth.isAuth()) {
            memNo = auth.getMemberNumber();
        }

        try {

            recommendEditorTemplateRegisterVO.setCreateNo(memNo);
            recommendEditorTemplateRegisterVO.setUpdateNo(memNo);

            editorControllerValidate.validateRegisterRecommendEditorTemplate(recommendEditorTemplateRegisterVO);

            recommendEditorTemplateNo = editorService.insertRecommandedEditorTemplate(recommendEditorTemplateRegisterVO);
        } catch (Exception ex) {
            EditorControllerException threx = new EditorControllerException(EditorControllerExceptionMessage.RECOMMENDED_EDITOR_TEMPLATE_REGISTER_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        RegisteredRecommendEditorTemplateVO model = new RegisteredRecommendEditorTemplateVO(recommendEditorTemplateNo);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


    @ApiOperation(
            value = "에디터 이미지 저장",
            response = EditorImageVO.class
    )
    @ApiResponses(value={
            @ApiResponse(code = 201, message = "에디터 이미지 저장 성공")
    })
    @PostMapping(value = "/image" )
    public ResponseEntity<EditorImageVO> registerEditorTemplateImage(
            @RequestParam("fileName") String fileName,
            @RequestParam("imageKind") String imageKind,
            @RequestBody String files , Auth auth ) {
        long memNo = 0;
        if(auth.isAuth()) {
            memNo = auth.getMemberNumber();
        }

        String uploadPath = "editorImg";

        EditorImageVO editorImageVO = null;

        try {
            byte[] imageByte= productImageService.decodeBase64ToBytes(files);
            //productImageService.uploadTempImageByte(imagByte, fileName, ImageKind.valueOf(imageKind), selMnbdNo);
            editorImageVO = editorService.saveEditorImageFileByte(imageByte, fileName, memNo, ImageKind.valueOf(imageKind));
        } catch (Exception ex) {
            ex.printStackTrace();
            String validateMsg = "";

            if(ex instanceof EditorServiceException) {
                EditorServiceException esex = (EditorServiceException)ex;

                Throwable cause = esex.getCause();

                if(cause != null &&  cause instanceof EditorServiceValidateException) {
                    EditorServiceValidateException esvex = (EditorServiceValidateException) cause;
                    validateMsg = cause.getMessage();
                }
            }


            EditorControllerException threx = new EditorControllerException(String.format(EditorControllerExceptionMessage.EDITOR_IMAGE_SAVE_ERROR,validateMsg), ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }


            if (!validateMsg.isEmpty()) threx = new EditorControllerException(validateMsg.substring(validateMsg.lastIndexOf("]")+1), ex, 0);

            throw threx;
        }


        return new ResponseEntity<>(editorImageVO, HttpStatus.CREATED);
    }



}


