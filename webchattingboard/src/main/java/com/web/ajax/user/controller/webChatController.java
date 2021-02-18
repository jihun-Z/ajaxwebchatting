package com.web.ajax.user.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
@SessionAttributes(value= {"userID"})
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
			loc="redirect:/";
			
		}else {
			mv.addAttribute("msg","로그인 실패");
			mv.addAttribute("loc","/");
			loc="common/msg";
		
		}
		return loc;
		
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
	@RequestMapping("/find.do")
	public String findFriend() {
		
		return "find";
	}
	@RequestMapping("/chat.do")
	public ModelAndView chatPage(String toID,ModelAndView mv ) {
		System.out.println("toID"+toID);
		mv.addObject("toID",toID);
		mv.setViewName("chat");
		return mv;
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
		else {
			
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
		}
		System.out.println("mv:"+mv);
		return mv;
	}
	
	
	//chatSubmitServlet
	@ResponseBody
	@RequestMapping("/chatSubmit.do")
	public int chatSubmit(String fromID,String toID,String chatContent) {
		int result;
		Chat chat=new Chat();
		System.out.println("toID: "+toID);
		System.out.println("chat:"+chat);
		if(fromID==null|| fromID.equals("")||
				toID ==null || toID.equals("")||
						chatContent == null|| chatContent.equals("")) {
			result=0;
			System.out.println(" submit result:"+result);
		}else {
			chat.setFromID(fromID);
			chat.setToID(toID);
			chat.setChatContent(chatContent);
			result=service.submit(chat);
		}
		System.out.println(" submit result:"+result);
		return result;
	}
	//chatlistServlet 
	@ResponseBody
	@RequestMapping("/chatList.do")
	public void chatList(String fromID,String toID,String listType,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");;
		System.out.println(""+listType);
		if(fromID==null || fromID.equals("")|| toID==null || toID.equals("")||
				listType==null|| listType.equals("")) {
			
			response.getWriter().write("");
		}
	
				// TODO Auto-generated catch block
		
		else if(listType.equals("ten"))
	
				response.getWriter().write(getTen(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8")));
		else {
			try {
				response.getWriter().write(getID(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8"),listType));
			}catch(Exception e) {
					response.getWriter().write("");
				}
		}
	}

	public String getTen(String fromID,String toID) {
		System.out.println("getTen: 실행");
		StringBuffer result=new StringBuffer();
		
		result.append("{\"result\":[");
		List<Chat> chatList=service.getChatListByRecent(fromID,toID);
		if(chatList.size() ==0 ) return "";
		for(int i = 0; i <chatList.size(); i++) {
			result.append("[{\"value\":\""+chatList.get(i).getFromID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getToID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() -1 ) result.append(",");//더있다면 ,를찍어라
		}
		result.append("],\"last\":\""+chatList.get(chatList.size() -1)
		.getChatId()+"\"}");
		System.out.println(" getTen result:"+result.toString());
		System.out.println("getTen 실행:"+(service.readChat(fromID,toID)));
		return result.toString();//문자열로 반환해준다.
		
	}
	public String getID(String fromID,String toID, String listType) {
		System.out.println("getID: 실행");
		StringBuffer result=new StringBuffer();
		result.append("{\"result\":[");
		List<Chat> chatList=service.getChatListByID(fromID,toID,listType);
		if(chatList.size() ==0 ) return "";
		for(int i = 0; i <chatList.size(); i++) {
			result.append("[{\"value\":\""+chatList.get(i).getFromID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getToID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() -1 ) result.append(",");//더있다면 ,를찍어라
		}
		result.append("],\"last\":\""+chatList.get(chatList.size() -1)
		.getChatId()+"\"}");
		System.out.println("getId result:"+result.toString());
		
		System.out.println("getID 실행:"+(service.readChat(fromID,toID)));
		return result.toString();//문자열로 반환해준다.
		
	}
	@ResponseBody
	@RequestMapping("/userRegisterCheck.do")
	public int userRegisterCheck(String userID,Model m) {
		int result;
		if(userID==null || userID.equals("")) result=-1;
		
		result=service.userRegisterCheck(userID);
		return result;
	}
	@RequestMapping("")
	public ModelAndView getAllUnreadChat(String userID,ModelAndView mv) {
		int result=service.getAllUnreadChat(userID);
		if(result == 0) {
			mv.addObject("count","0");
			mv.addObject("messageType","오류메세지");
			mv.addObject("messageContent","메세지가 없습니다.");
			mv.setViewName("index");
		}else {
			mv.addObject("count",result);
			mv.addObject("messageType","성공메세지");
			mv.addObject("messageContent","안 읽은 메세지가 있습니다.");
			mv.setViewName("index");
			
		}
		return mv;
	}
	
}
