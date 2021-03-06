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
	public List<Chat> getChatListByRecent(SqlSession session, String fromID, String toID,String listType) {
		
		int chatId=Integer.parseInt(listType);
		Chat chat=new Chat();
		chat.setFromID(fromID);
		chat.setToID(toID);
		chat.setChatId(chatId);
		
		// TODO Auto-generated method stub
		return session.selectList("user.getChatListByRecent",chat);
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
		return session.update("user.readChat",map);
	}

	@Override
	public int getAllUnreadChat(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.getAllUnreadChat",userID);
	}

	@Override
	public User selectUser(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.selectUser",userID);
	}

	@Override
	public List<Chat> getBox(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectList("user.getBox",userID);
	}

	@Override
	public int getUnreadChat(SqlSession session, String fromID, String toID) {
		// TODO Auto-generated method stub
		Chat chat=new Chat();
		chat.setFromID(fromID);
		chat.setToID(toID);
		return session.selectOne("user.getUnreadChat",chat);
	}

	@Override
	public int update(SqlSession session, User user) {
		// TODO Auto-generated method stub
		return session.update("user.update",user);
	}

	@Override
	public int profileUpdate(SqlSession session, User user) {
		// TODO Auto-generated method stub
		return session.update("user.profileUpdate",user);
	}

	@Override
	public User getFile(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectOne("user.getFile",userID);
	}

}
