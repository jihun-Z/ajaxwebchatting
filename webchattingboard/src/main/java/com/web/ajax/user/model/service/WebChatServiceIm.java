package com.web.ajax.user.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ajax.user.model.dao.WebChatDao;
import com.web.ajax.user.model.vo.Chat;
import com.web.ajax.user.model.vo.User;

@Service
public class WebChatServiceIm implements WebChatService {
	
	@Autowired
	SqlSession session;
	@Autowired
	WebChatDao dao;
	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return dao.insertUser(session,user);
	}
	@Override
	public User selectOneMember(String userID) {
		// TODO Auto-generated method stub
		return dao.selectOneMember(session,userID);
	}
	@Override
	public int checkId(String userID) {
		// TODO Auto-generated method stub
		return dao.checkId(session,userID);
	}
	@Override
	public int submit(Chat chat) {
		// TODO Auto-generated method stub
		return dao.submit(session,chat);
	}
	@Override
	public int selectOneUser(String userID,String userPassword) {
		// TODO Auto-generated method stub
		
		int result;
		User user=dao.selectOneMember(session,userID);
		System.out.println("user:"+user);
			if(user != null) {
				
				if(user.getUserPassword().equals(userPassword)) {
					result=1;//로그인 성공
				}else {
					result=2;//비밀번호가 틀림
				}
			}else {
				
				result=0;//해당사용자가 존재하지않음
			}
		
		return result;
	}
	@Override
	public List<Chat> getChatListByRecent(String fromID, String toID) {
		// TODO Auto-generated method stub
		return dao.getChatListByRecent(session,fromID,toID);
	}
	@Override
	public List<Chat> getChatListByID(String fromID, String toID, String chatID) {
		Chat map=dao.getChatListByID(session,fromID,toID,chatID);
		Chat chat=new Chat();
		List<Chat> chatList=new ArrayList<Chat>();
		chat.setChatId(map.getChatId());
		chat.setFromID(map.getFromID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setToID(map.getToID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setChatContent(map.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		int chatTime= Integer.parseInt(map.getChatTime().substring(11,13));
		String timeType="오전";
		if(chatTime >=12) {
			timeType ="오후";
			chatTime-= 12;
		}
		chat.setChatTime(map.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+map.getChatTime().substring(14,16)+"");
		chatList.add(chat);
		
		return chatList;
	}
	@Override
	public int userRegisterCheck(String userID) {
		// TODO Auto-generated method stub
		return dao.userRegisterCheck(session,userID);
	}


}
