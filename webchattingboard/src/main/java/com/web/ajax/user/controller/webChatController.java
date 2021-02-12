package com.web.ajax.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.web.ajax.user.model.service.WebChatService;
import com.web.ajax.user.model.vo.Chat;
import com.web.ajax.user.model.vo.User;

@Controller
@SessionAttributes(value= {"loginMember"})
public class webChatController {
	@Autowired
	WebChatService service;
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	@RequestMapping("/userRegister.do")
	public ModelAndView enrollMember(User user,ModelAndView mv
			) {
		System.out.println("user:"+user);
		if(user.getUserID() == null || user.getUserID().equals("")||
				user.getUserPassword() == null || user.getUserPassword().equals("")||
				user.getUserName() == null || user.getUserName().equals("")||
				user.getEmail() == null || user.getEmail().equals("")||
				user.getUserAge() == 0 
				) {
			mv.addObject("messageType","오류메시지");
			mv.addObject("messageContent","모든내용을 입력하세요 ");
			mv.setViewName("home");
			
		}else {
		
			String oriPw=user.getUserPassword();
			System.out.println("원본 패스워드:"+oriPw);
			//BcrptPasswordEncoder객체가 제공하는 메소드로 암호화처리하기
			//encode(암호화할값); ->암호화된 String 값을 반환을 함
			user.setUserPassword(pwEncoder.encode(oriPw));
			int result=service.insertUser(user);
			System.out.println("result:" +result);
			if(result == 1) {
				mv.addObject("messageType","성공메시지");
				mv.addObject("messageContent","회원가입에 성공했습니다. ");
				mv.setViewName("index");
				
			}
		}
			
		return mv;
	}
	@RequestMapping("/member/checkDuplicateId.do")
	@ResponseBody
	public int streamAjax(String userID,Model m) throws IOException{
		System.out.println("userID:"+userID);
		 int result=service.checkId(userID);
		 System.out.println("result:"+result);
		return result ;
	}
	@RequestMapping("/member/login.do")
	public String loginMember(String userID,String userPassword,Model mv) {
		
		User login=service.selectOneMember(userID);
		
		String loc="";
		if(login != null && pwEncoder.matches(userPassword,login.getUserPassword())) {
			mv.addAttribute("loginMember",login);
			loc="redirect:/";
			
		}else {
			mv.addAttribute("msg","로그인 실패");
			mv.addAttribute("loc","/");
			loc="common/msg";
		
		}
		return loc;
		
	}
	@RequestMapping("")
	public String chat(Chat chat) {
		
		Chat map=service.chat();
		List<Chat> chatList=new ArrayList<Chat>();
		chat.setChatId(map.getChatId());
		chat.setFromId(map.getFromId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setToId(map.getToId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setChatContent(map.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		int chatTime= Integer.parseInt(map.getChatTime().substring(11,13));
		String timeType="오전";
		if(chatTime >=12) {
			timeType ="오후";
			chatTime-= 12;
		}
		chat.setChatTime(map.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+map.getChatTime().substring(14,16)+"");
		chatList.add(chat);
		return "";
	}
	@RequestMapping("")
	public String getChatListByRecent(Chat chat) {
		
		Chat map=service.chat();
		List<Chat> chatList=new ArrayList<Chat>();
		chat.setChatId(map.getChatId());
		chat.setFromId(map.getFromId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setToId(map.getToId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		chat.setChatContent(map.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
		int chatTime= Integer.parseInt(map.getChatTime().substring(11,13));
		String timeType="오전";
		if(chatTime >=12) {
			timeType ="오후";
			chatTime-= 12;
		}
		chat.setChatTime(map.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+map.getChatTime().substring(14,16)+"");
		chatList.add(chat);
		return "";
	}
	@RequestMapping("")
	public int submit(Chat chat) {
		
		int result=service.submit(chat);
		return result;
	}
}
