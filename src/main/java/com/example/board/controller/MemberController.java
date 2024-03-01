package com.example.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.beans.vo.MemberDto;
import com.example.board.services.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MemberController {
	
	@Autowired
    private final MemberService service;
	private final String MODULE = "manage";

    @PostMapping("/getId")
    public String getId(MemberDto dto) {
        log.info("MemberController getId()");
        boolean b = service.getId(dto);
        if(b) { // true인 경우
            return "no"; // 아이디 중복
        }
        return "ok";
    }
    @PostMapping("/addMember")
    public String addMember(MemberDto dto) {
        log.info("MemberController addMember()");
        boolean b = service.addMember(dto);

        log.info(dto.toString());

        if(b) { // 회원가입이 완료되어 카운트 된 숫자가 0보다 클 경우 true를 반환
            return "ok";
        }
        return "no";
    }
    // 로그인한 정보를 세션에 객체로 저장해두기 위해 MemberDto를 반환하여 프론트엔드단까지 가져감.
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(MemberDto dto, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
        log.info("MemberController login()");
        HttpSession session = req.getSession();
        log.info("넘어온 ID"+dto.getId());
        log.info("넘어온 PW"+dto.getPwd());
        String name = service.login(dto);
        log.info(name);

        if (name == null) {
        	session.setAttribute("member", null);
        	rttr.addFlashAttribute("msg", false);
        } else {
        	session.setAttribute("member", name);
        }
        return "redirect:/login.do";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:board/list.do";
	}
    @GetMapping("/login.do")
    public String loginForm() {
    	log.info("로그인됨");
		return "login";
    }
    @GetMapping("/join.do")
    public String joinForm() {
		return "thymeleaf/" + MODULE + "/join";
    }
}