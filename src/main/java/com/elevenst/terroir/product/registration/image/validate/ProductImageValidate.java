package com.elevenst.terroir.product.registration.image.validate;

import com.elevenst.common.util.FileUtil;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.message.ImageExceptionMessage;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductImageValidate {

    private final long SINGLE_PRODUCT_LIMIT_IMAGE_SIZE = 10485760; //단일상품 이미지는 최대 10MB까지 가능하다.
    private final long LIMIT_IMAGE_SIZE = 3145728;
    private final long LIMIT_IMAGE_SIZE_MOBILE = 1572864;

    private final String SINGLE_PRODUCT_LIMIT_IMAGE_SIZE_PRINT = "10MB"; //단일상품 이미지는 최대 10MB까지 가능하다.
    private final String LIMIT_IMAGE_SIZE_PRINT = "3MB";
    private final String LIMIT_IMAGE_SIZE_MOBILE_PRINT = "1.5MB";

    private final List<String> allowExtensions = Arrays.asList("jpg", "jpeg", "png");
    private final List<String> jpgExtensions = Arrays.asList("jpg", "jpeg");

    @Autowired
    private ProductImageServiceImpl productImageService;

    @Autowired
    private SellerServiceImpl sellerService;


    public void checkImageExtension(String fileName, ImageKind imageKind) throws ProductException {
        String ext = FileUtil.getFileExt(fileName);
        if(ext.equals("")){
            throw new ProductException(ImageExceptionMessage.IMG_06);
        }
        if(ImageKind.WD == imageKind && !jpgExtensions.contains(ext)){
            throw new ProductException("모바일 상세설명 이미지는 "+ext+"확장명을 가진 이미지를 등록할 수 없습니다.");
        } else if(!allowExtensions.contains(ext)){
            throw new ProductException("상품 이미지는 "+ext+"확장명을 가진 이미지를 등록할 수 없습니다.");
        }
    }

    public void checkGifImage(String fileName) throws ProductException {
        if("gif".equals(FileUtil.getFileExt(fileName))){
            throw new ProductException(ImageExceptionMessage.IMG_02);
        }
    }

    public void checkImageSize(long size, ImageKind imageKind) throws ProductException {
        if (size < 1 ){
            throw new ProductException(ImageExceptionMessage.IMG_04);
        }



        long limitSize = LIMIT_IMAGE_SIZE;
        String errMsg = "상품 이미지는 "+LIMIT_IMAGE_SIZE_PRINT+" 이하로만 업로드 가능합니다.";

        if(imageKind == ImageKind.WD){
            limitSize = LIMIT_IMAGE_SIZE_MOBILE;
            errMsg = "모바일 상세설명 이미지는 "+LIMIT_IMAGE_SIZE_MOBILE_PRINT+" 이하로만 업로드 가능합니다.";
        } else if(imageKind == ImageKind.D){
            errMsg = "쇼킹딜 대표이미지는 "+LIMIT_IMAGE_SIZE_PRINT+" 이하로만 업로드 가능합니다.";
        }

        System.out.println("======> size : "+size + "limitSize : "+limitSize);

        if(size > limitSize) {
            throw new ProductException(errMsg);
        }
    }

    public void checkImageWH(ImageIcon imageIcon, ImageKind imageKind, long userNo, String createCd) throws ProductException {
        if(imageIcon == null) {
            throw new ProductException(ImageExceptionMessage.IMG_01);
        }

        if(imageKind == ImageKind.G) {
            int giftImageSizeLimit = 80;
            if(imageIcon.getIconWidth() != giftImageSizeLimit  || imageIcon.getIconHeight() != giftImageSizeLimit){
            //    throw new ProductException("사은품이미지는 반드시 "+giftImageSizeLimit+"X"+giftImageSizeLimit+" 으로 등록해주세요.");
            }
        }else {
            if(imageKind == ImageKind.WD) {
                if(imageIcon.getIconWidth() > 780){
                    throw new ProductException(ImageExceptionMessage.IMG_05);
                }
            } else if(imageKind == ImageKind.D){
                if(imageIcon.getIconWidth()!= 720 || imageIcon.getIconHeight()!=360){
                    throw new ProductException(ImageExceptionMessage.IMG_07);
                }
            }else if(imageKind == ImageKind.E){

            } else {

                if(sellerService.isFreeImgSeller(userNo)){
                    return;
                }
                int zoomWidthLimit = 600;
                int zoomHeightLimit = 600;
                if("0100".equals(createCd)) {
                    zoomWidthLimit = 300;
                    zoomHeightLimit = 300;
                }
                if(imageIcon.getIconWidth() < zoomWidthLimit || imageIcon.getIconHeight() < zoomHeightLimit){
                    throw new ProductException("상품이미지는 반드시 "+zoomWidthLimit+"X"+zoomHeightLimit+" 사이즈 이상으로 등록해주세요.");
                }

            }
        }
    }

    public void checkImageWH(String filePath, ImageKind imageKind, long userNo, String createCd) throws ProductException{
        checkImageWH(new ImageIcon(filePath), imageKind, userNo, createCd);
    }

    /**
     * 이미지파일 Validation
     * 서버에 업로드한 파일이 이미지파일이 아닐경우 파일을 삭제한 후 throw exception
     */
    public void checkImageValidation(String filePath) throws ProductException, IOException {
        int index = filePath.toLowerCase().lastIndexOf('.');
        if (index == -1) {
            return;
        }
        File file = new File(filePath);
        boolean isValid = false;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));

            int size = bis.available();

            //0byte 검사 및 파일형식 검사
            if( size==0 ) {
                return;
            }
            byte[] data = new byte[size];
            bis.read(data);

            String ext = filePath.substring(index + 1).toLowerCase().trim();
            if (ext.equals("png") && (data[1] == 0x50) && (data[2] == 0x4E) && (data[3] == 0x47)) {
                isValid = true;
            }
            else if((ext.equals("jpg") || ext.equals("jpeg"))
                    && ((data[6] == 0x4A) && (data[7] == 0x46) && (data[8] == 0x49) && (data[9] == 0x46)
                    || (data[6] == 0x45) && (data[7] == 0x78) && (data[8] == 0x69) && (data[9] == 0x66)
                    || (data[0] == (byte)0xFF) && (data[1] == (byte)0xD8)
                    || (data[0] == 0x47) && (data[1] == 0x49) && (data[2] == 0x46))){
                isValid = true;
            }
            else if(ext.equals("gif") && (data[0] == 0x47) && (data[1] == 0x49) && (data[2] == 0x46)) {
                isValid = true;
            }

        } catch (Exception e) {
            throw new ProductException(ImageExceptionMessage.IMG_03);
        } finally {
            bis.close();
        }
        if(!isValid){
            file.delete();
            throw new ProductException(ImageExceptionMessage.IMG_03);
        }
    }
}
