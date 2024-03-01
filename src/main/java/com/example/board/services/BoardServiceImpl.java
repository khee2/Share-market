package com.example.board.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.beans.dao.AttachFileDAO;
import com.example.board.beans.vo.BoardVO;
import com.example.board.beans.vo.Criteria;
import com.example.board.mappers.BoardMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	int i = 0 ;

	@Autowired
	private BoardMapper mapper;

	@Autowired
	private AttachFileDAO attachFileDAO;
	
	@Override
	public void register(BoardVO board) {
		mapper.insertSelectKey_bno(board);
		if(board.getAttachList() == null || board.getAttachList().size() == 0) {
			return;
		}
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			log.info("attachlist는?" + board.getAttachList());
			log.info("attachlist는?22" + attach);
			attachFileDAO.insert(attach); 
			log.info(i+"i가 뭐냐고");
			if (i < 1) { // 맨 처음 등록한 이미지만 리스트하도록 즉, 한개만 리스트하도록
				i++;
				log.info("i의 값은??hihi"+i+"");
				String a = "/image/" + attach.getUploadPath() + "/"+ attach.getFileName(); 
				board.setFileNameone(a); // set FileNameone
				log.info("a의 값은?" + a);
				log.info("저장되었니?" + board.fileNameone);
				mapper.update(board); // DB에 저장
			}
		});
		i=0; // i를 0으로 다시 setting하기
	}
	
	@Override
	public BoardVO get(Long bno, int inc) {
		if (inc == 1) mapper.increase(bno);
		return mapper.read(bno);
	}
	
	@Override
	public boolean modify(BoardVO board) {
		return mapper.update(board) != 0;
	}
	
	@Override
	public boolean remove(Long bno) {
		return mapper.delete(bno) != 0; // 못지우면 false 리턴, 지우면 true 리턴
	}
	
	@Override
	public List<BoardVO> getList() {
		return mapper.getList();
	}
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}
}
