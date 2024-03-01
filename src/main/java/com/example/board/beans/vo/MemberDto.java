package com.example.board.beans.vo;

import lombok.Getter;

@Getter
public class MemberDto {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private int auth;

	public MemberDto() {
	}

	public MemberDto(String id, String pwd, String name, String email, int auth) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.auth = auth;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "id='" + id + '\'' + ", pwd='" + pwd + '\'' + ", name='" + name + '\'' + ", email='" + email + '\''
				+ ", auth=" + auth;
	}
}