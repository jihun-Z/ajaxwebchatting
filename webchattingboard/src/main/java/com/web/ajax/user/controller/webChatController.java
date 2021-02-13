package com.web.ajax.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
			String userId=user.getUserID();
			User userID= service.selectOneMember(userId);
			System.out.println("result:" +result);
			if(result == 1) {
				System.out.println("index페이지");
				mv.addObject("userID",userID);
				mv.addObject("messageType","성공메시지");
				mv.addObject("messageContent","회원가입에 성공했습니다. ");
				mv.setViewName("index");
				
			}else {
				mv.addObject("messageType","오류메세지");
				mv.addObject("messageContent","이미 회원 등록 하셨습니다. ");
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
	@RequestMapping("/memberlogin.do")
	public String loginMember(String userID,String userPassword,Model mv) {
		if(userID ==null || userID.equals("")|| userPassword==null|| userPassword.equals("")) {
			mv.addAttribute("messageType","오류메시지");
			mv.addAttribute("messageContent","모든 내용을 입력하세요.");
			return "login";
		}
		User login=service.selectOneMember(userID);
		
		String loc="";
		if(login != null && pwEncoder.matches(userPassword,login.getUserPassword())) {
			mv.addAttribute("loginMember",login);
			mv.addAttribute("messageType","성공 메시지");
			mv.addAttribute("messageContent","로그인에 성공하였습니다.");
			//loc="redirect:/";
			loc="login";
			
		}else {
			mv.addAttribute("msg","로그인 실패");
			mv.addAttribute("loc","/");
			loc="common/msg";
		
		}
		return loc;
		
	}
//	@RequestMapping("")
//	public String chat(Chat chat) {
//		
//		Chat map=service.chat();
//		List<Chat> chatList=new ArrayList<Chat>();
//		chat.setChatId(map.getChatId());
//		chat.setFromId(map.getFromId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		chat.setToId(map.getToId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		chat.setChatContent(map.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		int chatTime= Integer.parseInt(map.getChatTime().substring(11,13));
//		String timeType="오전";
//		if(chatTime >=12) {
//			timeType ="오후";
//			chatTime-= 12;
//		}
//		chat.setChatTime(map.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+map.getChatTime().substring(14,16)+"");
//		chatList.add(chat);
//		return "";
//	}
//	@RequestMapping("")
//	public String getChatListByRecent(Chat chat) {
//		
//		Chat map=service.chat(chat);
//		List<Chat> chatList=new ArrayList<Chat>();
//		Chat catt=new Chat();
//		catt.setChatId(map.getChatId());
//		catt.setFromId(map.getFromId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		catt.setToId(map.getToId().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		catt.setChatContent(map.getChatContent().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>"));
//		int chatTime= Integer.parseInt(map.getChatTime().substring(11,13));
//		String timeType="오전";
//		if(chatTime >=12) {
//			timeType ="오후";
//			chatTime-= 12;
//		}
//		catt.setChatTime(map.getChatTime().substring(0,11)+" "+timeType+" "+chatTime+" : "+map.getChatTime().substring(14,16)+"");
//		chatList.add(catt);
//		return "";
//	}
	@ResponseBody
	@RequestMapping("/chatSubmit.do")
	public int submit(Chat chat, Model m) {
		if(chat.getFromID()== null|| chat.getFromID().equals("")
				||chat.getToID()==null|| chat.getToID().equals("")
				||chat.getChatContent()==null|| chat.getChatContent().equals("")) {
			m.addAttribute("result","0");
		}
		
		int result=service.submit(chat);
		System.out.println("result:"+result);
		return result;
	}
	
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}
	@RequestMapping("/home.do")
	public String enlloedMember() {
		return "home";
	}
	@RequestMapping("/login.do")
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping("/logoutAction.do")
	public String logoutAction(HttpSession session, SessionStatus ss) {
		//SessiontStatus객체를 이용해서 @SessionAttributes로 등록한값을 제거할수있음
		//-> SessionStatus.isComplete(); session이 존재하는지 확인.
		if(!ss.isComplete()) {
			//세션삭제하기 ->setcomplate();
			ss.setComplete();
		}
		if(session != null ) session.invalidate();
		//return"redirect:/";
		return "logoutAction";
	}
	@RequestMapping("/userlogin.do")
	public ModelAndView userlogin(String userID,String userPassword,ModelAndView mv) {
		if(userID ==null || userID.equals("")|| userPassword==null|| userPassword.equals("")) {
			mv.addObject("messageType","오류메시지");
			mv.addObject("messageContent","모든 내용을 입력하세요.");
			mv.setViewName("login");
		}
		int result=service.selectOneUser(userID,userPassword);
		System.out.println("result:"+result);
		String loc="";
		if(result == 1) {
			mv.addObject("userID",userID);
			mv.addObject("messageType","성공 메시지");
			mv.addObject("messageContent","로그인에 성공하였습니다.");
			//loc="redirect:/";
			mv.setViewName("index");
		}else if( result==2) {
			mv.addObject("messageType","오류 메시지");
			mv.addObject("messageContent","비밀번호를 다시 확인 하세요 ");
			mv.setViewName("login");
			loc="redirect:login";
		}else if(result==0) {
			mv.addObject("messageType","오류 메시지");
			mv.addObject("messageContent","아이디가 존재 하지 않습니다. ");
			mv.setViewName("login");
		}else if(result == -1) {
			mv.addObject("messageType","오류 메시지");
			mv.addObject("messageContent","데이터베이스 오류가 발생했습니다.");
			mv.setViewName("login");
		}
		System.out.println("mv:"+mv);
		return mv;
	}
}
