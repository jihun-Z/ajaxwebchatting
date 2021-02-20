package com.web.ajax.user.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	BCryptPasswordEncoder pwEncoder;
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
				
				//if(user.getUserPassword().equals(userPassword)) {
					if(user != null && pwEncoder.matches(userPassword,user.getUserPassword())) {
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
		List<Chat> map=dao.getChatListByRecent(session,fromID,toID);
		System.out.println("Map[Service]:"+map);
		
		List<Chat> chatList=new ArrayList<Chat>();
		for(Chat chat1: map) {
		
				Chat chat=new Chat();
				
				chat.setChatId(chat1.getChatId());
				chat.setFromID(chat1.getFromID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setToID(chat1.getToID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setChatContent(chat1.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				String timeChat=chat1.getChatTime();
				int chatTime= Integer.parseInt(chat1.getChatTime().substring(11,13));
				String timeType="오전";
				if(chatTime >=12) {
					timeType ="오후";
					chatTime-= 12;
				}
				chat.setChatTime(chat1.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+chat1.getChatTime().substring(14,16)+"");
				chatList.add(chat);
			
		}
		System.out.println("service ten : "+chatList);
		return chatList;
	}
	@Override
	public List<Chat> getBox(String userID) {
		List<Chat> map=dao.getBox(session,userID);
		System.out.println("Map[Service]:"+map);
		List<Chat> chatList=new ArrayList<Chat>();
		for(Chat chat1: map) {
			Chat chat=new Chat();
			
				chat.setChatId(chat1.getChatId());
				chat.setFromID(chat1.getFromID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setToID(chat1.getToID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setChatContent(chat1.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				String timeChat=chat1.getChatTime();
				int chatTime= Integer.parseInt(chat1.getChatTime().substring(11,13));
				String timeType="오전";
				if(chatTime >=12) {
					timeType ="오후";
					chatTime-= 12;
				}
				chat.setChatTime(chat1.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+chat1.getChatTime().substring(14,16)+"");
				chatList.add(chat);
				System.out.println("get box"+chatList);
			
		}
		for(int i=0;i<chatList.size();i++) {
			Chat x=chatList.get(i);
			for(int j=0;j< chatList.size(); j++) {
				Chat y=chatList.get(j);
				//두개의메세지를 비교
				//M메세지의 보내는 사람이 x 다른메세지의 받는 사람이 y 와 같고
				//다른 메세지 받는사람 y가 특정메세지의 보내는사람과 같은지 비교
				if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
					if(x.getChatId() < y.getChatId()) {
						chatList.remove(x);
						i--;
						break;
					}else {
						chatList.remove(y);
						j--;
					}
				}
			}
		}
		System.out.println("service getBox : "+chatList);
		return chatList;
	}
	@Override
	public List<Chat> getChatListByID(String fromID, String toID, String chatID) {
		List<Chat> map=dao.getChatListByID(session,fromID,toID,chatID);
	
		List<Chat> chatList=new ArrayList<Chat>();
		for(Chat chat1: map) {
			Chat chat=new Chat();
				
				chat.setChatId(chat1.getChatId());
				chat.setFromID(chat1.getFromID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setToID(chat1.getToID().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				chat.setChatContent(chat1.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
				int chatTime= Integer.parseInt(chat1.getChatTime().substring(11,13));
				String timeType="오전";
				if(chatTime >=12) {
					timeType ="오후";
					chatTime-= 12;
				}
				chat.setChatTime(chat1.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+chat1.getChatTime().substring(14,16)+"");
				chatList.add(chat);
		}
		System.out.println("service ID : "+chatList);
		return chatList;
	}
	@Override
	public int userRegisterCheck(String userID) {
		// TODO Auto-generated method stub
		return dao.userRegisterCheck(session,userID);
	}
	@Override
	public Chat getChat(String fromID, String toID) {
		// TODO Auto-generated method stub
		return dao.getChat(session,fromID,toID);
	}
	@Override
	public Object readChat(String fromID, String toID) {
		// TODO Auto-generated method stub
		return dao.readChat(session,fromID,toID);
	}
	@Override
	public int getAllUnreadChat(String userID) {
		// TODO Auto-generated method stub
		return dao.getAllUnreadChat(session,userID);
	}
	@Override
	public User selectUser(String userID) {
		// TODO Auto-generated method stub
		return dao.selectOneUser(session, userID);
	}
	@Override
	public int getUnreadChat(String fromID, String toID) {
		// TODO Auto-generated method stub
		return dao.getUnreadChat(session,fromID,toID);
	}
	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return dao.update(session,user);
	}
	@Override
	public int profileUpdate(User user) {
		// TODO Auto-generated method stub
		return dao.profileUpdate(session,user);
	}

	

}
