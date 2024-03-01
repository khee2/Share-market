package com.example.board.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.beans.dao.MemberDAO;
import com.example.board.beans.vo.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private final MemberDAO dao;

	public boolean getId(MemberDto dto) {
		int n = dao.getId(dto);
		return n > 0;
	}

	public boolean addMember(MemberDto dto) {
		int n = dao.addMember(dto);
		return n > 0;
	}

	public String login(MemberDto dto) throws Exception {
		return dao.login(dto);
	}
}
