// oracle에서 table이 만들어졌으면 통신하기 위한 VO를 생성해야 한다.
package com.example.board.beans.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private long rno; // reply number
	private long bno; // board number

	private String reply;
	private String replier;
	private String replyDate;
	private String updateDate;
}
