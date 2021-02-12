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
	private String fromId;
	private String toId;
	private String chatContent;
	private String chatTime;
}
