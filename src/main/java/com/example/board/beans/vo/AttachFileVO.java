package com.example.board.beans.vo;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AttachFileVO {
	private String fileName;
	private boolean image; // 이미지가 맞으면 true 반환, 아니면 false 반환
	private String uuid;
	private String uploadPath;
	private Long bno;
}
