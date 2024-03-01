package com.example.board.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TimeMapperTest {
	@Autowired
	private TimeMapper timeMapper;
	
	//TimeMapper timeMapper = new TimeMapper(); 등 안해도 됨 
	@Test
	public void testGetTime() {
		String sysdate = timeMapper.getTime();
		log.info("---------------------------");
		log.info("sysdate : " + sysdate);
		log.info("---------------------------");
		
	}
	
}
