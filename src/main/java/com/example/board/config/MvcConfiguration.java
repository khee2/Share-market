package com.example.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정 파일
public class MvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// html, css, img 등을 변경했을 때 서버 내렸다 올리지 않아도 구동이 가능. 캐시 저장되어 있음.
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60*60*24*365); // 위치와 갱신주기 설정하기
		// 60초 60분 24시간 365
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60*60*24*365);
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60*60*24*365);
//		registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60*60*24*365);
	}
}
