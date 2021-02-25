package com.web.ajax.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {
	
	private int attachMentNo;
	private int boardNo;
	private String boardFile;
	private String boardReFile;
	private Date uploadDate;
	private int downLoadCount;

}
