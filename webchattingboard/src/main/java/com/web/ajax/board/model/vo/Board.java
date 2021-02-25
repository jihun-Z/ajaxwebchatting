package com.web.ajax.board.model.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class Board {
	
	private String userID;
	private int boardNo;
	private String  boardTitle;
	private String boardContent;
	private Date boardDate;
	private int boardHit;
	private int boardGroup;
	private int boardSequence;
	private int boardLevel;

}
