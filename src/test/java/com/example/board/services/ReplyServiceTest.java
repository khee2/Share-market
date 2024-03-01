package com.example.board.services;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReplyServiceTest {
	private Long[] arBno = { 66L, 65L, 64L, 63L, 62L }; // 게시물을 달 이름 , 10번 테스트 각각 두번씩

	@Autowired
	private ReplyService replyService;

	// @Test
	public void testReplyService() {
		log.info(replyService.toString());
	}

	// @Test
	public void testInsert() {
		// 5개 게시물에 4개씩 댓글 달기
		IntStream.rangeClosed(1, 20).forEach(i -> {
			ReplyVO reply = new ReplyVO();
			reply.setBno(arBno[i % 5]);
			reply.setReply("서비스로 만든 댓글" + i);
			reply.setReplier("service" + i);

			replyService.register(reply);
		});
	}

	// @Test
	public void testRead() {
		long targetRno = 22L;

		log.info("Service Test read : " + replyService.get(targetRno));
	}
	
	// @Test
	public void testDelete() {
		long targetRno = 42L;
		replyService.remove(targetRno);
	}
	
	// @Test
	public void testUpdate() {
		long targetRno = 53L;
		
		ReplyVO reply = replyService.get(targetRno);
		reply.setReply("Update Reply OOOO");
		
		int count = replyService.modify(reply);
		log.info("Service Test Update : " + count);
	}
	
	// @Test
	public void testList() {
		Criteria cri = new Criteria(1, 5); // 기본값은 10개만 가져옴. 가장 마지막 거 5개 가져오기 (인덱스를 거꾸로 읽도록 설정했기 때문)
		
		List<ReplyVO> replies = replyService.getList(cri, 63L);
		replies.forEach(reply -> log.info("Service Test getList : "+reply.toString()));
	}
}
