package com.example.board.beans;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.dao.ReplyDAO;
import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReplyDAOTest {
	private Long[] arBno = { 66L, 65L, 64L, 63L, 62L }; // 게시물을 달 이름 , 10번 테스트 각각 두번씩

	@Autowired
	private ReplyDAO replyDAO;

	@Test
	public void testReplyDAO() {
		log.info(replyDAO.toString());
	} // replyDAO 있기는 있나?

	// @Test
	public void testInsert() {
		// 5개 게시물에 3개씩 댓글 달기
		IntStream.rangeClosed(1, 15).forEach(i -> {
			ReplyVO reply = new ReplyVO();

			reply.setBno(arBno[i % 5]);
			reply.setReply("댓글 테스트-DAO-" + i);
			reply.setReplier("guest" + i);

			log.info("Insert Count: " + replyDAO.register(reply));

		}); // 1~15까지의 집합에 위 문장 실행해라.
	}

	// @Test
	public void testGet() {
		Long targetRno = 30L;
		ReplyVO reply = replyDAO.get(targetRno);
		log.info(reply.toString()); // @Data => getter, setter 뿐만 아니라 내가 가지고 있는 필드들을 override한 toString()도 등장.
	}

	// @Test
	public void testRemove() {
		long targetRno = 35L;
		replyDAO.remove(targetRno);
	}

	// @Test
	public void testModify() {
		Long targetRno = 30L;

		ReplyVO reply = replyDAO.get(targetRno); // 일단 가져와서 고칠부분은 고치고 안 고칠부분은 그대로 나두기.
		reply.setReply("Reply Updated"); // Reply를 "Reply Updated"로 바꿈.

		int count = replyDAO.modify(reply); // 지금은 한 건 처리
		log.info("update count: " + count);
	}

	@Test
	public void testGetList() {
		Criteria cri = new Criteria(); // 파라미터에 아무것도 안 줬으므로 기본 값 1페이지 10개
		List<ReplyVO> replies = replyDAO.getList(cri, 66L); // Bno == 66L 개수만큼 reply달려야함
		replies.forEach(reply -> log.info(reply.toString()));
	}

}
