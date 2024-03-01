package com.example.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.beans.dao.AttachFileDAO;
import com.example.board.beans.vo.BoardVO;
import com.example.board.beans.vo.Criteria;
import com.example.board.beans.vo.PageDTO;
import com.example.board.services.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor // 자동으로 argument를 만들어주는 역할
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private final BoardService service; // 수정 불가능하게 만듦.
	private final AttachFileDAO attachFile; // 수정 불가능하게 만듦.

	private final String MODULE = "board";
	
	@GetMapping("")
	public String mainhome(Criteria cri, Model model) {
		model.addAttribute("list", service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotal(cri)));
		return "mainhome";
	}
	
	@GetMapping("list.do")
	public String list(Criteria cri, Model model) { // model => 특정 속성을 붙였다 뗐다 할 수 있는 
		model.addAttribute("list", service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotal(cri)));
		return "thymeleaf/" + MODULE + "/list";
	}
	
	@GetMapping("first_page.do")
	public String first_page() { // model => 특정 속성을 붙였다 뗐다 할 수 있는 
		return "thymeleaf/"+ MODULE + "/first_page";
	}
	
	// increase가 없으면 0으로 들어오고, 있으면 그 값 넘겨줌.
	@GetMapping("get.do") 
	public String get(@RequestParam("bno") Long bno, @RequestParam(defaultValue = "0") int inc, Model model) {
		// log.info("no=" + bno + ", inc=" + inc);
		model.addAttribute("board", service.get(bno, inc));
		// List<AttachFileVO> afv = attachFile.findByBno(bno);
		// AttachFileVO afvo = (AttachFileVO)afv.get(0);
		// log.info(afvo.getFileName());
		model.addAttribute("filethum", attachFile.findByBno(bno));

		return "thymeleaf/"+ MODULE + "/get";
	}
	// 글쓰기 form
	@GetMapping("register.do")
	public String registerForm(HttpServletRequest request, RedirectAttributes rttr) {
		// return "thymeleaf/" + MODULE + "/register";
		HttpSession session = request.getSession();
		if (session.getAttribute("member") == null) {
			rttr.addFlashAttribute("msg2", "로그인하십시오");
	        return "redirect:/login.do";
		}
		return "/reg";
	}
	
	// 글쓰기 처리
	// submit을 하면 post로 전송하기 때문에 등록 시작
	@PostMapping("register.do")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("등록 board=" + board);
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach -> log.info(attach.toString()));
			log.info("하하호호");
		}
		service.register(board);
		// 새롭게 등록된 번호와 같이 전달하기 위해 RedirectAttribute를 이용
		rttr.addFlashAttribute("bno", board.getBno());
		rttr.addFlashAttribute("msg", "게시판 글등록이 되었습니다.ㅎㅎ");
		// rttr.addFlashAttribute("filethum2", attachFile.findByBno(board.getBno())); // 이 방법은 잠깐만 보이게 해주므로

		return "redirect:list.do";
		
	}
	// 글수정 form
	@GetMapping("modify.do") 
	public String modifyForm(@RequestParam("bno") Long bno, Model model) {
		log.info("수정 board=" + bno);
		model.addAttribute("board", service.get(bno, 0)); // 0이면 increase하지 않는다.
		return "thymeleaf/"+ MODULE + "/modify";
	}
	
	// 글수정 처리
	@PostMapping("modify.do")
	public String modify(BoardVO board, RedirectAttributes rttr) {		
		if(service.modify(board)) { // 성공하면 true가 들어옴.
			log.info(service.modify(board)+"하하하하하항");
			// addAttribute()는 GET방식으로 QueryString에 전달
			// addFlashAttribute()는 POST 방식으로 Session에 저장되어 전달
			rttr.addAttribute("result", "success");
			rttr.addFlashAttribute("msg", "게시판 글수정이 되었습니다.");
			//rttr.addAttribute("board", service.get(board.getBno()));
		}
		return "redirect:get.do?bno=" + board.getBno();
		// 수정 후 글 번호를 함께 넘겨줘야 함. 
	} 
	// 성공 or 실패를 팝업에..
	// 삭제 성공 시 result에 "success"를 flash에 담아서 전달
	// 삭제 실패 시 result에 "fail"를 flash에 담아서 전달
	@PostMapping("remove.do")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
			rttr.addFlashAttribute("msg", "게시판 글 삭제가 되었습니다.");
		}
		else {
			rttr.addFlashAttribute("result", "fail");
		}	
	return "redirect:list.do";
	}
}
