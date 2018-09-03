package com.elevenst.terroir.product.registration.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "milab.category.svr")
@Data
public class UrlProperty implements Serializable {
    private static final long serialVersionUID = -2875165318251443352L;

    private String url;
}
