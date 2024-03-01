package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude= {DataSourceAutoConfiguration.class}) DB 연결은 안하도록..
// 스프링 부트로 동작하는 프로그램이라는 걸 명시 
// 설정 파일이 자동 적용되어서 사용 가능.
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args); // 해당 클래스를 그대로 인수로 지정함.
	}
}
