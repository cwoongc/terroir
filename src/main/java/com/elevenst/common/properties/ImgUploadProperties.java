package com.elevenst.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "img.upload")
public class ImgUploadProperties {

    private String domain;

    private final Server server = new Server();


    @Data
    public static class Server {

        private String path;

    }


}
