package com.elevenst.terroir.product.registration.common.controller;

        import com.elevenst.common.util.StringUtil;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonZoneCheck {

    @GetMapping("/jsp/zone/check")
    public String getZoneType() {
        return "terroir was " + StringUtil.nvl(System.getProperty("zone.type"));
    }
}
