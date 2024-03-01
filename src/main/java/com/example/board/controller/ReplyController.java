package com.example.board.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.ReplyVO;
import com.example.board.services.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController // ViewResolver를 사용하지 않고 리턴 값을 그대로 사용한다.
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/replies/*")

public class ReplyController {
	private final ReplyService replyService;

	// 댓글 등록
	// 브라우저에서 JSON타입으로 데이터를 전송하고
	// 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 전송한다.
	// consumes : Ajax를 통해 전달받은 데이터의 타입
	// produces : Ajax의 success: function(result)에 있는 result로 전송할 데이터 타입
	// @ResponseBody : @Controller에서 Body를 응답하기 위해(viewResolver를 가지 않게 하기 위해서)

	// 문자열 전달 시 한글이 깨지는 것을 막기 위해 text/plain; charset=utf-8을 작성한다.
	@PostMapping(value = "/new", consumes = "application/json", produces = "text/plain; charset= utf-8")

	// ResponseEntity : 서버의 상태코드, 응답 메시지 등을 담을 수 있는 타입
	// @RequestBody : json 형태로 오지만, 나는 몸체에 해당하는 거 requestBody -> ReplyVO형태로 받겠다.
	public ResponseEntity<String> create(@RequestBody ReplyVO reply) throws UnsupportedEncodingException {
		int insertCnt = 0;
		log.info("ReplyVO : " + reply);
		insertCnt = replyService.register(reply); // 웹에서 호출 (Service)-> 등록 가능. (db가면 들어가져있음.)
		// 누군가 JSON 타입을 줬을 때 내가 ReplyVO 형태로 받을 수 있다.
		if (insertCnt == 1)
			return new ResponseEntity<>(new String("댓글 등록 성공".getBytes(), "UTF-8"), HttpStatus.OK);
		// 성공
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 server Error
		// 실페
	}

	// 게시글 댓글 전체 조회
	@GetMapping("pages/{bno}/{page}")
	// pathvariable을 사용해서 위의 bno와 page를 가져온다.
	public List<ReplyVO> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
		log.info("getList....");
		Criteria cri = new Criteria(page, 10);
		log.info(cri.toString());

		return replyService.getList(cri, bno);
	}

	// 댓글 조회
	@GetMapping("{rno}")
	public ReplyVO get(@PathVariable("rno") Long rno) {
		log.info("get....");
		return replyService.get(rno);
	}

	// 댓글 수정
	@PatchMapping(value = { "/{rno}", "/{rno}/{replier}" },
			consumes = "application/json",
			produces = "text/plain; charset=utf-8")
	public String modify(@RequestBody ReplyVO reply, @PathVariable(value = "replier", required = false) String replier, // 컨텐츠																									// false))
			@PathVariable("rno") Long rno) { // 컨텐츠만 수정
		reply.setRno(rno);

		log.info("rno : " + rno);
		log.info("modify : " + reply);

		if (reply.getReplier() == null) // json 검증 replier가 없으면
			reply.setReplier("anonymous"); // 임의의 사람
		return replyService.modify(reply) == 1 ? "댓글 수정 성공" : "댓글 수정 실패"; // reply 수정 후
	}
	
	// 댓글 삭제
	@DeleteMapping(value = "{rno}", 
			produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) throws UnsupportedEncodingException {
		log.info("remove : " + rno);

		return replyService.remove(rno) == 1
				? new ResponseEntity<>(new String("댓글 등록 성공".getBytes(), "UTF-8"), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
