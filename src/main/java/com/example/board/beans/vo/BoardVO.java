package com.example.board.beans.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

// VO는 테이블 모습과 똑같이 만들어놓음
@Component
@Data // 자동적으로 set, get, toString()이 만들어짐.
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Long price;
	private Long hit;
	private String regiCode;
	private Long cateCode;

	// input 태그의 name에 
	// attachList[i].fileName
	// attachList[i].uuid
	// attachList[i].uploadPath
	// attachList[i].fileType
	// 방식으로 submit을 하면 자동으로 List에 add
	private List<AttachFileVO> attachList;
	public String fileNameone; // 한 개의 파일만 저장
	public BoardVO() {}
}
