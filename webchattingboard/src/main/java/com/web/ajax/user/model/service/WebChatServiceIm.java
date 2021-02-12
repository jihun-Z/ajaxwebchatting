package com.web.ajax.user.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ajax.user.model.dao.WebChatDao;
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

}
