package com.elevenst.terroir.product.registration.editor.service;

import com.elevenst.common.properties.CommonUploadProperties;
import com.elevenst.common.properties.ImgUploadProperties;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.FileUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.property.FileProperty;
import com.elevenst.terroir.product.registration.editor.entity.DpCpeditorFile;
import com.elevenst.terroir.product.registration.editor.entity.PdRecmEdtrTmplt;
import com.elevenst.terroir.product.registration.editor.exception.EditorServiceException;
import com.elevenst.terroir.product.registration.editor.mapper.EditorMapper;
import com.elevenst.terroir.product.registration.editor.message.EditorServiceExceptionMessage;
import com.elevenst.terroir.product.registration.editor.validate.EditorServiceValidate;
import com.elevenst.terroir.product.registration.editor.vo.EditorImageVO;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateRegisterVO;
import com.elevenst.terroir.product.registration.editor.vo.RecommendEditorTemplateVO;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.validate.ProductImageValidate;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Service
public class EditorServiceImpl {


    private boolean isMock = false;

    @Autowired
    EditorServiceValidate editorServiceValidate;

    @Autowired
    EditorMapper editorMapper;

    @Autowired
    ImgUploadProperties imgUploadProperties;

    @Autowired
    CommonUploadProperties commonUploadProperties;

    @Autowired
    private ProductImageValidate productImageValidate;

    @Autowired
    private FileProperty fileProperty;

    public final String FILE_SEPERATOR = System.getProperty("file.separator");

    public final String PREFIX_TEMP_PATH = "editorImg";


    /**
     * 추천 에디터 템플릿 목록 조회
     * @return 추천 에디터 템플릿 목록
     */
    public List<RecommendEditorTemplateVO> selectRecommededTemplates() {

        List<RecommendEditorTemplateVO> recommendEditorTemplateVOS = null;


        if(isMock) {
            return getMockRecommendedTemplates();
        } else {

            try {

                List<PdRecmEdtrTmplt> pdRecmEdtrTmplts = editorMapper.selectRecommandEditorTemplates();

                recommendEditorTemplateVOS = new ArrayList<>();

                for(PdRecmEdtrTmplt pdRecmEdtrTmplt : pdRecmEdtrTmplts) {
                    recommendEditorTemplateVOS.add(RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(pdRecmEdtrTmplt));
                }

            } catch (Exception ex) {
                EditorServiceException threx = new EditorServiceException(EditorServiceExceptionMessage.RECOMMENDED_TEMPLATES_SELECT_ERROR, ex);
                if (log.isErrorEnabled()) {
                    log.error(threx.getMessage(), threx);
                }
                throw threx;
            }

        }

        return recommendEditorTemplateVOS;

    }

    private List<RecommendEditorTemplateVO> getMockRecommendedTemplates() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date  now = new Date();


        PdRecmEdtrTmplt entity1 = new PdRecmEdtrTmplt(
                1001L,
                "추천 템플릿 1",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 1</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://http://fragstore.com/images/thumbnails/260/260/detailed/1/Na%E2%80%99Vi_T-Shirt_Calligraphy_Black_front.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0
        );

        PdRecmEdtrTmplt entity2 = new PdRecmEdtrTmplt(
                1002L,
                "추천 템플릿 2",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 2</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://http://fragstore.com/images/thumbnails/260/260/detailed/1/Na%E2%80%99Vi_T-Shirt_Calligraphy_Black_front.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0

        );

        PdRecmEdtrTmplt entity3 = new PdRecmEdtrTmplt(
                1003L,
                "추천 템플릿 3",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 3</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://atariage.com/forums/uploads/monthly_04_2012/post-5757-0-30426700-1333983257_thumb.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0

        );

        List<RecommendEditorTemplateVO> vos = Arrays.asList(RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity1),
                RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity2),
                RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity3)
        );

        return vos;

    }


    /**
     * 추천 에디터 템플릿 조회
     * @param editorTemplateNo 추천 에디터 템플릿 번호 (PK, SEQUENCE)
     * @return 추천 에디터 템플릿
     */
    public RecommendEditorTemplateVO selectRecommededTemplate(Long editorTemplateNo) {

        RecommendEditorTemplateVO recommendEditorTemplateVO = null;

        if(isMock) {

            return getMockRecommendedTemplate(editorTemplateNo);
        } else {

            try {
                PdRecmEdtrTmplt pdRecmEdtrTmplt = editorMapper.selectRecommandEditorTemplate(editorTemplateNo);

                if(pdRecmEdtrTmplt != null) recommendEditorTemplateVO = RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(pdRecmEdtrTmplt);

            } catch (Exception ex) {
                EditorServiceException threx = new EditorServiceException(EditorServiceExceptionMessage.RECOMMENDED_TEMPLATE_SELECT_ERROR, ex);
                if (log.isErrorEnabled()) {
                    log.error(threx.getMessage(), threx);
                }
                throw threx;
            }
        }

        return  recommendEditorTemplateVO;
    }

    private RecommendEditorTemplateVO getMockRecommendedTemplate(Long editorTemplateNo) {
        RecommendEditorTemplateVO recommendEditorTemplateVO = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date  now = new Date();



        PdRecmEdtrTmplt entity1 = new PdRecmEdtrTmplt(
                1001L,
                "추천 템플릿 1",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 1</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://atariage.com/forums/uploads/monthly_04_2012/post-5757-0-30426700-1333983257_thumb.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0
        );

        PdRecmEdtrTmplt entity2 = new PdRecmEdtrTmplt(
                1002L,
                "추천 템플릿 2",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 2</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://atariage.com/forums/uploads/monthly_04_2012/post-5757-0-30426700-1333983257_thumb.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0

        );

        PdRecmEdtrTmplt entity3 = new PdRecmEdtrTmplt(
                1003L,
                "추천 템플릿 3",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>추천 템플릿 3</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>",
                "http://atariage.com/forums/uploads/monthly_04_2012/post-5757-0-30426700-1333983257_thumb.jpg",
                "Y",
                sdf.format(now),
                0,
                sdf.format(now),
                0

        );

        List<RecommendEditorTemplateVO> vos = Arrays.asList(RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity1),
                RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity2),
                RecommendEditorTemplateVO.convertFromPdRecmEdtrTmplt(entity3)
        );

        for(RecommendEditorTemplateVO vo : vos) {
            if(vo.getEditorTemplateNo().equals(editorTemplateNo)) {

                recommendEditorTemplateVO = vo;
                break;

            }
        }

        return recommendEditorTemplateVO;
    }


    /**
     * 추천 에디터 템플릿 등록
     * @param recommendEditorTemplateRegisterVO
     * @return 등록된 추천 에디터 템플릿 번호 (PK, SEQUENCE)
     * @throws EditorServiceException
     */
    public long insertRecommandedEditorTemplate(RecommendEditorTemplateRegisterVO recommendEditorTemplateRegisterVO) throws EditorServiceException {

        long recmEdtrTmpltNo = 0;

        try {

//            editorServiceValidate.validateInsert();

            recmEdtrTmpltNo = editorMapper.createRecommandEditorTemplateNo();

            editorMapper.insertRecommandEditorTemplate(recommendEditorTemplateRegisterVO.convertToPdRecmEdtrTmplt(recmEdtrTmpltNo));

        } catch (Exception ex) {
            EditorServiceException threx = new EditorServiceException(EditorServiceExceptionMessage.RECOMMENDED_TEMPLATE_INSERT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return recmEdtrTmpltNo;
    }


    /**
     * 에디터 이미지 파일 저장
     * @param multipartFile 멀티파트파일
     * @param memNo 회원번호
     * @param uploadPath editorImg
     * @return
     * @throws EditorServiceException
     */
    public EditorImageVO saveEditorImageFile(MultipartFile multipartFile, long memNo, String uploadPath) throws EditorServiceException {

        boolean isValid = false;

        InputStream is = null;
        OutputStream os = null;
        DpCpeditorFile dpCpeditorFile = null;
        EditorImageVO editorImageVO = null;

        try {

            String saveDirectory = imgUploadProperties.getServer().getPath() + "/" + uploadPath;
            String savedFileName = "";
            String savedFilePath = "";

            if(multipartFile != null && saveDirectory != null) {

                File uploadDir = new File(saveDirectory);

                if(!uploadDir.exists()) uploadDir.mkdirs();

                File uploadFile = new File(uploadDir, multipartFile.getOriginalFilename());
                uploadFile = this.rename(uploadFile, memNo);

                editorServiceValidate.validateUploadImageFileName(uploadFile.getName());

                is = multipartFile.getInputStream();
                os = new BufferedOutputStream(new FileOutputStream(uploadFile));

                int read = 0;
                while((read = is.read()) != -1) {
                    os.write(read);
                }

                os.flush();

                savedFileName = uploadFile.getName();
                savedFilePath = uploadFile.getParent();

                editorServiceValidate.validateUploadedImageFile(uploadFile);

                String curTime = DateUtil.formatDate("yyyyMMddHHmmddssS");

                int idx = savedFileName.lastIndexOf(".");
                String extention = "";
                if(idx != -1) {
                    extention = savedFileName.substring(idx);
                }
                String newFileName = curTime + extention;

                if(uploadFile.renameTo(new File(savedFilePath+ System.getProperty("file.separator") + newFileName))) {
                    if(log.isDebugEnabled()) {
                        log.debug("true");
                        log.debug(uploadFile.getPath());
                    }
                } else {
                    if(log.isDebugEnabled()) {
                        log.debug("false");
                        log.debug(uploadFile.getPath());

                    }

                }


                int stPos  = savedFilePath.lastIndexOf(uploadPath);
                String savedFileUrl = savedFilePath.substring(stPos - 1);

                savedFileName = newFileName;
                if(log.isDebugEnabled()) {
                    log.debug("저장된 파일명:" + savedFilePath+ System.getProperty("file.separator") + newFileName);
                    log.debug("FULL PATH :" + savedFilePath);
                }

                dpCpeditorFile = new DpCpeditorFile();

                dpCpeditorFile.setFileType("image");
                dpCpeditorFile.setFileNm(savedFileName);
                dpCpeditorFile.setFileSize(Long.toString(multipartFile.getSize()));
                dpCpeditorFile.setFilePath(savedFilePath);
                dpCpeditorFile.setFileUrl(savedFileUrl);


                // 파일 정보 DB 에 저장
                long fid = this.selectFid();
                dpCpeditorFile.setFid(fid);
                dpCpeditorFile.setCreateNo(memNo);

                editorMapper.insertUploadedEditorImageFileInfo(dpCpeditorFile);

                isValid = true;

                editorImageVO = EditorImageVO.convertFromDpCpeditorFile(dpCpeditorFile,isValid,commonUploadProperties);


            }
        } catch (Exception ex) {
            EditorServiceException threx = new EditorServiceException(EditorServiceExceptionMessage.EDITOR_IMAGE_SAVE_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return editorImageVO;
    }


    /**
     * 에디터 이미지 파일 저장
     * @param imageByte 파일 데이터
     * @param memNo 회원번호
     * @param imageKind 이미지 유형
     * @return
     * @throws EditorServiceException
     */
    public EditorImageVO saveEditorImageFileByte(byte [] imageByte, String fileName, long memNo, ImageKind imageKind) throws EditorServiceException , IOException{

        StringJoiner joiner = new StringJoiner(FILE_SEPERATOR);
        joiner.add(fileProperty.getUploadPath())
                .add(PREFIX_TEMP_PATH)
                .add(DateUtil.getFormatString("yyyyMMdd"))
                .add(String.valueOf(memNo));
        String tempPath = joiner.toString();
        String filePath = tempPath+"/"+String.valueOf(new Date().getTime())+"_"+imageKind.name()+"."+FileUtil.getFileExt(fileName);
        String retPath = "/"+PREFIX_TEMP_PATH+FILE_SEPERATOR+DateUtil.getFormatString("yyyyMMdd")+FILE_SEPERATOR+String.valueOf(memNo)+FILE_SEPERATOR+String.valueOf(new Date().getTime())+"_"+imageKind.name()+"."+FileUtil.getFileExt(fileName);

        File tempDir = new File(tempPath);
        FileUtils.forceMkdir(tempDir);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, FileUtil.getFileExt(fileName), new File(filePath)); //저장하고자 하는 파일 경로를 입력합니다


        productImageValidate.checkImageSize(tempDir.length(), imageKind);  //췤 용량체크 없어질듯?
        productImageValidate.checkImageValidation(filePath);
        productImageValidate.checkImageWH(new ImageIcon(filePath), imageKind, memNo, CreateCdTypes.MO_SIMPLEREG.getDtlsCd());

        DpCpeditorFile dpCpeditorFile = new DpCpeditorFile();

        dpCpeditorFile.setFileType("image");
        dpCpeditorFile.setFileNm(fileName);
        dpCpeditorFile.setFileSize(Long.toString(imageByte.length));
        dpCpeditorFile.setFilePath(new File(tempPath).getPath());
        dpCpeditorFile.setFileUrl(retPath);


        // 파일 정보 DB 에 저장
        /*
        long fid = this.selectFid();
        dpCpeditorFile.setFid(fid);
        dpCpeditorFile.setCreateNo(memNo);

        editorMapper.insertUploadedEditorImageFileInfo(dpCpeditorFile);
        */

        boolean isValid = true;

        EditorImageVO editorImageVO = EditorImageVO.convertFromDpCpeditorFile(dpCpeditorFile,isValid,commonUploadProperties);

        return editorImageVO;
    }

    /**
     * DP_CPEDITOR_FILE.FID의 SEQUENCE 채번
     * @return 채번된 DP_CPEDITOR_FILE.FID의 SEQUENCE
     * @throws EditorServiceException
     */
    public long selectFid() throws EditorServiceException {
        long fid = 0L;

        try {
            fid = editorMapper.selectFid();
        } catch (Exception ex) {
            EditorServiceException threx = new EditorServiceException(EditorServiceExceptionMessage.EDITOR_IMAGE_FID_SELECT_ERROR, ex);
            if (log.isErrorEnabled()) {
                log.error(threx.getMessage(), threx);
            }
            throw threx;
        }

        return fid;
    }


    private File rename(File srcFile, Long memNo) {
        if (srcFile == null) {
            return null;
        }

        File newFle = new File(srcFile.getParent()+System.getProperty("file.separator")
                +DateUtil.formatDate("yyyy"
                + System.getProperty("file.separator") + "MM"
                + System.getProperty("file.separator") + "dd")
                + System.getProperty("file.separator")
                + (memNo != null ? memNo : "")
                , srcFile.getName());

        File makeDirs = new File(newFle.getParent());
        if(!makeDirs.exists()) {
            makeDirs.mkdirs();
        }

        // 원하는 이름으로 파일을 생성할 수 있다면, 파일명 변경 없이 돌아가도록 한다 .
        if (createNewFile(newFle)) {
            return newFle;
        }
        String name = newFle.getName();
        String body = null;
        String ext = null;

        int dot = name.lastIndexOf(".");
        if (dot != -1) {
            body = name.substring(0, dot);
            ext = name.substring(dot); // includes "."
        }
        else {
            body = name;
            ext = "";
        }

        // Increase the count until an empty spot is found.
        // Max out at 9999 to avoid an infinite loop caused by a persistent
        // IOException, like when the destination dir becomes non-writable.
        // We don't pass the exception up because our job is just to rename,
        // and the caller will hit any IOException in normal processing.
        int count = 0;
        while (!createNewFile(newFle) && count < 9999) {
            count++;
            String newName = body + count + ext;
            newFle = new File(newFle.getParent(), newName);
        }

        return newFle;

    }

    private boolean createNewFile(File f) {
        try {
            return f.createNewFile();
        } catch (IOException var3) {
            return false;
        }
    }

    private boolean isIgnoreFileName(String fileName) {
        try {
            if (StringUtil.isNotEmpty(fileName)) {
                String fileNameExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                if (StringUtil.isNotEmpty(fileNameExtension) && "aspx,asp,php,jsp,html,cgi,perl,sh,exe".contains(fileNameExtension.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return false;
    }


}
