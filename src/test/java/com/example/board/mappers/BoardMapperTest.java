package com.example.board.mappers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.vo.BoardVO;
import com.example.board.beans.vo.Criteria;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardMapperTest {

	@Autowired
	private BoardMapper mapper;
	
	//@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board.toString()));
	}
	
	
	// @Test
	public void testGetListWithPaging() {
		Criteria cri = new Criteria(2, 2);
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info("BNO : " + board.getBno()));
	}
	
	// @Test
	/*
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성한 글 제목");
		board.setContent("새로 작성한 글 내용");
		board.setWriter("user01");
		
		mapper.insert(board);
	}
	*/
	@Test
	public void testInsertSelectKey_bno() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성한 글 제목");
		board.setContent("새로 작성한 글 내용");
		board.setWriter("user02");
		
		mapper.insertSelectKey_bno(board);
	}
	
	//@Test
	public void testRead() {
		String str = mapper.read(1L).toString(); 
		log.info("BoardVO : " + str);
	}
	
	//@Test
	public void testDelete() {
		log.info("Delete : " + mapper.delete(3L));
	}
	
	//@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();

		// 실행 전 존재하는 글인지 확인
		Long bno = 1L;
		if(mapper.read(bno) != null) {
			board.setBno(bno);
			board.setTitle("나지완 전격은퇴");
			board.setContent("이대호도 오늘 은퇴");
			board.setWriter("Eagles");
		
			int count = mapper.update(board);
			log.info("Update Count : " + count);
		}
		else {
			log.info("게시물 미존재");
		}
	}
}
