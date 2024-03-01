package com.example.board.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.beans.vo.BoardVO;
import com.example.board.beans.vo.Criteria;

@Mapper
public interface BoardMapper {
	// 게시글 목록
	public List<BoardVO> getList();
	public List<BoardVO> getListWithPaging(Criteria cri);
	// PageObject pageObject가 Criteria cri임.
	// 게시글 등록(PK 가져오기) write
	public void insertSelectKey_bno(BoardVO board);
	
	// 게시글 보기 view
	public BoardVO read(Long bno);
	
	// 게시글 삭제
	// 성공 시 삭제한 건수 리턴, 실패 시 0 리턴
	public int delete(Long bno);
	
	// 게시글 수정
	// 수정완료 시 수정한 건수 리턴, 실패시 0 리턴
	public int update(BoardVO board);
	
	// 게시글 개수 getTotalRow
	public int getTotal(Criteria cri);
	
	// 조회수 1증가
	public int increase(Long bno);
}
