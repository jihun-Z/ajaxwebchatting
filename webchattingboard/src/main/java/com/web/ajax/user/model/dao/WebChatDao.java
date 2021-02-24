package com.web.ajax.user.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.web.ajax.user.model.vo.Chat;
import com.web.ajax.user.model.vo.User;

public interface WebChatDao {

	int insertUser(SqlSession session, User user);

	User selectOneMember(SqlSession session, String userID);

	int checkId(SqlSession session, String userID);

	int submit(SqlSession session, Chat chat);

	List<Chat> chat(SqlSession session, Chat chat);

	User selectOneUser(SqlSession session, String userID);

	int userRegisterCheck(SqlSession session, String userID);

	Chat getChat(SqlSession session, String fromID, String toID);

	List<Chat> getChatListByID(SqlSession session, String fromID, String toID, String chatId);

	Object readChat(SqlSession session, String fromID, String toID);

	int getAllUnreadChat(SqlSession session, String userID);

	User selectUser(SqlSession session, String userID);

	List<Chat> getBox(SqlSession session, String userID);

	int getUnreadChat(SqlSession session, String fromID, String toID);

	int update(SqlSession session, User user);

	int profileUpdate(SqlSession session, User user);

	User getFile(SqlSession session, String userID);

	List<Chat> getChatListByRecent(SqlSession session, String fromID, String toID, String listType);

}
