package com.elevenst.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tomcat.ajp")
@Data
@Slf4j
public class TomcatAjpConfig {

    private boolean enable;
    private String protocol;
    private int port;
    private boolean secure;
    private boolean allowTrace;
    private String scheme;


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        log.info("enable: "+enable );
        log.info("protocol: "+protocol );
        log.info("port: "+port );
        log.info("secure: "+secure );
        log.info("allowTrace: "+allowTrace );
        log.info("scheme: "+scheme );

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        if (enable)
        {
            Connector ajpConnector = new Connector(protocol);
            ajpConnector.setPort(port);
            ajpConnector.setSecure(secure);
            ajpConnector.setAllowTrace(allowTrace);
            ajpConnector.setScheme(scheme);
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }


}
