package com.web.ajax.user.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.web.ajax.user.model.vo.User;

public interface WebChatDao {

	int insertUser(SqlSession session, User user);

	User selectOneMember(SqlSession session, String userID);

	int checkId(SqlSession session, String userID);

}
