package com.elevenst.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import skt.tmall.auth.Auth;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;



@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Swagger Terroir")
                        .description("Terroir Product Registration API Document")
                        .license("Apache 2.0")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .version("0.1.0")
                        .contact(new Contact("최웅철","","wcchoi@sk.com"))
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.elevenst.terroir.product.registration"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Auth.class)
                .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                        .name("Content-Type")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .description("Swagger global HTTP request header 'Content-Type'")
                        .required(true)
                        .defaultValue(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .allowableValues(new AllowableListValues(Arrays.asList(
                                MediaType.APPLICATION_JSON_UTF8_VALUE,
                                MediaType.APPLICATION_JSON_VALUE,
                                MediaType.APPLICATION_ATOM_XML_VALUE,
                                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                MediaType.APPLICATION_PDF_VALUE,
                                MediaType.APPLICATION_RSS_XML_VALUE,
                                MediaType.APPLICATION_XHTML_XML_VALUE,
                                MediaType.TEXT_HTML_VALUE,
                                MediaType.MULTIPART_FORM_DATA_VALUE,
                                MediaType.APPLICATION_XML_VALUE,
                                MediaType.IMAGE_GIF_VALUE,
                                MediaType.IMAGE_JPEG_VALUE,
                                MediaType.IMAGE_PNG_VALUE,
                                MediaType.TEXT_PLAIN_VALUE,
                                MediaType.TEXT_EVENT_STREAM_VALUE,
                                MediaType.TEXT_MARKDOWN_VALUE,
                                MediaType.TEXT_XML_VALUE
                        ),"String"))
                        .build())
                );
    }
}
