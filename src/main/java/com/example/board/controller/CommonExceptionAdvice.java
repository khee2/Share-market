package com.example.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {
	
	// Exception 처리
	@ExceptionHandler(Exception.class) // 오류 발생시 처리함.
	public String except(Exception e, Model model) {
		log.error("Exception ....... " + e.getMessage()); // log level이 error가 됨.
		// Exception으로부터 무슨 오류인지 얻어옴
		model.addAttribute("exception", e);
		log.error(model.toString());
		return "thymeleaf/error_page";
	}
	// NoHandler 처리
	@ExceptionHandler(NoHandlerFoundException.class) // Exception을 모르는 ex) 모르는 페이지를 찾았다든지
	@ResponseStatus(HttpStatus.NOT_FOUND) // 페이지가 없는 애를 찾았을 때 처리하는 애
	public String handle404(NoHandlerFoundException e) {
		return "thymeleaf/error_page";
	}
}
