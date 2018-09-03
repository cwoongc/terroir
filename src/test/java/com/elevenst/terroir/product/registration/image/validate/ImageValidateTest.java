package com.elevenst.terroir.product.registration.image.validate;

import com.elevenst.terroir.product.registration.image.code.ImageKind;
import com.elevenst.terroir.product.registration.image.message.ImageExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest
public class ImageValidateTest {

	@Autowired
	ProductImageValidate productImageValidate;

	@Rule
	public ExpectedException expectedExcetption = ExpectedException.none();


	@Test
	public void checkImageExtension() {
        String[] files = {"custom.JPG", "custom.PNG", "custom.Jpg", "custom.png", "custom.jpg", "custom.jpeg"};

		for(int i=0, len=files.length; i<len; i++){
			productImageValidate.checkImageExtension(files[i], ImageKind.B);
		}
	}

	@Test
	public void checkImageGif() {
		String[] files = {"custom.JPG", "custom.PNG", "custom.Jpg", "custom.png", "custom.jpg", "custom.jpeg"};

		for(int i=0, len=files.length; i<len; i++){
			productImageValidate.checkGifImage(files[i]);
		}
	}

	@Test
	public void checkImageSize() throws IOException {

		String[] files = {"img/80x80.jpg",
				"img/600x600.jpg",
				"img/600x600.png"};

		for(int i=0, len=files.length; i<len; i++){
			File file = new ClassPathResource(files[i]).getFile();
			productImageValidate.checkImageSize(file.length(), ImageKind.B);
		}
	}

	@Test
	public void checkImageWH() throws IOException {

		//기본이미지
		String[] files = {"img/anklebreaker604x610.jpg",
				"img/600x600.jpg",
				"img/600x600.png"};

		for(int i=0, len=files.length; i<len; i++){
			File file = new ClassPathResource(files[i]).getFile();
			productImageValidate.checkImageWH(file.getPath(), ImageKind.B, -1, "");
			productImageValidate.checkImageWH(file.getPath(), ImageKind.WD, -1, "");
		}

		// 카드뷰 이미지
		String[] files2 = {"img/daeman720x360.jpg",
				"img/cardview720x360.png"};
		for(int i=0, len=files2.length; i<len; i++){
			File file = new ClassPathResource(files2[i]).getFile();
			productImageValidate.checkImageWH(file.getPath(), ImageKind.D, -1, "");
		}
	}

	@Test
	public void checkImageValid() throws IOException {
		String[] files = {"img/600x600.jpg", "img/cardview720x360.png", "img/anklebreaker604x610.jpg", "img/soonsal540x960.jpg", "img/daeman720x360.jpg", "img/dogbird468x751.png"};

		for(int i=0, len=files.length; i<len; i++){
			File file = new ClassPathResource(files[i]).getFile();
			productImageValidate.checkImageValidation(file.getPath());
		}
	}

	// exception test
	@Test
	public void checkWrongExtException() {
		expectedExcetption.expectMessage("확장명을 가진 이미지를 등록할 수 없습니다.");
        String[] files = {"custom.JPG", "custom.GIF", "custom.PNG", "custom.Jpg", "custom.gif", "custom.jpeg", "jpg.gif", "custom", "testjpg"};

		for(int i=0, len=files.length; i<len; i++){
			productImageValidate.checkImageExtension(files[i], ImageKind.B);
		}
	}

	@Test
	public void checkGifException() {
		expectedExcetption.expectMessage(ImageExceptionMessage.IMG_02);
		String[] files = {"custom.GIF", "custom.gif", "jpg.Gif", "jpg.jpg"};

		for(int i=0, len=files.length; i<len; i++){
			productImageValidate.checkGifImage(files[i]);
		}
	}

	@Test
	public void checkSizeException() throws IOException {
		expectedExcetption.expectMessage("사이즈 이상으로 등록해주세요.");

		String path = new ClassPathResource("img/dogbird468x751.png").getFile().getPath();
		productImageValidate.checkImageWH(path, ImageKind.B, -1, "");
	}
}
