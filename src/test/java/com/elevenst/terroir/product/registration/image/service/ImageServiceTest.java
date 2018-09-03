package com.elevenst.terroir.product.registration.image.service;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.property.FileProperty;
import com.elevenst.terroir.product.registration.image.code.ImageCode;
import com.elevenst.terroir.product.registration.image.code.ImageDir;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.vo.ProductImageVO;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class ImageServiceTest {

    @Autowired
    ProductImageServiceImpl productImageService;

    @Autowired
    private FileProperty fileProperty;

    @Test
    public void getImageStoragePathTest() {
        long prdNo = 1245252486;
        String imageStoragePath = productImageService.getImageStoragePath(prdNo, ImageDir.PRODUCT);
        assertNotNull(imageStoragePath);

        assertTrue(Pattern.matches("/([a-z]{2,3})/(\\d{2})/\\d/\\d/\\d/\\d/\\d/\\d$", imageStoragePath));
    }

    @Test
    public void getImageDBPath() {
        long prdNo = 1245252486;
        String imageDBPath = productImageService.getImageDBPath(prdNo, ImageDir.PRODUCT);
        assertNotNull(imageDBPath);

        assertTrue(Pattern.matches("/([a-z]{2,3})/(\\d{2})/\\d/\\d/\\d/\\d/\\d/\\d/([a-zA-Z]{5})$", imageDBPath));
    }

    @Test
    public void removeTempPathRandomString() {
        String imageUrl = "/data1/upload/st/pd/18/2/7/1/3/0/9/QARjd/1245271309_A3.jpg";
        String removedRandomString = imageUrl.replaceAll("/([a-zA-Z]{5})/", "/");

        assertNotNull(removedRandomString);
    }

    @Test
    public void imageCopyTest() throws IOException {
        String filepath = "img/anklebreaker604x610.jpg";
        File file = new ClassPathResource(filepath).getFile();
        FileUtils.copyFileToDirectory(file, new File(fileProperty.getTempPath()));
        File copiedFile = new File(fileProperty.getTempPath()+"/"+file.getName());

        assertTrue(FileUtils.contentEquals(file, copiedFile));
        FileUtils.forceDelete(copiedFile);
    }

    @Test
    public void getImageKindTest() {
        String filepath = "123456778_A.jpg";

        Pattern pattern = Pattern.compile("_([A-Z0-9])([._])");
        Matcher matcher = pattern.matcher(filepath);
        if(matcher.find()){
            log.info("matcher.group() : "+matcher.group(1));
        }
    }

    @Test
    public void uploadTmpFileTest() throws IOException {
        String filepath = "img/anklebreaker604x610.jpg";
        File file = new ClassPathResource(filepath).getFile();

        String result = productImageService.uploadTempImage(2, file, ImageKind.B, 1001);

        assertTrue(StringUtil.isNotEmpty(result));
    }

    @Test
    public void test02_selectImage() {
        long prdNo = 1245252486;
        PdPrdImage pdPrdImage = productImageService.getProductImage(prdNo);

        assertNotNull(pdPrdImage);
    }


    @Test
    public void test03_insertImageTest() throws IOException {
        ProductImageVO productImageVO = new ProductImageVO();
        productImageVO.setPrdNo(2);
        productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_NEW);
        productImageVO.setPrdTypCd("01");
        productImageVO.setCreateCd("0203");
        productImageVO.setSelMnbdNo(1001);

        List<ProductImageVO.ImageFile> imageFileList = new ArrayList<>();
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.B).setFile(new ClassPathResource("img/anklebreaker604x610.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A1).setFile(new ClassPathResource("img/600x600.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A2).setFile(new ClassPathResource("img/600x600.png").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A3).setFile(new ClassPathResource("img/600x600.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.D).setFile(new ClassPathResource("img/daeman720x360.jpg").getFile()));

        // 임시파일 업로드
        for(ProductImageVO.ImageFile imageFile : imageFileList){
            if(StringUtil.isEmpty(imageFile.getTempPath())){
                continue;
            }
            String temp = productImageService.uploadTempImage(2, imageFile.getFile(), imageFile.getImageKind(), 1001);
            imageFile.setTempPath(temp);
        }

        productImageVO.setImgFiles(imageFileList);

        productImageService.processProductImage(productImageVO, new ProductVO());
    }

    @Test
    public void test03_updateImageTest() throws IOException {
        ProductImageVO productImageVO = new ProductImageVO();
        productImageVO.setPrdNo(2);
        productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_UPDATE);
        productImageVO.setPrdTypCd("01");
        productImageVO.setCreateCd("0203");
        productImageVO.setSelMnbdNo(1001);

        List<ProductImageVO.ImageFile> imageFileList = new ArrayList<>();
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.B).setFile(new ClassPathResource("img/soonsal600x1067.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A1).setFile(new ClassPathResource("img/600x600.png").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A2).setFile(new ClassPathResource("img/600x600.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A3).setTempPath(""));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.D).setFile(new ClassPathResource("img/cardview720x360.png").getFile()));

        // 임시파일 업로드
        for(ProductImageVO.ImageFile imageFile : imageFileList){
            if(StringUtil.isEmpty(imageFile.getTempPath())){
                continue;
            }
            String temp = productImageService.uploadTempImage(2, imageFile.getFile(), imageFile.getImageKind(), 1001);
            imageFile.setTempPath(temp);
        }

        productImageVO.setImgFiles(imageFileList);

        ProductVO productVO = new ProductVO();
        productVO.setUpdate(true);
        productImageService.processProductImage(productImageVO, productVO);
    }

    @Test
    public void test03_copyImageTest() throws IOException {
        ProductImageVO productImageVO = new ProductImageVO();
        productImageVO.setPrdNo(3);
        productImageVO.setReRegPrdNo(2);
        productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_COPY);
        productImageVO.setPrdTypCd("01");
        productImageVO.setCreateCd("0203");
        productImageVO.setSelMnbdNo(1001);

        List<ProductImageVO.ImageFile> imageFileList = new ArrayList<>();
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.B).setFile(new ClassPathResource("img/600x600.png").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A1).setFile(new ClassPathResource("img/anklebreaker604x610.jpg").getFile()));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.A3).setTempPath(""));
        imageFileList.add(new ProductImageVO.ImageFile().setImageKind(ImageKind.L300).setTempPath(""));

        // 임시파일 업로드
        for(ProductImageVO.ImageFile imageFile : imageFileList){
            if(StringUtil.isEmpty(imageFile.getTempPath())){
                continue;
            }
            String temp = productImageService.uploadTempImage(2, imageFile.getFile(), imageFile.getImageKind(), 1001);
            imageFile.setTempPath(temp);
        }

        productImageVO.setImgFiles(imageFileList);

        ProductVO productVO = new ProductVO();
        productVO.setUpdate(true);
        productImageService.processProductImage(productImageVO, productVO);
    }

    private String setInsertImageJsonData() {
        String jsonData = "\n" +
            "{  \n" +
            "    \"productImageVO\": {\n" +
            "        \"imgFiles\": [\n" +
            "            {   \"imageKind\":\"B\", \"tempPath\":\"/data1/tmp/test_B.jpg\" },\n" +
            "            {   \"imageKind\":\"A1\", \"tempPath\":\"/data1/tmp/test_A1.jpg\"   },\n" +
            "            {   \"imageKind\":\"A2\", \"tempPath\":\"/data1/tmp/test_A2.jpg\"   },\n" +
            "            {   \"imageKind\":\"A3\", \"tempPath\":\"/data1/tmp/test_A3.jpg\"   },\n" +
            "            {   \"imageKind\":\"L300\", \"tempPath\":\"/data1/tmp/test_L300.jpg\"   }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

        return jsonData;
    }

    private String setUpdateImageJsonData() {
        String jsonData = "\n" +
            "{  \n" +
            "    \"productImageVO\": {\n" +
            "        \"imgFiles\": [\n" +
            "            {   \"imageKind\":\"B\", \"tempPath\":\"/data1/tmp/test_U_B.jpg\" },\n" +
            "            {   \"imageKind\":\"A1\", \"tempPath\":\"/data1/tmp/test_U_A1.jpg\"   },\n" +
            "            {   \"imageKind\":\"A2\", \"tempPath\":\"\"   },\n" +
            "            {   \"imageKind\":\"A3\", \"tempPath\":\"\"   },\n" +
            "            {   \"imageKind\":\"L300\", \"tempPath\":\"/data1/tmp/test_U_L300.jpg\"   }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

        return jsonData;
    }

    private String setCopyImageJsonData() {
        String jsonData = "\n" +
            "{  \n" +
            "    \"productImageVO\": {\n" +
            "        \"imgFiles\": [\n" +
            "            {   \"imageKind\":\"B\", \"tempPath\":\"/data1/tmp/test_C_B.jpg\" },\n" +
            "            {   \"imageKind\":\"A1\", \"tempPath\":\"/data1/tmp/test_C_A1.jpg\"   }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

        return jsonData;
    }

    @Test
    public void insertImage() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setInsertImageJsonData(), ProductVO.class);

        productVO.getProductImageVO().setSelMnbdNo(10000276);
        productVO.getProductImageVO().setPrdNo(1);
        productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_NEW);
        productVO.getProductImageVO().setPrdTypCd("01");
        productVO.getProductImageVO().setCreateCd("0203");

        try {;
            productImageService.processProductImage(productVO.getProductImageVO(), productVO);
        } catch(Exception e) {
            e.printStackTrace();
        }

        /*
        if(productVO != null && productVO.getProductImageVO() != null && productVO.getProductImageVO().getImgFiles() != null) {
            for(ProductImageVO.ImageFile imageFile : productVO.getProductImageVO().getImgFiles()) {
                System.out.println("aa= " + imageFile.getImageKind() + " : " + imageFile.getTempPath() + " : " + imageFile.isDelete());
            }
        }
        */
    }

    @Test
    public void updateImage() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setUpdateImageJsonData(), ProductVO.class);

        productVO.getProductImageVO().setSelMnbdNo(10000276);
        productVO.getProductImageVO().setPrdNo(1);
        productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_UPDATE);
        productVO.getProductImageVO().setPrdTypCd("01");
        productVO.getProductImageVO().setCreateCd("0203");

        try {
            productImageService.processProductImage(productVO.getProductImageVO(), productVO);
        } catch(Exception e) {
            e.printStackTrace();
        }

        /*
        if(productVO != null && productVO.getProductImageVO() != null && productVO.getProductImageVO().getImgFiles() != null) {
            for(ProductImageVO.ImageFile imageFile : productVO.getProductImageVO().getImgFiles()) {
                System.out.println("aa= " + imageFile.getImageKind() + " : " + imageFile.getTempPath() + " : " + imageFile.isDelete());
            }
        }
        */
    }

    @Test
    public void copyImage() {
        ProductVO productVO = new ProductVO();
        Gson gson = new Gson();
        productVO = gson.fromJson(setCopyImageJsonData(), ProductVO.class);

        productVO.getProductImageVO().setSelMnbdNo(10000276);
        productVO.getProductImageVO().setPrdNo(7); //신규 생성 상품번호
        productVO.getProductImageVO().setReRegPrdNo(1); //현재 상품번호
        productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_COPY);
        productVO.getProductImageVO().setPrdTypCd("01");
        productVO.getProductImageVO().setCreateCd("0203");

        try {
            productImageService.processProductImage(productVO.getProductImageVO(), productVO);
        } catch(Exception e) {
            e.printStackTrace();
        }

        /*
        if(productVO != null && productVO.getProductImageVO() != null && productVO.getProductImageVO().getImgFiles() != null) {
            for(ProductImageVO.ImageFile imageFile : productVO.getProductImageVO().getImgFiles()) {
                System.out.println("aa= " + imageFile.getImageKind() + " : " + imageFile.getTempPath() + " : " + imageFile.isDelete());
            }
        }
        */
    }

}
