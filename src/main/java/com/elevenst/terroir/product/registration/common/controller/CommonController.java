package com.elevenst.terroir.product.registration.common.controller;


import com.elevenst.common.util.cache.ObjectCacheImpl;
import com.elevenst.terroir.product.registration.common.service.CommonServiceImpl;
import com.elevenst.terroir.product.registration.common.vo.HelpPopVO;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonController {

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    ObjectCacheImpl objectCache;

    @GetMapping("/getHelpPopCont/{code}")
    public HelpPopVO getPrdCtlgAttrVal(@PathVariable String code ) {
        return commonService.getHelpPopCont(code);
    }

    @PostMapping("/getHelpPopContList")
    public List<HelpPopVO> getHelpPopContList(@RequestBody List<String> codeList) {
        boolean requestAll = false;

        if(codeList == null) codeList = new ArrayList<>();
        List<HelpPopVO> helpPopList = null;
        String cacheKey = "getHelpPopContList_All";

        if(codeList.size() == 0){
            requestAll = true;
            helpPopList = (List<HelpPopVO>)objectCache.getObject(cacheKey);
            if(helpPopList != null){
                return helpPopList;
            }

            codeList.add("81015");
            codeList.add("74001");
            codeList.add("0102G");
            codeList.add("0105G");
            codeList.add("74002");
            codeList.add("0107G");
            codeList.add("74003");
            codeList.add("0108G");
            codeList.add("0104G");
            codeList.add("82016");
            codeList.add("93022");
            codeList.add("0116G");
            codeList.add("0115G");
            codeList.add("0117G");
        }

        helpPopList = commonService.getHelpPopContList(codeList);
        if(requestAll){
            objectCache.setObject(cacheKey, helpPopList, 86400);
        }
        return helpPopList;
    }
}
