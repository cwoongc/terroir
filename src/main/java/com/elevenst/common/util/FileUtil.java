package com.elevenst.common.util;

import com.elevenst.terroir.product.registration.product.exception.ProductException;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public final String PATH_GIFT = "/data1/upload/";

    public final String FILE_ERR01 = "파일 복사 오류. 원본 이미지 파일이 존재하지 않습니다. 파일명을 확인 해 주십시오. (파일명이 한글이 있는경우 이미지 업로드가 되지 않습니다.)";

    /**
     * 파일 업로드
     * @param fileName
     * @param path
     * @return
     * @throws ProductException
     */
    public static String upload(String fileName, String path) throws ProductException {

        return "";
    }

    public static boolean exist(String filepath) {
        return new File(filepath).exists();
    }
    
    /**
     * 파일 복사
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void copy(String srcPath, String destPath) throws IOException {
        FileUtils.copyFile(new File(srcPath), new File(destPath));
    }

    public static void copy(File srcPath, String destPath) throws IOException {
        FileUtils.copyFile(srcPath, new File(destPath));
    }

    /**
     * 파일 복사
     * @param files
     * @param destPath
     * @throws IOException
     */
    public static void copyFiles(File[] files, String destPath) throws IOException {
        for(File file : files){
            FileUtils.copyFileToDirectory(file, new File(destPath));
        }

    }

    /**
     * 파일 삭제
     * @param filePath
     * @throws IOException
     */
    public static void delete(String filePath) throws IOException {
        if(filePath == null){
            return;
        }
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        FileUtils.forceDelete(file);
    }

    /**
     * 파일 이동
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void move(String srcPath, String destPath) throws IOException {
        FileUtils.copyFile(new File(srcPath), new File(destPath));
        FileUtils.forceDelete(new File(srcPath));
    }

    /**
     * 파일타입 변환 MultipartFile -> File
     * @param multipartFile
     * @throws IllegalStateException
     * @throws IOException
     */
    public static File convertMultipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        File file = new File( multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

    /**
     * 파일명 가져오기
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath){
        return filePath != null && filePath.lastIndexOf('/') > 0 ? filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length()) : "";
    }

    /**
     * 확장자 가져오기
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName){
        return fileName != null && fileName.lastIndexOf('.') > 0 ? fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toLowerCase() : "";
    }
}
