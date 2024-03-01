package com.example.board.beans.vo;

import lombok.Data;

@Data
public class Criteria { // 검색의 기준
	private int pageNum;
	private int amount;
	private String type;
	private String keyword;

	public Criteria() {
		this(1, 10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public String[] getTypeArr() {
		// split("") : 구분자 없이 모든 문자 분리. "ABC".split("") = {"A", "B", "C"}
		return type == null ? new String[] {} : type.split("");
	}

}
