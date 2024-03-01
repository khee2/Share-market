package com.example.board.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service // Spring Container에 Bean 등록
@Mapper // 내가 Mapper라는 걸 선언. MyBatis 연결 등록(xml의 id와 매핑을 하게 됨.)
public interface TimeMapper {
	//@Select("select sysdate from dual") // => TimeMapper.xml에서 처리함.
	public String getTime();
}
