package com.elevenst.terroir.product.registration.image.controller;

import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.service.ProductImageServiceImpl;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.message.ProductExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skt.tmall.auth.Auth;
import skt.tmall.auth.AuthCode;
import skt.tmall.auth.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value="/image", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImageController {

    @Autowired
    ProductImageServiceImpl productImageService;

    private static final String BASE_64_PREFIX_JPEG = "data:image/jpeg;base64,";
    private static final String BASE_64_PREFIX_PNG = "data:image/png;base64,";
    private static final String BASE_64_PREFIX_GIF = "data:image/gif;base64,";

    @PostMapping ("/upload/tempByte")
    public JSONObject uploadTempImageByte(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam("imageKind") String imageKind,
                                          @RequestParam("fileName") String fileName,
                                          @RequestBody  String files  ){

        long selMnbdNo = 0;
        String tempPath = "";
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        try {
            byte[] imagByte= productImageService.decodeBase64ToBytes(files);
            tempPath = productImageService.uploadTempImageByte(imagByte, fileName, ImageKind.valueOf(imageKind), selMnbdNo);
        }catch(Exception e){
            throw new ProductException(e);
        }

        JSONObject json = new JSONObject();
        json.put("tempPath", tempPath);
        return json;

    }




    @PostMapping("/upload/temp")
    public JSONObject uploadTempImage(
        HttpServletRequest request, HttpServletResponse response,
        @RequestParam("imageKind") String imageKind,
        @RequestParam("files") MultipartFile[] files) throws ProductException, IOException {

        long selMnbdNo = 0;
        Auth auth = AuthService.getAuth(request, response, AuthCode.TMALL_AUTH);
        if (auth != null)  {
            selMnbdNo = auth.getMemberNumber();
        }
        if (selMnbdNo == 0) throw new ProductException(ProductExceptionMessage.AUTH_ERROR);

        if(files.length == 0){
            throw new ProductException("파일이 존재하지 않습니다.");
        }

        String tempPath = productImageService.uploadTempImage(files[0], ImageKind.valueOf(imageKind), selMnbdNo);
        JSONObject json = new JSONObject();
        json.put("tempPath", tempPath);
        return json;
    }

}
