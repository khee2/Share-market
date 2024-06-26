package com.example.board.services;

import java.util.List;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;

public interface ReplyService {
	public int register(ReplyVO reply);

	public ReplyVO get(Long rno);

	public int modify(ReplyVO reply);

	public int remove(Long rno);

	public List<ReplyVO> getList(Criteria cri, Long bno);
}
