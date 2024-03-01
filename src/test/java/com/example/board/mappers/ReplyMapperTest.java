package com.example.board.mappers;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReplyMapperTest {
	@Autowired
	private ReplyMapper mapper;
	
	// @Test
	public void testMapper() {
		log.info(mapper.toString());
	}
	
	private Long[] arBno = {66L, 65L, 64L, 63L, 62L}; // 게시물을 달 이름 , 10번 테스트 각각 두번씩
	
	// @Test
	public void testInsert() {
		IntStream.rangeClosed(1,10).forEach(i -> { // 1~10까지
			ReplyVO reply = new ReplyVO();
			
			reply.setBno(arBno[i % 5]);
			reply.setReply("댓글 테스트" + i); // 1,2,3,4,0,1,2,3,4,0 순서로 설정
			reply.setReplier("replier" + i);
			
			mapper.insert(reply);
		});
	}
	// @Test
	public void testRead() {
		Long targetRno = 8L;
		ReplyVO reply = mapper.read(targetRno);
		log.info(reply.toString());
	}
	
	// @Test
	public void testDelete() {
		Long targetRno = 9L;
		mapper.delete(targetRno);
	}
	
	// @Test
	public void testUpdate() {
		long targetRno = 8L;
		
		ReplyVO reply = mapper.read(targetRno);
		if (reply != null) {
			reply.setReply("Update OK"); 
			reply.setReplier("Test Ok");
			int count = mapper.update(reply);
			log.info("Update Count : " +count);
		}
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, arBno[0]); 
		replies.forEach(reply -> log.info(reply.toString()));
	}
}

