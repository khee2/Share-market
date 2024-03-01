package com.example.board.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.beans.vo.AttachFileVO;

@Mapper
public interface AttachFileMapper {
	public void insert(AttachFileVO vo);
	public void delete(String vo);
	public List<AttachFileVO> findByBno(Long bno);
}
