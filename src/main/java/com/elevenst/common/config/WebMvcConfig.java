package com.elevenst.common.config;

import com.elevenst.common.interceptor.LoginCheckInterceptor;
import com.elevenst.common.interceptor.CORSInterceptor;
import com.elevenst.common.listener.ConveyorContextListener;
import com.elevenst.common.listener.ESOCacheContextListener;
import com.elevenst.common.properties.CommonUploadProperties;
import com.elevenst.common.properties.ImgUploadProperties;
import com.elevenst.common.support.AuthHandlerMethodArgumentResolver;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	ImgUploadProperties imgUploadProperties;

	@Autowired
	CommonUploadProperties commonUploadProperties;

	@Autowired
	Gson gson;


	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

		GsonHttpMessageConverter gconveter = new GsonHttpMessageConverter();
		gconveter.setGson(gson);
		converters.add(gconveter);

	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**", "/static/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/", "classpath:/static/");

		registry.addResourceHandler("/editorImg/**")
				.addResourceLocations("file:"+ imgUploadProperties.getServer().getPath()+"/editorImg/");

	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(corsInterceptor());
		registry.addInterceptor(loginCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");

	}

	@Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	    argumentResolvers.add(authHandlerMethodArgumentResolver());
    }


	@Bean
	public CORSInterceptor corsInterceptor() {
		return new CORSInterceptor();
	}

	@Bean
	public LoginCheckInterceptor loginCheckInterceptor() { return new LoginCheckInterceptor(); }

	@Bean
    public AuthHandlerMethodArgumentResolver authHandlerMethodArgumentResolver() { return  new AuthHandlerMethodArgumentResolver(); }




	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> registrationListenerBean() {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new ESOCacheContextListener());
		return bean;
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> registrationConveyorListenerBean() {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new ConveyorContextListener());
		return bean;
	}

	@Bean
	public ServletContextInitializer initializer() {
		return new ServletContextInitializer() {
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				servletContext.setInitParameter("PropertyFileName", "config/properties/esocache/[$server_type].esocache.properties");
				servletContext.setInitParameter("ConveyorContextPropertiesPath", "config/properties/conveyor/conveyor.context.properties");
			}
		};
	}



	/*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".html");
		viewResolver.setContentType("text/html; charset=utf-8");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/index.html");
		super.addViewControllers(registry);
	}




	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/resources/pageNotFound.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/resources/pageNotFound.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.PAYMENT_REQUIRED, "/resources/pageNotFound.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/resources/pageNotFound.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/resources/pageNotFound.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/resources/pageNotFound.jsp"));

				container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/resources/internalError.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_IMPLEMENTED, "/resources/internalError.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, "/resources/internalError.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/resources/internalError.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.GATEWAY_TIMEOUT, "/resources/internalError.jsp"));
			}
		};
	}

	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}
	*/

}
