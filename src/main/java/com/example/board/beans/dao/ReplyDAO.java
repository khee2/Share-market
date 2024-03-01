package com.example.board.beans.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;
import com.example.board.mappers.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReplyDAO {
	@Autowired
	private final ReplyMapper mapper; // mapper 가져오기

	public int register(ReplyVO reply) { // reply주면 등록해줌.
		log.info("register......" + reply);
		return mapper.insert(reply);
	}

	public ReplyVO get(Long rno) { // rno을 주면 해당하는 ReplyVO 줌.
		log.info("register......" + rno);
		return mapper.read(rno);
	}

	public int modify(ReplyVO reply) { // reply주면(rely 내에는 rno가 들어있음. 어떤 부분을 수정할지 모르기에 통째로 줌.) 수정해줌.
		log.info("modify......" + reply);
		return mapper.update(reply);
	}

	public int remove(Long rno) { // rno주면 제거해줌.
		log.info("register......" + rno);
		return mapper.delete(rno);
	}

	public List<ReplyVO> getList(Criteria cri, Long bno) { // replyVO가 여러 개 들어간 list, bno는 여러 개 있을 수 있으므로(rno는 항상 한개)
															// bno로 가져와야 함.
		log.info("get Reply List of Board......" + bno);
		return mapper.getListWithPaging(cri, bno);
	}
}
