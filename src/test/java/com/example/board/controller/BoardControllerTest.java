package com.example.board.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.board.BoardApplication;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { BoardApplication.class })
@Slf4j
public class BoardControllerTest {
	// 가짜 MVC
	// 마치 브라우저에서 URL을 요청한 것처럼 환경 생성
	// 웹에서 호출하지 않고 내부적으로 호출시킴.
	private MockMvc mockMvc;
	// 요청을 처리해주는 WebApplicationContext를 불러온다.
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach // 다른 테스트보다 우선해서 생성한다. before <=> After
	// 함수가 여러 개가 붙을 수 있으니까 Each
	// Each(각각마다 실행됨.) <=> All (한 번만 실행됨.)
	public void setUp() {
		// 가짜 MVC에 WebApplicationContext를 전달 후 환경 생성
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// 환경만드는 건 그냥 Builder (MockMvcBuilders) vs 요청에 대해서 만듦.(MockMvcRequestBuilders)
	// @Test
	public void testList() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).toString()// .andReturn().getModelAndView().getModelMap().toString()
		); // get방식으로 board/list를 호출한 뒤 return 받는 거에 Model과 view를 얻어옴. 그 중 ModelMap만 가지고 와서
			// string을 찍음.
	}

	// @Test
	public void testRegister() throws Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "테스트 새 글 제목")
				.param("content", "테스트 새 글 내용")
				.param("writer", "Cat")).toString();
		// register라는 서비스한테 parameter를 세 개 던짐
		log.info(result);
	}
	// @Test
	public void testGet() throws Exception {
		log.info(mockMvc
				.perform(MockMvcRequestBuilders.get("/board/get")
						.param("bno", "24")).andReturn().toString()
		);
	}

	// @Test
	public void testModify() throws Exception {
		String result = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/modify")
						.param("bno", "2")
						.param("title", "수정된 새 글 제목")
						.param("content", "수정된 새 글 내용")
						.param("writer", "Dog"))
				.andReturn().getModelAndView().getModelMap().toString();

		log.info(result);
	}

	// @Test
	public void testRemove() throws Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "22")).andReturn()
				.getFlashMap().toString();

		log.info(result);
	}

}
