package com.example.board.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.beans.vo.MemberDto;

@Mapper
public interface MemberMapper {    
	public int getId(MemberDto dto);

	public int addMember(MemberDto dto);

	public String login(MemberDto dto);
}
