package com.example.board.mybatis;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration  // 내가 설정하는 클래스라는 걸 알려주고
@RequiredArgsConstructor // 필요한 Argument를 만들겠다. 
@MapperScan("com.example.board.mappers") // @mapper 딱지가 붙은 걸 찾으로 감.
public class MyBatisConfig {
	// 커넥션 풀 및 MyBatis에 필요한 요소를 메모리에 할당 및 관리, xml과 java연동에 필요한 경로 관리
	private final ApplicationContext applicationContext;

	// @Bean을
	// @Configuration 또는 @Component가 작성된 클래스의 메서드에 사용하게 되면
	// 메서드의 실행결과(리턴값)을 스프링 컨테이너(객체들이 들어가는 공간)에 등록
	// 객체명은 메서드의 이름으로 자동 생성, 직접 설정하려면 @Bean(name="객체명")으로 사용
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")  // 상위경로 고정
	public HikariConfig hikariConfig() { return new HikariConfig(); }
	// application.properties에 작성된 JDBC datasource 정보 설정
	
	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	} // 위에서 만든 hikariConfig()를 수행한 결과를 던짐/ 하는 일은 DataSource 객체에 DBMS 정보 설정
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws IOException {
		// 세션 팩토리 객체 생성
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		// 위에서 설정한 datasource 객체를 세션 팩토리 설정에 전달
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		// SQL 쿼리를 작성할 xml 경로 설정
		sqlSessionFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath*:/mappers/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(
		        applicationContext.getResource("classpath:/config/config.xml"));
		try {
			// Session factory 생성
			SqlSessionFactory factory = sqlSessionFactoryBean.getObject();
			
			// 카멜 표기법으로 자동 변경
			factory.getConfiguration().setMapUnderscoreToCamelCase(true);
			
			return factory; // 팩토리를 만들어서 나중에 factory가 실제 application이 들어오면 그때 session들을 만들어줌.
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
@Configuration
class WebConfig implements WebMvcConfigurer {
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/image/**")
            .addResourceLocations("file:///upload/");
}
}
