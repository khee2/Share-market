package com.example.board.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.beans.dao.ReplyDAO;
import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private final ReplyDAO replyDAO;

	@Override
	public int register(ReplyVO reply) {
		log.info("Service register..."+reply);
		return replyDAO.register(reply);
	}
	@Override
	public ReplyVO get(Long rno) {
		log.info("Service get...." + rno);
		return replyDAO.get(rno);
	}
	@Override
	public int modify(ReplyVO reply) {
		log.info("Service modify...." + reply);
		return replyDAO.modify(reply);
	}
	@Override
	public int remove(Long rno) {
		log.info("Service remove..." + rno);
		return replyDAO.remove(rno);
	}
 // DAO vs service: romove + modify => 서비스를 호출하는 사람들이 1개만 호출하는 것이 아니기 때문
	// dao : service mapping 1:n // dao : mapper : n:1 도 가능.
	public List<ReplyVO> getList(Criteria cri, Long bno){
		log.info("Service Reply List of a board: " + bno);
		return replyDAO.getList(cri, bno);
		// one query : 쿼리 하나로 
	}
}
