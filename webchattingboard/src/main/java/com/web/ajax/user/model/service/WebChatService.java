package com.web.ajax.user.model.service;

import java.util.ArrayList;
import java.util.List;

import com.web.ajax.user.model.vo.Chat;
import com.web.ajax.user.model.vo.User;

public interface WebChatService {

	int insertUser(User user);

	User selectOneMember(String userID);

	int checkId(String userID);

	int submit(Chat chat);

	int selectOneUser(String userID, String userPassword);

	List<Chat> getChatListByRecent(String fromID, String toID, int number);

	List<Chat> getChatListByID(String fromID, String toID,String chatID);

	int userRegisterCheck(String userID);

	Chat getChat(String fromID, String toID);

	Object readChat(String fromID, String toID);


}
