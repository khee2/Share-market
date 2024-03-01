package com.example.board.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

@Mapper
public interface ReplyMapper {
	// 댓글 등록
	public int insert(ReplyVO reply);
	
	// 댓글 1개 조회
	public ReplyVO read(long rno);
	
	// 댓글 삭제
	public int delete(Long rno);
	
	// 댓글 업데이트
	public int update(ReplyVO reply);
	
	// 댓글 목록
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") long bno);
}
