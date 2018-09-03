package com.elevenst.terroir.product.registration.rmaterial.service;

import com.elevenst.terroir.product.registration.product.entity.PdPrdRmaterial;
import com.elevenst.terroir.product.registration.product.entity.PdPrdRmaterialIngred;
import com.elevenst.terroir.product.registration.product.service.ProductServiceImpl;
import com.elevenst.terroir.product.registration.product.vo.ProductRmaterialVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class RmaterialServiceTest {

	@Autowired
	ProductServiceImpl productService;


	@Test
	public void test01_insertRmaterial() {
		long prdNo = 1245252486;
		productService.deleteProductRmaterial(prdNo);

		List<PdPrdRmaterial> pdPrdRmaterialList = new ArrayList<>();
		List<PdPrdRmaterialIngred> pdPrdRmaterialIngredList = new ArrayList<>();
		for(int i=0; i<5; i++){
			int idx = i+1;
			PdPrdRmaterial pdPrdRmaterial = new PdPrdRmaterial();
			pdPrdRmaterial.setPrdNo(prdNo);
			pdPrdRmaterial.setRmaterialSeqNo(idx);
			pdPrdRmaterial.setRmaterialNm("원재료 "+idx);
			pdPrdRmaterial.setCreateNo(0);
			pdPrdRmaterial.setUpdateNo(0);
			pdPrdRmaterialList.add(pdPrdRmaterial);
			for(int j=0; j<2; j++){
				PdPrdRmaterialIngred pdPrdRmaterialIngred = new PdPrdRmaterialIngred();
				pdPrdRmaterialIngred.setPrdNo(prdNo);
				pdPrdRmaterialIngred.setRmaterialSeqNo(idx);
				pdPrdRmaterialIngred.setIngredSeqNo(j+1);
				pdPrdRmaterialIngred.setIngredNm("원재료성분 "+idx+" - "+(j+1));
				pdPrdRmaterialIngred.setOrgnCountry("소말리아");
				pdPrdRmaterialIngred.setContent(10);
				pdPrdRmaterialIngredList.add(pdPrdRmaterialIngred);
			}
		}
		productService.insertProductRmaterial(pdPrdRmaterialList, pdPrdRmaterialIngredList);

//		ProductRmaterialVO productRmaterialVO = productService.getProductRmaterialList(1245252486);

//		assertTrue(productRmaterialVO.getRmaterialList().size() == pdPrdRmaterialList.size());
//		assertTrue(productRmaterialVO.getRmaterialIngredList().size() == pdPrdRmaterialIngredList.size());

	}

	@Test
	public void test02_selectRmaterial() {

//		ProductRmaterialVO productRmaterialVO = productService.getProductRmaterialList(1245252486);

//		String res = "";
//		for (PdPrdRmaterial rmaterial : productRmaterialVO.getRmaterialList()) {
//			res += rmaterial.toString() + "\n";
//		}
//		res += "\n";
//		for (PdPrdRmaterialIngred rmaterialIngred : productRmaterialVO.getRmaterialIngredList()) {
//			res += rmaterialIngred.toString() + "\n";
//		}
//		log.info(res);
	}

	@Test
	public void test03_deleteRmaterial() {
		productService.deleteProductRmaterial(1245252486);

//		ProductRmaterialVO productRmaterialVO = productService.getProductRmaterialList(1245252486);

//		assertTrue(productRmaterialVO.getRmaterialList().size() == 0);
//		assertTrue(productRmaterialVO.getRmaterialIngredList().size() == 0);
	}

}
