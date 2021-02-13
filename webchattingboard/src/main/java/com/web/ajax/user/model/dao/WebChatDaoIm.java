package com.web.ajax.user.model.dao;

import java.util.List;
import java.util.TreeMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.web.ajax.user.model.vo.Chat;
import com.web.ajax.user.model.vo.User;

@Repository
public class WebChatDaoIm implements WebChatDao {

	@Override
	public int insertUser(SqlSession session, User user) {
		// TODO Auto-generated method stub
		return session.insert("user.insertUser",user);
	}

	@Override
	public User selectOneMember(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.selectOneMember",userID);
	}

	@Override
	public int checkId(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		System.out.println("userId:"+userID);
		return session.selectOne("user.checkId",userID);
	}

	@Override
	public int submit(SqlSession session, Chat chat) {
		// TODO Auto-generated method stub
		return session.insert("user.submit",chat);
	}

	@Override
	public List<Chat> chat(SqlSession session, Chat chat) {
		// TODO Auto-generated method stub
		return session.selectList("user.chat",chat);
	}

	@Override
	public User selectOneUser(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.selectOneUser",userID);
	}

}
