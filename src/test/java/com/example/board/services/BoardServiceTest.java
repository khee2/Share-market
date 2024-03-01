package com.example.board.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardServiceTest {
	@Autowired
	BoardServiceImpl boardService;
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("새로운 글 생성");
		board.setContent("새로운 내용 생성");
		board.setWriter("Unknown");
		
		boardService.register(board);
		log.info("생성 게시물 번호 : " + board.getBno());
	}
	
	@Test
	public void testGet() {
		log.info(boardService.get(1L, 0).toString());
	}

	@Test
	public void testGetList() {
		boardService.getList().forEach(board -> log.info(board.toString()));
	}

	@Test
	public void testModify() {  // 게시물 2L 수정 테스트
		BoardVO board = boardService.get(2L, 0);
		
		if(board == null) { // get에서 board를 가지고 왔는데, 만약 board가 없으면
			return;
		}
		// board가 있으면 수정한다.
		board.setTitle("제목 또 수정합니다");
		log.info("Modify : " + boardService.modify(board));
		
	}
	
	@Test
	public void testRemove() {
		log.info("Remove : " + boardService.remove(21L));
	}
}
