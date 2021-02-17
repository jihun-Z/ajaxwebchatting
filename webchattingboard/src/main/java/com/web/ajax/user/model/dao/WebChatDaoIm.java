package com.web.ajax.user.model.dao;

import java.util.ArrayList;
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

	@Override
	public List<Chat> getChatListByRecent(SqlSession session, String fromID, String toID) {
		TreeMap<String,String> map=new TreeMap<String,String>();
		map.put("fromID",fromID);
		map.put("toID",toID);
		
		// TODO Auto-generated method stub
		return session.selectList("user.getChatListByRecent",map);
	}

	@Override
	public List<Chat> getChatListByID(SqlSession session, String fromID, String toID, String chatId) {
	Chat chat=new Chat();
	chat.setFromID(fromID);
	chat.setToID(toID);
	chat.setChatId(Integer.parseInt(chatId));
		// TODO Auto-generated method stub
		return session.selectList("user.getChatListByID",chat);
	}

	@Override
	public int userRegisterCheck(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.userRegisterCheck",userID);
	}

	@Override
	public Chat getChat(SqlSession session, String fromID, String toID) {
		TreeMap<String,String> map=new TreeMap<String,String>();
		map.put("fromID",fromID);
		map.put("toID",toID);
		// TODO Auto-generated method stub
		return session.selectOne("user.getChat",map);
	}

	@Override
	public Object readChat(SqlSession session, String fromID, String toID) {
		// TODO Auto-generated method stub
		TreeMap<String,String> map=new TreeMap<String,String>();
		map.put("fromID",fromID);
		map.put("toID",toID);
		return session.insert("user.readChat",map);
	}

	@Override
	public int getAllUnreadChat(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.getAllUnreadChat",userID);
	}

}
