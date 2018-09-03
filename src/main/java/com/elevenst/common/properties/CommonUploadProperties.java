package com.elevenst.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "common.upload")
public class CommonUploadProperties {

    private String url;
    private String path;

}