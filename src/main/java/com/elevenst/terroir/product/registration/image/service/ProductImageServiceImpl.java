package com.elevenst.terroir.product.registration.image.service;

import com.elevenst.common.process.RegistrationInfoConverter;
import com.elevenst.common.util.DateUtil;
import com.elevenst.common.util.FileUtil;
import com.elevenst.common.util.GroupCodeUtil;
import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.common.property.FileProperty;
import com.elevenst.terroir.product.registration.image.code.ImageCode;
import com.elevenst.terroir.product.registration.image.code.ImageDir;
import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.entity.PdPrdImage;
import com.elevenst.terroir.product.registration.image.mapper.ProductImageMapper;
import com.elevenst.terroir.product.registration.image.message.ImageExceptionMessage;
import com.elevenst.terroir.product.registration.image.validate.ProductImageValidate;
import com.elevenst.terroir.product.registration.image.vo.ProductImageVO;
import com.elevenst.terroir.product.registration.option.entity.PdOptDtlImage;
import com.elevenst.terroir.product.registration.option.mapper.OptionMapper;
import com.elevenst.terroir.product.registration.product.code.CreateCdTypes;
import com.elevenst.terroir.product.registration.product.code.PrdTypCd;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import com.elevenst.terroir.product.registration.product.vo.ProductVO;
import com.elevenst.terroir.product.registration.seller.service.SellerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skt.tmall.common.properties.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ProductImageServiceImpl implements RegistrationInfoConverter<ProductVO>{

    public final String FILE_SEPERATOR = System.getProperty("file.separator");
    public final String PREFIX_TEMP_PATH = "/temp/prd";
    private FastDateFormat format = FastDateFormat.getInstance("yy");

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private ProductImageValidate productImageValidate;

    @Autowired
    private FileProperty fileProperty;

    @Autowired
    private ProductImageSetter productImageSetter;

    @Autowired
    private SellerServiceImpl sellerService;

    private static final String BASE_64_PREFIX_JPEG = "data:image/jpeg;base64,";
    private static final String BASE_64_PREFIX_PNG = "data:image/png;base64,";
    private static final String BASE_64_PREFIX_GIF = "data:image/gif;base64,";


    /**
     * 이미지 조회
     * @param prdNo
     * @throws ProductException
     */
    public PdPrdImage getProductImage(long prdNo) throws ProductException {
        return productImageMapper.getProductImage(prdNo);
    }

    /**
     * 이미지 임시폴더 업로드(상품번호 없음)
     * @param multipartFile
     * @param imageKind
     * @param userNo
     * @throws ProductException
     */
    public String uploadTempImage(MultipartFile multipartFile, ImageKind imageKind, long userNo) throws ProductException, IOException {
        productImageValidate.checkImageExtension(multipartFile.getOriginalFilename(), imageKind);
        productImageValidate.checkGifImage(multipartFile.getOriginalFilename());

        StringJoiner joiner = new StringJoiner(FILE_SEPERATOR);
        joiner.add(fileProperty.getTempPath())
                .add(DateUtil.getFormatString("yyyyMMdd"))
                .add(String.valueOf(userNo));
        String tempPath = joiner.toString();
        String filePath = tempPath+"/"+String.valueOf(new Date().getTime())+"_"+imageKind.name()+"."+FileUtil.getFileExt(multipartFile.getOriginalFilename());
        String retPath = PREFIX_TEMP_PATH+FILE_SEPERATOR+DateUtil.getFormatString("yyyyMMdd")+FILE_SEPERATOR+String.valueOf(userNo)+FILE_SEPERATOR+String.valueOf(new Date().getTime())+"_"+imageKind.name()+"."+FileUtil.getFileExt(multipartFile.getOriginalFilename());

        File tempDir = new File(tempPath);
        FileUtils.forceMkdir(tempDir);
        multipartFile.transferTo(new File(filePath));

        productImageValidate.checkImageSize(tempDir.length(), imageKind);  //췤 용량체크 없어질듯?
        productImageValidate.checkImageValidation(filePath);
        productImageValidate.checkImageWH(new ImageIcon(filePath), imageKind, userNo, CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        return retPath;
    }

    public String uploadTempImageByte(byte [] imageByte, String fileName, ImageKind imageKind, long userNo) throws ProductException, IOException {
        //productImageValidate.checkImageExtension(multipartFile.getOriginalFilename(), imageKind);
        //productImageValidate.checkGifImage(multipartFile.getOriginalFilename());

        StringJoiner joiner = new StringJoiner(FILE_SEPERATOR);
        joiner.add(fileProperty.getTempPath())
                .add(DateUtil.getFormatString("yyyyMMdd"))
                .add(String.valueOf(userNo));
        String tempPath = joiner.toString();
        String createTempFileNameStr = String.valueOf(new Date().getTime());
        String filePath = tempPath+"/"+createTempFileNameStr+"_"+imageKind.name()+"."+FileUtil.getFileExt(fileName);
        String retPath = PREFIX_TEMP_PATH+FILE_SEPERATOR+DateUtil.getFormatString("yyyyMMdd")+FILE_SEPERATOR+String.valueOf(userNo)+FILE_SEPERATOR+createTempFileNameStr+"_"+imageKind.name()+"."+FileUtil.getFileExt(fileName);

        File tempDir = new File(tempPath);
        FileUtils.forceMkdir(tempDir);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        File tempFile = new File(filePath);
        ImageIO.write(bufferedImage, FileUtil.getFileExt(fileName), tempFile); //저장하고자 하는 파일 경로를 입력합니다

        System.out.println("====> tempDir.length() "+tempDir.length());
        System.out.println("====> tempPath() "+tempPath);
        productImageValidate.checkImageSize(tempFile.length(), imageKind);  //췤 용량체크 없어질듯?
        productImageValidate.checkImageValidation(filePath);
        productImageValidate.checkImageWH(new ImageIcon(filePath), imageKind, userNo, CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        return retPath;
    }

    /**
     * 이미지 임시폴더 업로드
     * @param prdNo
     * @param file
     * @param imageKind
     * @param userNo
     * @throws ProductException
     */
    public String uploadTempImage(long prdNo, File file, ImageKind imageKind, long userNo) throws ProductException, IOException {
        productImageValidate.checkImageExtension(file.getName(), imageKind);
        productImageValidate.checkGifImage(file.getName());

        StringJoiner joiner = new StringJoiner(FILE_SEPERATOR);
        joiner.add(fileProperty.getTempPath())
                .add(DateUtil.getFormatString("yyyyMMddHHmmss"))
                .add(String.valueOf(userNo))
                .add(String.valueOf(prdNo)+"_"+imageKind.name()+"."+FileUtil.getFileExt(file.getName()));
        String tempPath = joiner.toString();

        FileUtil.copy(file.getAbsolutePath(), tempPath);
        productImageValidate.checkImageSize(new File(tempPath).length(), imageKind);  //췤 용량체크 없어질듯?
        productImageValidate.checkImageValidation(tempPath);
        productImageValidate.checkImageWH(new ImageIcon(tempPath), imageKind, userNo, CreateCdTypes.MO_SIMPLEREG.getDtlsCd());
        return tempPath;
    }

    public ProductImageVO uploadTempCopyImage(ProductImageVO productImageVO, PdPrdImage orgnPdPrdImage, long userNo) throws ProductException, IOException {
        long prdNo = productImageVO.getPrdNo();
        List<ProductImageVO.ImageFile> imgFileList = productImageVO.getImgFiles();
        for(ImageKind imageKind : ImageKind.values()){
            String extNm = productImageSetter.getExtNm(imageKind, orgnPdPrdImage);
            if(StringUtil.isEmpty(extNm) || isCopyNewImage(productImageVO, imageKind)){
                continue;
            }
            String tempPath = uploadTempImage(prdNo, new File(fileProperty.getUploadPath()+"/"+parseImageDBPath(extNm)), imageKind, userNo);
            imgFileList.add(new ProductImageVO.ImageFile().setImageKind(imageKind).setTempPath(tempPath));
        }
        productImageVO.setImgFiles(imgFileList);
        return productImageVO;
    }

    // 복사할때 원본 이미지가 존재하고 그 원본이미지를 그대로 갖다쓸지 체크 - true이면 원본 이미지 안쓰고 새로 이미지 등록함
    private boolean isCopyNewImage(ProductImageVO productImageVO, ImageKind imageKind){
        return productImageVO.getImgFiles().stream().filter(x -> x.getImageKind() == imageKind).count() > 0;
    }

    /**
     * 이미지 저장과정(유효성 검증 및 세팅)
     * @param productImageVO
     * @throws ProductException
     */
    public void processProductImage(ProductImageVO productImageVO, ProductVO productVO) throws ProductException, IOException {
        long userNo = productImageVO.getSelMnbdNo();
        long prdNo = productImageVO.getPrdNo();
        long orgnPrdNo = prdNo;   //복사, 수정시 원본 상품번호가 된다.
        long uploadType = productImageVO.getUploadType();
        boolean isImageHistoryInsert = true;
        String realHardImagePath = fileProperty.getUploadPath();
        String orgnRealImagePath = "";
        PdPrdImage orgnPdPrdImage = null;

        PdPrdImage pdPrdImage = new PdPrdImage();
        pdPrdImage.setPrdNo(prdNo);

//        췤 임시등록은 따로 빠질듯
//        //이미지 임시등록 (API, 대량등록일 경우 예외)
//        String createCd = productImageVO.getCreateCd();
//        if (!"01".equals(createCd.substring(0,2)) && GroupCodeUtil.notEqualsDtlsCd(createCd, CreateCdTypes.SO_BULKREG)) {
//            productImageValidate.checkImageSize(productImageVO.getImgFiles());
//            FileUtil.copyFiles(productImageVO.getImgFiles(), imageProperty.getTempPath());
//        }

        //복사인데 원본상품번호가 없거나 원본과 등록상품 번호가 같은경우 exception
        if(uploadType == ImageCode.UPLOAD_TYPE_COPY){
            if(productImageVO.getReRegPrdNo()<=0 || productImageVO.getReRegPrdNo() == prdNo){
                throw new ProductException(ImageExceptionMessage.IMG_08);
            }
            orgnPrdNo = productImageVO.getReRegPrdNo();
        }

        //복사 수정 원본세팅
        if(uploadType == ImageCode.UPLOAD_TYPE_COPY || uploadType == ImageCode.UPLOAD_TYPE_UPDATE) {
            orgnPdPrdImage = this.getProductImage(orgnPrdNo);
            if (orgnPdPrdImage == null || orgnPdPrdImage.getPrdNo() == 0) {
                throw new ProductException(ImageExceptionMessage.IMG_08);
            }
            if(uploadType == ImageCode.UPLOAD_TYPE_COPY) {
                productImageVO = uploadTempCopyImage(productImageVO, orgnPdPrdImage, userNo);
                orgnRealImagePath = fileProperty.getUploadPath();
                if(orgnPdPrdImage.getUrlPath() != null){
                    orgnRealImagePath = orgnPdPrdImage.getUrlPath();
                }
            }
            // 복사일 경우 -- ASIS/TOBE 정보를 구분하여 가지고 있는다.
//            췤 위에 넣어도 될것같음
//            if(uploadType == ImageProperty.UPLOAD_TYPE_COPY){
//                orgRealImagePath = imageProperty.getUploadPath() + orgnPdPrdImage.getUrlPath();
//            }
            pdPrdImage = setImageMapping(productImageVO, pdPrdImage, orgnPdPrdImage);
        }

//        // 복사된 상품번호로 조회 시 복사된 이미지 정보가 존재하지 않을 경우 Exception 발생
//        if(uploadType == ImageProperty.UPLOAD_TYPE_COPY && !isModify){
//            throw new ProductException(ImageExceptionMessage.IMG_08);
//        }

//        췤 필요없는것 같음
        // GIF 이미지 구매에 따른 셋팅
        // GIF 전시 아이템 여부가 "Y" 이면 무조건 "_10" 을 말아서 저장한다.
//        if(productImageVO.isGifItm()){
//            pdPrdImage.setCutCnt(10);
//        }
//        // 등록일 때 GIF 전시 아이템 여부가 "N" 이면 무조건 "_10"을 넣지 않고말아서 저장한다.
//        else if(!isModify && !productImageVO.isGifItm()){
//            pdPrdImage.setCutCnt(1);
//        }
//        // 수정일 때
//        else if(isModify){
//            //수정일때 GIF 전시아이템 여부가 "N" "_10"을 넣지 않고 말아서 저장한다.
//            pdPrdImage.setCutCnt(1);
//
//            //수정일때 GIF 전시아이템 여부가 "Y" 이면 "_10" 을 말아 넣는다
//            if(orgnPdPrdImage != null && orgnPdPrdImage.getCutCnt() > 1 && uploadType != ImageProperty.UPLOAD_TYPE_COPY){
//                pdPrdImage.setCutCnt(10);
//            }
//        }

        // image table select
        PdPrdImage selectPdPrdImage = productImageMapper.getProductImage(prdNo);

        for(ProductImageVO.ImageFile imageFile : productImageVO.getImgFiles()){

            ImageKind imageKind = imageFile.getImageKind();

            //String tempPath = realHardImagePath+FILE_SEPERATOR+DateUtil.getFormatString("yyyyMMdd")+FILE_SEPERATOR+productImageVO.getSelMnbdNo()+imageFile.getTempPath();
            String tempPath = realHardImagePath+imageFile.getTempPath();
            /*
            if(productVO.isUpdate() && StringUtil.isNotEmpty(imageFile.getTempPath())) {
                int randomIndex = imageFile.getTempPath().lastIndexOf(FILE_SEPERATOR);
                tempPath = realHardImagePath+imageFile.getTempPath().substring(0, randomIndex-6)+imageFile.getTempPath().substring(randomIndex, imageFile.getTempPath().length());
            }
            */
            String filename = String.valueOf(prdNo) + "_"+imageKind.name()+"."+FileUtil.getFileExt(tempPath);

            //if(imageFile.isDelete()){
            if(StringUtil.isEmpty(imageFile.getTempPath())){
                if(uploadType!= ImageCode.UPLOAD_TYPE_COPY){
                    FileUtil.delete(realHardImagePath + parseImageDBPath(productImageSetter.getExtNm(imageKind, orgnPdPrdImage)));
                }
                productImageSetter.setExtNm(imageKind, pdPrdImage, null);
                continue;
            }

            if(imageKind == ImageKind.B && selectPdPrdImage != null && imageFile.getTempPath().equals(selectPdPrdImage.getDtlBasicExtNm())) {
                continue;
            } else if(imageKind == ImageKind.A1 && selectPdPrdImage != null && imageFile.getTempPath().equals(selectPdPrdImage.getAdd1ExtNm())) {
                continue;
            } else if(imageKind == ImageKind.A2 && selectPdPrdImage != null && imageFile.getTempPath().equals(selectPdPrdImage.getAdd2ExtNm())) {
                continue;
            } else if(imageKind == ImageKind.A3 && selectPdPrdImage != null && imageFile.getTempPath().equals(selectPdPrdImage.getAdd3ExtNm())) {
                continue;
            } else if(imageKind == ImageKind.L300 && selectPdPrdImage != null && imageFile.getTempPath().equals(selectPdPrdImage.getPngExtNm())) {
                continue;
            }

            if(uploadType == ImageCode.UPLOAD_TYPE_UPDATE || uploadType== ImageCode.UPLOAD_TYPE_COPY){
                tempPath = tempPath.replaceAll("/([a-zA-Z]{5})/", "/");
            }

            /*
            if(StringUtil.isEmpty(imageFile.getTempPath())) {
                continue;
            }
            */

            productImageValidate.checkImageExtension(filename, imageKind);
            productImageValidate.checkGifImage(filename);
            productImageValidate.checkImageSize(new File(tempPath).length(), imageKind);
            productImageValidate.checkImageValidation(tempPath);
            productImageValidate.checkImageWH(tempPath, imageKind, 0, productImageVO.getCreateCd());

//            췤 필요없는것 같음
//            if(productImageVO.getEventNo() > 0 && imageKind == ImageKind.B){
//                rtnMap.put("changeImage", true);
//            }

            // 변경 파일명(실제 변경되어 저장 되어지는 파일명)
            String dbImagePath =  getImageDBPath(prdNo, productImageVO.isDeal() ? ImageDir.DEAL : ImageDir.PRODUCT);
            String storageImagePath = getImageStoragePath(prdNo, productImageVO.isDeal() ? ImageDir.DEAL : ImageDir.PRODUCT);

            String targetFileNameDB = getTargetFileName(productImageVO, filename, imageKind, dbImagePath);
            String targetFileNameStorage = getTargetFileName(productImageVO, filename, imageKind, storageImagePath);

            // 기본이미지 이면서 여행상품인경우(PRD_TYP_CD = '13')는 별도로 원본이미지를 따로 저장한다.
            if(imageKind == ImageKind.B && GroupCodeUtil.equalsDtlsCd(productImageVO.getPrdTypCd(), PrdTypCd.BP_TRAVEL)){
                FileUtil.copy(tempPath, realHardImagePath+"/"+targetFileNameStorage.replace(ImageKind.B.name(), ImageKind.O.name()));  // 입력 받은 파일을 이동
            }

            if(uploadType!= ImageCode.UPLOAD_TYPE_COPY && StringUtil.isNotEmpty(productImageSetter.getExtNm(imageKind, orgnPdPrdImage))){
                String orgnFile = fileProperty.getUploadPath() + parseImageDBPath(productImageSetter.getExtNm(imageKind, orgnPdPrdImage));
                FileUtil.delete(orgnFile);
            }

            //FileUtil.move(tempPath, realHardImagePath+"/"+targetFileNameStorage); // 임시 폴더에 들어간 파일을 실제 경로로 이동
            FileUtil.copy(tempPath, realHardImagePath+"/"+targetFileNameStorage); // 임시 폴더에 들어간 파일을 실제 경로로 이동

            //// 췤 - 함수로 빼던지 해야함
            ImageIcon imageIcon = new ImageIcon(tempPath);
            String zoominImgYn = (imageIcon.getIconWidth() > 999 && imageIcon.getIconHeight() > 999) ? "Y":"N";
            String srcStorageImagePath = parseImageDBPath(productImageSetter.getExtNm(imageKind, orgnPdPrdImage));
            String destDbImagePath = productImageSetter.getExtNm(imageKind, pdPrdImage);
            String destStorageImagePath = parseImageDBPath(destDbImagePath);
            pdPrdImage = productImageSetter.setExtNm(imageKind, pdPrdImage, targetFileNameDB);  // 변경 파일명 설정

            if(imageKind == ImageKind.B){
                productImageVO.setDtlBaseImgChgYn("Y");	//기본이미지 등록변경시엔 히스토리 남김
                productImageVO.setImgScoreEftvYn("N");  //기본이미지 등록변경시엔 이미지스코어유효성이 N으로 무효화 됨

                // 정사이즈가 아닐경우 white background 처리
                boolean isImgExSeller = sellerService.isImgExSeller(userNo);
                // 파일의 복사 가 있으므로 PNG 파일을 먼저 만듬
                if(!isImgExSeller && StringUtil.isNotEmpty(targetFileNameDB)){
                    // 해당 경로에 변경되는 파일명으로 PNG 파일 생성
                    doWhiteBgProc(realHardImagePath, targetFileNameDB);
                }

                // PD_PRD_IMAGE용 - 신규
                pdPrdImage.setBasicZoominImgYn(zoominImgYn);
                pdPrdImage.setPngExtNm(targetFileNameDB); 		// 상품 PNG 파일 이미지 명

                // 기본이미지의 원본이미지 (제휴사 여행 상품일 경우)
                if(GroupCodeUtil.equalsDtlsCd(productImageVO.getPrdTypCd(), PrdTypCd.BP_TRAVEL)){
                    pdPrdImage.setOrgExtNm(targetFileNameDB.replace(ImageKind.B.name(), ImageKind.O.name()));	// 상품 원본 이미지 명 (B->0로 변경)
                }

                // Basic Image 파일 확장자명 - 췤 필요없어보임
                //dataBox.put("basicFileExt", fileExt);

                // 상품 복사일 경우
                if(uploadType == ImageCode.UPLOAD_TYPE_COPY){
                    String orgnFilePath = orgnRealImagePath + srcStorageImagePath;

                    // 복사하려는 상품 기본 이미지가 실제 존재하는지 여부(존재하지 않을 경우 Exception 발생)
                    if(StringUtil.isEmpty(orgnPdPrdImage.getDtlBasicExtNm()) || !FileUtil.exist(orgnFilePath)){
                        throw new ProductException("기존 상품에 기본이미지가 존재하지 않습니다. 기본이미지 등록 후 재시도 하십시요.");
                    }

                    // 기존 상품(ASIS) -> 복사 상품(TOBE)로 물리적 복사
                    productImageVO.setDtlBaseImgChgYn("Y"); //기존상품의 이미지가 있는 경우에는 복사대상상품 이미지 추가됨

//                    췤 위에서 옮기는데 중복 복사인듯
//                    FileUtil.copy(orgnFilePath, realHardImagePath+"/"+destStorageImagePath);

                    // 제휴사 여행 상품일 경우
                    if(GroupCodeUtil.equalsDtlsCd(productImageVO.getPrdTypCd(), PrdTypCd.BP_TRAVEL)){
                        // 원본 이미지
                        String orginFileNm1 = srcStorageImagePath.replace("_"+ImageKind.B.name(), "_"+ImageKind.O.name());
                        String orginFileNm2 = destStorageImagePath.replace("_"+ImageKind.B.name(), "_"+ImageKind.O.name());
                        String descDbFileNm = destDbImagePath.replace("_"+ImageKind.B.name(), "_"+ImageKind.O.name());
                        // 원본이미지가 있으면 원본을 저장하고 없으면 기본이미지를 원본이미지로 복사한다.
                        if( FileUtil.exist(orgnRealImagePath+"/"+orginFileNm1)){
                            FileUtil.copy(orgnRealImagePath+"/"+orginFileNm1, realHardImagePath+"/"+orginFileNm2);
                        }
                        else{
                            FileUtil.copy(orgnFilePath, realHardImagePath+"/"+orginFileNm2);
                        }
                        // 원본이미지 셋팅
                        pdPrdImage.setOrgExtNm(descDbFileNm);
                    }
                }
            } else if(Arrays.asList(ImageKind.values()).contains(imageKind)) {
                if(imageKind == ImageKind.A1 || imageKind == ImageKind.A2 || imageKind == ImageKind.A3){
                    pdPrdImage = productImageSetter.setZoominImgYn(imageKind, pdPrdImage, zoominImgYn);
                    productImageVO = productImageSetter.setImgChgYn(imageKind, productImageVO, "Y");
                }

                if(uploadType == ImageCode.UPLOAD_TYPE_COPY && !FileUtil.exist(orgnRealImagePath+"/"+srcStorageImagePath)) {
                    //췤 파일이 존재하지 않으면 null로 세팅함 확인필요
                    pdPrdImage = productImageSetter.setExtNm(imageKind, pdPrdImage, null);
                    isImageHistoryInsert = false;
                }
                /*
                췤 복사시 원본이미지 사용을 위에서 처리해서 여기선 필요 없을듯
                // 추가 이미지 복사일 경우
                // 실제 이미지 복사
                // 복사하려는 상품 추가 이미지가 실제 존재하는지 여부(존재하지 않을 경우 DB에도 저장하지 않음)
                if(uploadType == ImageProperty.UPLOAD_TYPE_COPY){
                    if(FileUtil.exist(orgnRealImagePath+"/"+srcStorageImagePath)){
                        // 기존 상품(ASIS) -> 복사 상품(TOBE)로 물리적 복사
                        FileUtil.copy(orgnRealImagePath+"/"+srcStorageImagePath, realHardImagePath+"/"+destStorageImagePath);
                    }
                    // DB에는 있지만 물리적으로 데이터가 없는경우는 DB도 저장하지 않음
                    else {
                        pdPrdImage = productImageSetter.setExtNm(imageKind, pdPrdImage, null);
                        isImageHistoryInsert = false;
                    }
                }*/

                if(imageKind == ImageKind.L300){
                    /* 췤 필요없어보임
                    // 후처리 상품대표이미지와 신규목록이미지가 수정될경우
                    if(StringUtil.isEmpty(pdPrdImage.getBasicExtNm())){
                        pdPrdImage.setBasicExtNm(pdPrdImage.getDtlBasicExtNm());
                    }
                    if(isPrdCopy){
                        if(pdPrdImage.getBasicExtNm() == null || orgnPdPrdImage.getBasicExtNm() == null
                                || (!isImage && orgnPdPrdImage.getDtlBasicExtNm().equals(orgnPdPrdImage.getBasicExtNm())) ){
                            isImageHistoryEnable = true;
                            pdPrdImage.setBasicExtNm(pdPrdImage.getDtlBasicExtNm());
                        }
                    }else if(isUpdate){
                        if(pdPrdImage.getBasicExtNm() == null || orgnPdPrdImage.getBasicExtNm() == null
                                || (!pdPrdImage.getDtlBasicExtNm().equals(orgnPdPrdImage.getDtlBasicExtNm())
                                && pdPrdImage.getBasicExtNm().equals(orgnPdPrdImage.getBasicExtNm())
                                && orgnPdPrdImage.getDtlBasicExtNm().equals(orgnPdPrdImage.getBasicExtNm())) ){

                            pdPrdImage.setBasicExtNm(pdPrdImage.getDtlBasicExtNm());
                        }
                    }else{
                        if(pdPrdImage.getBasicExtNm() == null || pdPrdImage.getBasicExtNm().length() == 0){
                            pdPrdImage.setBasicExtNm(pdPrdImage.getDtlBasicExtNm());
                        }
                    }
                    if(!pdPrdImage.getBasicExtNm().equals(orgnPdPrdImage.getBasicExtNm())){
                        pdPrdImage.setL170ExtNm(null);
                        pdPrdImage.setL80ExtNm(null);
                    }*/

                    if (isImageHistoryInsert) { //추가,변경,복사 이면서 삭제가 아닌 경우
                        productImageVO.setBaseImgChgYn("Y");
                        if (uploadType != ImageCode.UPLOAD_TYPE_COPY) { //이미지 복사를 제외한 추가,변경삭제 등이 이루어질 경우 기존 스코어는 무효화
                            pdPrdImage.setImgScoreEftvYn("N");
                        }
                    }
                }

            }
        }
        //목록이미지 없을경우 기본이미지 넣음
        if(StringUtil.isEmpty(pdPrdImage.getBasicExtNm())){
            pdPrdImage.setBasicExtNm(pdPrdImage.getDtlBasicExtNm());
        }

        productImageVO.setPdPrdImage(pdPrdImage);
        saveProductImage(productImageVO);
    }

    public void saveProductImage(ProductImageVO productImageVO) throws ProductException, IOException {
        if(productImageVO.getUploadType() == ImageCode.UPLOAD_TYPE_UPDATE){
            productImageMapper.updateProductImage(productImageVO.getPdPrdImage());
        } else {
            productImageMapper.insertProductImage(productImageVO.getPdPrdImage());
        }
        // image history
        if (productImageVO.isImageHistoryInsert()
                && ("Y".equals(productImageVO.getBaseImgChgYn())
                || "Y".equals(productImageVO.getDtlBaseImgChgYn())
                || "Y".equals(productImageVO.getAdd1ImgChgYn())
                || "Y".equals(productImageVO.getAdd2ImgChgYn())
                || "Y".equals(productImageVO.getAdd3ImgChgYn()))) {

            productImageMapper.insertProductImageChgHist(productImageVO.getPdPrdImageChgHist());
        }
    }

    private PdPrdImage setImageMapping(ProductImageVO productImageVO, PdPrdImage pdPrdImage, PdPrdImage orgnPdPrdImage) throws ProductException {
        long prdNo = productImageVO.getPrdNo();

        pdPrdImage.setImgScoreEftvYn(orgnPdPrdImage.getImgScoreEftvYn());
        pdPrdImage.setBaseImgScore(orgnPdPrdImage.getBaseImgScore());
        pdPrdImage.setDtlBaseImgScore(orgnPdPrdImage.getDtlBaseImgScore());
        pdPrdImage.setBaseImgWdthVal(orgnPdPrdImage.getBaseImgWdthVal());
        pdPrdImage.setBaseImgVrtclVal(orgnPdPrdImage.getBaseImgVrtclVal());
        pdPrdImage.setDtlBaseImgWdthVal(orgnPdPrdImage.getDtlBaseImgWdthVal());
        pdPrdImage.setDtlBaseImgVrtclVal(orgnPdPrdImage.getDtlBaseImgVrtclVal());

        pdPrdImage.setBasicZoominImgYn(StringUtil.nvl(orgnPdPrdImage.getBasicZoominImgYn(), "N"));
        pdPrdImage.setAdd1ZoominImgYn(StringUtil.nvl(orgnPdPrdImage.getAdd1ZoominImgYn(), "N"));
        pdPrdImage.setAdd2ZoominImgYn(StringUtil.nvl(orgnPdPrdImage.getAdd2ZoominImgYn(), "N"));
        pdPrdImage.setAdd3ZoominImgYn(StringUtil.nvl(orgnPdPrdImage.getAdd3ZoominImgYn(), "N"));

        // 복사일 경우 -- ASIS/TOBE 정보를 구분하여 가지고 있는다.
        if(productImageVO.getUploadType() == ImageCode.UPLOAD_TYPE_COPY){
            String dbImagePath = getImageDBPath(prdNo, productImageVO.isDeal() ? ImageDir.DEAL : ImageDir.PRODUCT)+"/";
            // 복사해서 저장할 이미지 정보
            pdPrdImage.setBasicExtNm(joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getBasicExtNm(), prdNo)));
            pdPrdImage.setAdd1ExtNm (joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getAdd1ExtNm(), prdNo)));
            pdPrdImage.setAdd2ExtNm (joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getAdd2ExtNm(), prdNo)));
            pdPrdImage.setAdd3ExtNm (joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getAdd3ExtNm(), prdNo)));
            pdPrdImage.setPngExtNm  (joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getPngExtNm(), prdNo)));
            pdPrdImage.setOrgExtNm  (joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getOrgExtNm(), prdNo)));
            pdPrdImage.setStyleExtNm(joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getStyleExtNm(), prdNo)));
            pdPrdImage.setWirelssDtlExtNm(joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getWirelssDtlExtNm(), prdNo)));
            pdPrdImage.setDtlBasicExtNm(joinUrlPrdFileNm(dbImagePath, prdNoChange(orgnPdPrdImage.getDtlBasicExtNm(), prdNo)));
        }
        else{
            // 수정해서 저장할 이미지 정보
            String dbImagePath = StringUtil.nvl(orgnPdPrdImage.getUrlPath());
            pdPrdImage.setBasicExtNm(joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getBasicExtNm())));
            pdPrdImage.setAdd1ExtNm (joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getAdd1ExtNm())));
            pdPrdImage.setAdd2ExtNm (joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getAdd2ExtNm())));
            pdPrdImage.setAdd3ExtNm (joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getAdd3ExtNm())));
            pdPrdImage.setPngExtNm  (joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getPngExtNm())));
            pdPrdImage.setOrgExtNm  (joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getOrgExtNm())));
            pdPrdImage.setStyleExtNm(joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getStyleExtNm())));
            pdPrdImage.setWirelssDtlExtNm(joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getWirelssDtlExtNm())));
            pdPrdImage.setDtlBasicExtNm(joinUrlPrdFileNm(dbImagePath, StringUtil.nvl(orgnPdPrdImage.getDtlBasicExtNm())));
        }
        return pdPrdImage;
    }

    private String joinUrlPrdFileNm(String urlPath, String fileNm ) {
        if(StringUtil.isEmpty(fileNm)) {
            return "";
        }
        return urlPath+fileNm;
    }

    private String prdNoChange(String fileName, long chglPrdNo ) {
        String rtnFileName = "";

        if(StringUtil.isEmpty(fileName)){
            return rtnFileName;
        }
        if (fileName.indexOf("_V") > -1){
            rtnFileName = String.valueOf(chglPrdNo) + fileName.substring(fileName.indexOf("_"), fileName.lastIndexOf("_")) + fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        } else {
            rtnFileName = chglPrdNo + fileName.substring(fileName.indexOf("_"));
        }
        return rtnFileName;
    }

    public ImageKind getImageKind(String filename) {
        Pattern pattern = Pattern.compile("_([A-Z0-9])([._])");
        Matcher matcher = pattern.matcher(filename);
        if(matcher.find()){
            return ImageKind.valueOf(matcher.group(1));
        }
        return null;
    }

    private String getTargetFileName(ProductImageVO productImageVO, String fileNm, ImageKind imageKind, String imagePath) throws ProductException {
        String ext = FileUtil.getFileExt(fileNm);
        if(productImageVO.isDeal()){
            if(productImageVO.getEventNo() == 0){
                return imagePath+"/"+String.valueOf(productImageVO.getPrdNo())+"."+ext;
            }
            return imagePath+"/"+String.valueOf(productImageVO.getPrdNo())+"_"+String.valueOf(productImageVO.getEventNo())+"."+ext;
        }
        else{
            return imagePath+"/"+String.valueOf(productImageVO.getPrdNo())+"_"+imageKind.name()+"."+ext;
        }
    }

    /**
     * 물리디스크에 저장되는 image path
     * ex) /{rootPath}/{2digityear}/1/2/3/4/5/6
     * @param prdNo
     * @param dir
     * @return
     */
    public String getImageStoragePath(long prdNo, ImageDir dir){
        StringJoiner joiner = new StringJoiner(FILE_SEPERATOR);
        joiner.add(dir.toString()).add(format.format(new Date()));

        String strPrdNo = StringUtils.leftPad(String.valueOf(prdNo), 6, '0');
        // 상품번호 뒤에서부터 6자리를 URL로 변경한다.
        for(int i=strPrdNo.length()-6, len=strPrdNo.length(); i<len; i++){
            joiner.add(Character.toString(strPrdNo.charAt(i)));
        }
        return joiner.toString();
    }

    /**
     * db에 저장되는 image path
     * ex) /{rootPath}/{2digityear}/1/2/3/4/5/6 + abcde
     * @param prdNo
     * @param dir
     * @return
     */
    public String getImageDBPath(long prdNo, ImageDir dir){
        return getImageStoragePath(prdNo, dir) + FILE_SEPERATOR + RandomStringUtils.randomAlphabetic(5);
    }

    /**
     * 주어진 경로(dbpath)에서 랜덤텍스트 제거 후 결과 반환
     * @param dbPath
     * @return
     */
    public static String parseImageDBPath(String dbPath){
        if(dbPath == null){
            return null;
        }
        if(dbPath.indexOf("/pd/") != 0 && dbPath.indexOf("/dl/") != 0 && dbPath.indexOf("/cat/") != 0) {
            return dbPath;
        }
        return dbPath.replaceAll("/([a-zA-Z]{5})", "");
    }

    public String setOptionImage(long prdNo, String dgstExtNm, String realHardImagePath, String dbImagePath, String type) throws Exception {
        String fileName = "";

        // 외부 이미지 URL일 경우
        if( dgstExtNm.indexOf("http") == 0 ) {
            fileName = dgstExtNm;
        } else {
            // 내부 이미지
            String makeFiles[] = dgstExtNm.split("_");
            if("summary".equals(type) && makeFiles.length >= 1) {
                fileName = dbImagePath + FILE_SEPERATOR + prdNo+"_"+makeFiles[1];
            } else if("detail".equals(type) && makeFiles.length >= 2) {
                fileName = dbImagePath + FILE_SEPERATOR + prdNo+"_"+makeFiles[1]+"_"+makeFiles[2];
            }

            File file = new File(realHardImagePath+dgstExtNm);
            if(!"".equals(fileName) && file.isFile())
                FileUtil.copy(realHardImagePath+dgstExtNm, realHardImagePath+fileName);
            else fileName = dgstExtNm;
        }

        return fileName;
    }

    public List<PdOptDtlImage> setOptionImage(ProductVO productVO, long prevPrdNo, long newPrdNo, boolean isMainImageReg, boolean isCopyOptValueSeq) throws Exception {
        /* 췤 필요없을듯
        if(isMainImageReg) {
            Map<String, Object> productImgMap = this.getCopyProductImageList(prevPrdNo, newPrdNo);
            this.insertProductImg((ProductImageVO)productImgMap.get("productImgBO"), null); // 신규테이블 저장
        }
        */
        productVO.setPrdNo(newPrdNo);
        productVO.setCreateNo(productVO.getUpdateNo());

        String realHardImagePath = PropertiesManager.getProperty("productImageUploadRoot.path"); // 물리적으로 저장될 root Path(고정) /data1/upload
        String dbImagePath  = this.getImageStoragePath(newPrdNo, ImageDir.SMART_OPTION);
        // 옵션 이미지 정보를 복사
        PdOptDtlImage productOptDtlImageBO = new PdOptDtlImage();
        productOptDtlImageBO.setPrdNo(prevPrdNo);
        if(!isCopyOptValueSeq) {
            productOptDtlImageBO.setOptItemNo(1);
        }
        List<PdOptDtlImage> preOptImageList = optionMapper.getProductOptImageList(productOptDtlImageBO);
        List<PdOptDtlImage> productOptImageList = new ArrayList<PdOptDtlImage>();

        for(PdOptDtlImage productOptDtlImage : preOptImageList) {
            PdOptDtlImage setProductOptDtlImageInfo = new PdOptDtlImage();
            setProductOptDtlImageInfo.setPrdNo(Long.valueOf(newPrdNo));
            setProductOptDtlImageInfo.setOptItemNo(productOptDtlImage.getOptItemNo());
            setProductOptDtlImageInfo.setOptValueNo(productOptDtlImage.getOptValueNo());
            setProductOptDtlImageInfo.setOptValueNm(productOptDtlImage.getOptValueNm());
            setProductOptDtlImageInfo.setCreateNo(productVO.getCreateNo());
            setProductOptDtlImageInfo.setUpdateNo(productVO.getUpdateNo());
            // pd_opt_value 대표이미지가 있을 경우 이미지 복사 후 db update
            if(productOptDtlImage.getDgstExtNm() != null && !"".equals(productOptDtlImage.getDgstExtNm())) {
                setProductOptDtlImageInfo.setDgstExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDgstExtNm(), realHardImagePath, dbImagePath, "summary"));
            }
            // pd_opt_dtl_image 값이 있을 경우 복사 후 db insert
            if(productOptDtlImage.getDtl1ExtNm() != null && !"".equals(productOptDtlImage.getDtl1ExtNm())) {
                setProductOptDtlImageInfo.setDtl1ExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDtl1ExtNm(), realHardImagePath, dbImagePath, "detail"));
            }
            if(productOptDtlImage.getDtl2ExtNm() != null && !"".equals(productOptDtlImage.getDtl2ExtNm())) {
                setProductOptDtlImageInfo.setDtl2ExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDtl2ExtNm(), realHardImagePath, dbImagePath, "detail"));
            }
            if(productOptDtlImage.getDtl3ExtNm() != null && !"".equals(productOptDtlImage.getDtl3ExtNm())) {
                setProductOptDtlImageInfo.setDtl3ExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDtl3ExtNm(), realHardImagePath, dbImagePath, "detail"));
            }
            if(productOptDtlImage.getDtl4ExtNm() != null && !"".equals(productOptDtlImage.getDtl4ExtNm())) {
                setProductOptDtlImageInfo.setDtl4ExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDtl4ExtNm(), realHardImagePath, dbImagePath, "detail"));
            }
            if(productOptDtlImage.getDtl5ExtNm() != null && !"".equals(productOptDtlImage.getDtl5ExtNm())) {
                setProductOptDtlImageInfo.setDtl5ExtNm(setOptionImage(newPrdNo, productOptDtlImage.getDtl5ExtNm(), realHardImagePath, dbImagePath, "detail"));
            }

            productOptImageList.add(setProductOptDtlImageInfo);
        }
        return productOptImageList;
    }

    /**
     * 이미지가 정사이즈가 아닐 경우, white background 처리
     *
     * 1.확장자확인(jpg/png파일일 경우 처리)
     * 2.이미지가 존재하는지 확인
     * 3.이미지 사이즈 확인(원본이미지가 정사이즈가 아닐 경우 처리)
     * 4.white image 처리할지 여부 확인
     * @param procFileName
     */
    public void doWhiteBgProc(String realImagePath, String procFileName ){
        try{
            //확장자명
            String fileRev = procFileName.substring(procFileName.lastIndexOf(".")+1).toLowerCase();
            if(fileRev.equals("jpg")||fileRev.equals("png")){

                File file = new File(realImagePath+"/"+procFileName);

                //boolean isExist = new File(realImagePath+"/"+procFileName).exists();
                //if(isExist){
                if(file.exists()) {
                    //기본이미지 정보 확인
                    Image img = new ImageIcon(realImagePath+"/"+procFileName).getImage();
                    int width  = img.getWidth(null);
                    int height = img.getHeight(null);

                    Dimension dim = new Dimension(width, height);

                    //이미지 사이즈 확인(원본이미지가 정사이즈가 아닐 경우 처리)
                    if(dim.width!=dim.height){
                        //정사이즈(가로/세로)
                        int reSize = dim.width>dim.height?dim.width:dim.height;

                        //최소한 이미지 크기는 300으로
                        boolean isSmall = false;

                        //Background image file(bgFile)
                        String strBgFile = fileProperty.getUploadPath()+"/white.jpg";
                        File bgFile = new File(strBgFile);

                        if (bgFile.exists()) {

                            Image src = new ImageIcon(bgFile.toURL()).getImage();
                            BufferedImage image = new BufferedImage(reSize,reSize,BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = image.createGraphics();

                            //background 이미지를 정사이즈로 그려줌
                            g.drawImage(src, 0, 0, reSize, reSize, null);

                            //시작점 좌표 체크
                            int x = 0;
                            int y = 0;

                            if(isSmall){
                                x = (reSize-dim.width)/2;
                                y = (reSize-dim.height)/2;
                            }else{
                                if(dim.width>dim.height){
                                    x = (dim.width-dim.width)/2;
                                    y = (dim.width-dim.height)/2;
                                }else{
                                    x = (dim.height-dim.width)/2;
                                    y = (dim.height-dim.height)/2;
                                }
                            }
                            //File bImgFile = new File(realImagePath+"/"+procFileName);
                            //Image bImg = ImageIO.read(bImgFile);
                            Image bImg = ImageIO.read(file);

                            g.drawImage(bImg, x, y, dim.width, dim.height, null);
                            g.dispose();

                            //File bgProcImgFile = new File(realImagePath+"/"+procFileName);
                            //ImageIO.write(image, fileRev,bgProcImgFile);
                            ImageIO.write(image, fileRev, file);
                        }
                    }
                }
            }

        }catch(Throwable t){
            log.warn("[Throwable]"+t);
        }
    }

    @Override
    public void convertRegInfo(ProductVO preProructVo, ProductVO productVO) {
        // SO logic + current logic
        /*
        ProductImageVO productImageVO = new ProductImageVO();
        //productImageVO.setPrdNo(productVO.getPrdNo());
        productImageVO.setPrdTypCd(productVO.getBaseVO().getPrdTypCd());
        productImageVO.setCreateCd(productVO.getBaseVO().getCreateCd());
        productImageVO.setSelMnbdNo(productVO.getSelMnbdNo());
        productImageVO.setImgFiles(productVO.getProductImageVO().getImgFiles());

        productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_NEW);
        if(productVO.isUpdate()) {
            productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_UPDATE);
        } else if("Y".equals(productVO.getPrdCopyYn())) {
            productImageVO.setReRegPrdNo(productVO.getReRegPrdNo());
            productImageVO.setUploadType(ImageCode.UPLOAD_TYPE_COPY);
        }
        productVO.setProductImageVO(productImageVO);
        */

        productVO.getProductImageVO().setPrdTypCd(productVO.getBaseVO().getPrdTypCd());
        productVO.getProductImageVO().setCreateCd(productVO.getBaseVO().getCreateCd());
        productVO.getProductImageVO().setSelMnbdNo(productVO.getSelMnbdNo());

        productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_NEW);
        if(productVO.isUpdate()) {
            productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_UPDATE);
        } else if("Y".equals(productVO.getPrdCopyYn())) {
            productVO.getProductImageVO().setReRegPrdNo(productVO.getReRegPrdNo());
            productVO.getProductImageVO().setUploadType(ImageCode.UPLOAD_TYPE_COPY);
        }
    }

    public void mergeImageInfoProcess(ProductVO preProructVO, ProductVO productVO) {
        try {
            productVO.getProductImageVO().setPrdNo(productVO.getPrdNo());
            this.processProductImage(productVO.getProductImageVO(), productVO);
        } catch(Exception e) {
            log.error("[ProductImageServiceImpl.mergeImageInfoProcess]"+e);
            throw new ProductException (e.getMessage(), e);
        }
    }


    public static byte[] decodeBase64ToBytes(String imageString) {
        if (imageString.startsWith(BASE_64_PREFIX_JPEG)){
            return java.util.Base64.getDecoder().decode(imageString.substring(BASE_64_PREFIX_JPEG.length()));
        }else if(imageString.startsWith(BASE_64_PREFIX_PNG)){
            return java.util.Base64.getDecoder().decode(imageString.substring(BASE_64_PREFIX_PNG.length()));
        }else if(imageString.startsWith(BASE_64_PREFIX_GIF)) {
            return java.util.Base64.getDecoder().decode(imageString.substring(BASE_64_PREFIX_GIF.length()));
        }else{
            throw new IllegalStateException("it is not base 64 string");
        }
    }
}
