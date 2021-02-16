package com.web.ajax.user.model.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Chat {
	private int chatId;
	private String fromID;
	private String toID;
	private String chatContent;
	private String chatTime;
	private int chatRead;
}
