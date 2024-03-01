package com.example.board.beans;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.dao.BoardDAO;
import com.example.board.beans.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

// 어노테이선 알아두기
@SpringBootTest 
@Slf4j
public class BoardDAOTest{
	@Autowired
	BoardDAO boardDAO;
	
	@Disabled // 여기 테스트 안 함.
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO(); // test용도로 잠깐 만듦
		board.setTitle("오늘 새로 작성1");
		board.setContent("진짜임1");
		board.setWriter("Erik기자1");
		
		boardDAO.register(board);
		log.info("생성된 게시물의 번호 : " + board.getBno());
	}
	
	// @Test
	public void testGet() {
		log.info("testGet: "+boardDAO.get(1L).toString()); // bno는 long 타입이므로
	}
	// @Test
	public void getList() {
		// 가져온 값마다 똑같은 일을 해야 한다면.. => forEach 사용하기
		boardDAO.getList().forEach(board -> log.info("testGetList: "+ board.toString())); // bno는 long 타입이므로
	}
	// @Test
	public void testModify() {
		BoardVO board = new BoardVO();
		board.setTitle("제목 수정");
		board.setContent("오늘 수정한 내용");
		board.setWriter("익명의 기자");
		board.setBno(1L);
		
		log.info("Modify: " + boardDAO.modify(board));
		// 수정 되면 true 반환
	}
	@Test
	public void getRemove() {
		boardDAO.remove(122L);

	}
}
