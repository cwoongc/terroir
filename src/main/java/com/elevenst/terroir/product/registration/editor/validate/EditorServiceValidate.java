package com.elevenst.terroir.product.registration.editor.validate;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.editor.exception.EditorServiceValidateException;
import com.elevenst.terroir.product.registration.editor.message.EditorServiceValidateExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
@Slf4j
public class EditorServiceValidate {

    public void validateUploadImageFileName(String fileName) {
        if (StringUtil.isNotEmpty(fileName)) {
            String fileNameExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (StringUtil.isNotEmpty(fileNameExtension) && !"aspx,asp,php,jsp,html,cgi,perl,sh,exe".contains(fileNameExtension.toLowerCase())) {
                return;
            }
        }

        throwException(EditorServiceValidateExceptionMessage.NOT_ALLOWED_EDITOR_TEMPLATE_IMAGE_FILE_NAME_ERROR);
    }

    public void validateUploadedImageFile(File file) {
        if(isUploadedFileImageFormat(file)) {
            return;
        } else {
            throwException(EditorServiceValidateExceptionMessage.UPLOADED_EDITOR_TEMPLATE_IMAGE_FILE_IS_INVALID);
        }
    }

    private boolean isUploadedFileImageFormat(File file) {
//        String fileName = file.getPath() + "/" + file.getName();
        String fileName = file.getName();

        try {
            int index = fileName.toLowerCase().lastIndexOf(".");

            if (index != -1) {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

                int size = bis.available();

                //0byte 검사 및 파일형식 검사
                if( size>0 ) {
                    byte[] data = new byte[size];
                    bis.read(data);
                    bis.close();
                    bis = null;

                    String ext = fileName.substring(index + 1).toLowerCase().trim();

                    //------- PNG
                    if (ext.equals("png")) {
                        if((data[1] == 0x50) && (data[2] == 0x4E) && (data[3] == 0x47))
                            return true;

                        //------- JFIF
                    }else if(ext.equals("jpg") || (ext.equals("jpeg"))) {
                        if(((data[6] == 0x4A) && (data[7] == 0x46) && (data[8] == 0x49) && (data[9] == 0x46)) || ((data[0] == (byte)0xFF) && (data[1] == (byte)0xD8)))
                            return true;
                            // Exif - Exchangeable Image File Format
                        else if((data[6] == 0x45) && (data[7] == 0x78) && (data[8] == 0x69) && (data[9] == 0x66) || ((data[0] ==(byte)0xFF) && (data[1] == (byte)0xD8)))
                            return true;
                        else if((data[0] == 0x47) && (data[1] == 0x49) && (data[2] == 0x46))
                            return true;

                        //------- GIF
                    }else if(ext.equals("gif")) {
                        if((data[0] == 0x47) && (data[1] == 0x49) && (data[2] == 0x46))
                            return true;

                        //------- SWF
                    }else if(ext.equals("swf")) {
                        if((data[0] == 0x43) && (data[1] == 0x57) && (data[2] == 0x53))
                            return true;

                        //------- BMP
                    }else if(ext.equals("bmp")) {
                        if((data[0] == 0x42) && (data[1] == 0x4D))
                            return true;

                        //------- TIF
                    }else if(ext.equals("tif")) {
                        if((data[0] == 0x49) && (data[1] == 0x49) && (data[2] == 0x2A))
                            return true;

                        //------- ICO
                    }else if(ext.equals("ico")) {
                        if((data[0] == 0x00) && (data[1] == 0x00) && (data[2] == 0x01))
                            return true;

                        //------- FLV
                    }else if(ext.equals("flv")) {
                        if((data[0] == 0x46) && (data[1] == 0x4C) && (data[2] == 0x56))
                            return true;

                        //------- FLA
                    }else if(ext.equals("fla")) {
                        if((data[0] == 0xD0) && (data[1] == 0xCF) && (data[2] == 0x11) && (data[3] == 0xE0) && (data[4] == 0xA1) && (data[4] == 0xB1))
                            return true;
                    }//if
                }//if
            }//if
        }
        catch (IOException e) {
            log.error("이미지 체크 오류",e);
        }
        catch (Exception e) {
            log.error("이미지 체크 오류",e);
        }

        //상기에서 이미지파일이 이상이 있을경우 현재 라인까지 진행이 되므로 업로드된 파일을 삭제 후 false를 반환한다
        file.delete();
        file = null;

        return false;
    }



    private void throwException(String message) {
        EditorServiceValidateException threx = new EditorServiceValidateException(message);
        if(log.isErrorEnabled()) {
            log.error(threx.getMessage(), threx);
        }
        throw threx;
    }



}
