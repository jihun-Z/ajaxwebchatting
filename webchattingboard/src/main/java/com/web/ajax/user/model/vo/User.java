package com.web.ajax.user.model.vo;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	public static final User builder = null;
	private String userID;
	private String userPassword;
	private String userName;
	private int userAge;
	private String userGender;
	private String email;
	private String profile;
	private String reProfile;

	
}
