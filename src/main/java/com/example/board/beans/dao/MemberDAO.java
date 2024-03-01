package com.example.board.beans.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.beans.vo.MemberDto;
import com.example.board.mappers.MemberMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor


public class MemberDAO {	
	@Autowired
	private MemberMapper mapper;
	public int getId(MemberDto dto) {
		return mapper.getId(dto);
	}

	public int addMember(MemberDto dto) {
		return mapper.addMember(dto);
	}

	public String login(MemberDto dto){
		return mapper.login(dto);
	}
}
