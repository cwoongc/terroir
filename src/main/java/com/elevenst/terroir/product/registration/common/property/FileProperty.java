package com.elevenst.terroir.product.registration.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "product.file")
@Data
public class FileProperty {
    private String uploadPath;
    private String tempPath;
    private String giftPath;
}
