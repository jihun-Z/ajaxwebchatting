package com.web.ajax.user.model.service;

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


}
