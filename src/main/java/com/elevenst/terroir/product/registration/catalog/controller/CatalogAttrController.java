package com.elevenst.terroir.product.registration.catalog.controller;

import com.elevenst.terroir.product.registration.catalog.service.CatalogAttrServiceImpl;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogAttrVO;
import com.elevenst.terroir.product.registration.catalog.vo.CatalogAttrValueVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/catalog/attr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CatalogAttrController {

    @Autowired
    CatalogAttrServiceImpl catalogAttrService;

    @GetMapping("/getPrdCtlgAttrVal/{prdNo}")
    public List<HashMap> getPrdCtlgAttrVal(@PathVariable long prdNo ) {
        return catalogAttrService.getProductCtlgAttrValList(prdNo);
    }

    @GetMapping("/getCtlgAttrVal/{ctlgNo}")
    public List<HashMap> getCtlgAttrVal( @PathVariable long ctlgNo ) {
        return catalogAttrService.getCtlgAttrValList(ctlgNo);

    }

    @GetMapping("/getAttrList/{dispCtgrNo}")
    public List<CatalogAttrVO> getCtgrStdAttrInfo(
                                @PathVariable long dispCtgrNo,
                                @RequestParam(name="prdNo", defaultValue = "0") long prdNo,
                                @RequestParam(name="ctlgNo", defaultValue = "0") long ctlgNo) {
        List<CatalogAttrVO> catalogAttrVOS = null;
        try {

            catalogAttrVOS = catalogAttrService.getCtgrAttrInfo(dispCtgrNo, prdNo, ctlgNo);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return catalogAttrVOS;
    }






}
