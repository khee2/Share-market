package com.example.board.beans.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.beans.vo.AttachFileVO;
import com.example.board.mappers.AttachFileMapper;

@Repository
public class AttachFileDAO {
	@Autowired
	private AttachFileMapper attachFileMapper;

	public void insert(AttachFileVO vo) {
		attachFileMapper.insert(vo);
	}
	public List<AttachFileVO> findByBno(Long bno){
		return attachFileMapper.findByBno(bno);
	}
}
