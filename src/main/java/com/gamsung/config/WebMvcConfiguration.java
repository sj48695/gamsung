package com.gamsung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer { // 이 설정 클래스가 Spring MVC 설정 클래스의 역할

	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//
//		registry.addInterceptor(new LogInterceptor()); // loginterceptor를 등록 해놓음.
//	}
	
	//apache commons-fileupload를 사용해서 파일 업로드 하는 객체
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 5); // 파일 하나 당 업로드 가능한 Size를 지정해 줌.
		return resolver;
	}
}

