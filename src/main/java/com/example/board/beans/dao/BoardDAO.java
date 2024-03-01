package com.example.board.beans.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.beans.vo.BoardVO;
import com.example.board.beans.vo.Criteria;
import com.example.board.mappers.BoardMapper;

import lombok.RequiredArgsConstructor;

@Repository // 관리를 다른 곳에서 함. 생성을 spring framework에서 하니까 가져다 씀.
// 가져다 쓴다는 걸 명시를 해줘야 함. (AutoWired)
@RequiredArgsConstructor
public class BoardDAO {
    @Autowired
	private BoardMapper mapper;
	
	public void register(BoardVO board) {
		mapper.insertSelectKey_bno(board);
	}
	
	// 글 볼 때 조회수를 1 증가한다. 수정에서 글 보기 올 때는 increase하면 안되므로 
	// 변수 한 개를 줘서 확인하는 작업을 거친다.
	public BoardVO get(Long bno) {
		return mapper.read(bno);
	}
	public void increase(Long bno) {
		mapper.increase(bno);
	}
	public boolean modify(BoardVO board) {
		return mapper.update(board) != 0;
	}
	
	public boolean remove(Long bno) {
		return mapper.delete(bno) != 0; // 못지우면 false 리턴, 지우면 true 리턴
	}
	
	public List<BoardVO> getList(){
		return mapper.getList();
	}
	
	public List<BoardVO> getList(Criteria cri){
		return mapper.getListWithPaging(cri);
	}
	
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
}
