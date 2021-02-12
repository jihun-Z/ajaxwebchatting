package com.web.ajax.user.model.service;

import com.web.ajax.user.model.vo.User;

public interface WebChatService {

	int insertUser(User user);

	User selectOneMember(String userID);

	int checkId(String userID);

}
