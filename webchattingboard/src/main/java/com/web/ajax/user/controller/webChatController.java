package com.web.ajax.user.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
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
	public int chatSubmit(String fromID,String toID,String chatContent,HttpServletResponse response,HttpSession session) {
		int result;
		Chat chat=new Chat();
		System.out.println("toID: "+toID);
		System.out.println("chat:"+chat);
		if(fromID==null|| fromID.equals("")||
				toID ==null || toID.equals("")||
						chatContent == null|| chatContent.equals("")) {
			result=0;
			System.out.println(" submit result:"+result);
		}else if(fromID.equals(toID)) {
			result=-1;
		}
		else {
				try {
					if(!fromID.equals(session.getAttribute("userID"))) {
					response.getWriter().write("");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			
			}
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
	public void chatList(String fromID,String toID,String listType,
			HttpSession session,HttpServletResponse response) throws Exception {
		System.out.println("fromID"+fromID+"toID"+toID+"listType"+listType);
		response.setContentType("text/html;charset=UTF-8");;
		System.out.println(""+listType);
		if(fromID==null || fromID.equals("")|| toID==null || toID.equals("")||
				listType==null|| listType.equals("")) {
			
			response.getWriter().write("");
		}
	
				// TODO Auto-generated catch block
		
		else if(listType.equals("ten"))
	
				response.getWriter().write(getTen(fromID,toID));
		else {
			try {
				if(!fromID.equals(session.getAttribute("userID"))) {
					response.getWriter().write("");
					return;
				}
				response.getWriter().write(getID(fromID,toID,listType));
				System.out.println("실행");
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
		service.readChat(fromID,toID);
		return result.toString();//문자열로 반환해준다.
		
	}
	public String getID(String fromID,String toID, String listType) {
		System.out.println("getID: 실행");
		StringBuffer result=new StringBuffer();
		result.append("{\"result\":[");
		List<Chat> chatList=service.getChatListByID(fromID,toID,listType);
		if(chatList.size() ==0 ) return "";
		System.out.println("chatList: "+chatList);
		for(int i = 0; i <chatList.size(); i++) {
			result.append("[{\"value\":\""+chatList.get(i).getFromID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getToID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() -1 ) result.append(",");//더있다면 ,를찍어라
		}
		result.append("],\"last\":\""+chatList.get(chatList.size() -1)
		.getChatId()+"\"}");
		service.readChat(fromID,toID);
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
	@ResponseBody
	@RequestMapping("/chatUnread.do")
	public int getAllUnreadChat(String userID) {
		int result;
		if(userID == null || userID.equals("")) {
			result=0;
		}else {
			
			result=service.getAllUnreadChat(userID);
		}

		return result;
	}
	@ResponseBody
	@RequestMapping("/chatUnreadMessage.do")
	public int getUnreadChat(String fromID,String toID) {
		int result;
		if(fromID == null || fromID.equals("")||
				toID == null || toID.equals("")) {
			result=0;
		}else {
			
			result=service.getUnreadChat(fromID,toID);
		}
		
		return result;
	}
	@RequestMapping("/box.do")
	public String box() {
		return "box";
	}
	@ResponseBody
	@RequestMapping("/chatbox.do")
	public void getBox(String userID,HttpServletResponse response,HttpSession session,Model m) throws Exception {
		response.setContentType("text/html;charset=UTF-8");;
		if(userID==null || userID.equals("")) {
			response.getWriter().write("");
		}
		else {
			try {
				if(!userID.equals(session.getAttribute("userID"))) {
					response.getWriter().write("");
					return;
				}
				response.getWriter().write(getBoxx(userID));
			}catch(Exception e) {
					response.getWriter().write("");
				}
		}
	}
	public String getBoxx(String userID) {
		System.out.println("getID: 실행");
		StringBuffer result=new StringBuffer();
		result.append("{\"result\":[");
		Chat chat=new Chat();
		List<Chat> chatList=service.getBox(userID);
		if(chatList.size() ==0 ) return "";
		for(int i = chatList.size() -1; i >=0; i++) {
			String unread = "";
			if(userID.equals(chatList.get(i).getToID())) {
				unread= service.getUnreadChat(chatList.get(i).getFromID(),userID)+ "";
				if(unread.equals("0")) unread = "";
			}
			result.append("[{\"value\":\""+chatList.get(i).getFromID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getToID()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\":\""+chatList.get(i).getChatTime()+"\"},");
			result.append("{\"value\":\""+unread+"\"}]");
			if(i != 0 ) result.append(",");//더있다면 ,를찍어라
		}
		result.append("],\"last\":\""+chatList.get(chatList.size() -1)
		.getChatId()+"\"}");
		return result.toString();//문자열로 반환해준다.
	}
	@RequestMapping("/update.do")
	public ModelAndView updatePage(ModelAndView mv,String userID) {
		System.out.println("userID:"+userID);
		User user=service.selectUser(userID);
		mv.addObject("user",user);
		mv.setViewName("update");
		return mv;
	}
	@RequestMapping("/updateEnd.do")
	public ModelAndView updateUser(User user,@RequestParam(value="userPassword1") String userPassword1 , HttpSession session,ModelAndView mv) {
		if(user.getUserID()==null|| user.getUserID().equals("")||user.getUserPassword()==null||
				user.getUserPassword().contentEquals("")|| user.getUserName()==null|| user.getUserName().equals("")||
				user.getUserGender()==null||user.getUserGender().equals("")||
				user.getEmail()==null||user.getEmail().equals("")) {
			mv.addObject("messageType","오류메시지");
			mv.addObject("messageContent","모든 내용을 입력하세요.");
			mv.setViewName("update");
		}
		if(!user.getUserID().equals(session.getAttribute("userID"))) {
			mv.addObject("messageType","오류메세지");
			mv.addObject("messageContent","회원 아이디를 제대로 입력하세요. ");
			mv.setViewName("redirect:/update.do");
		
		}else if(!user.getUserPassword().equals(userPassword1)){
			mv.addObject("messageType","오류메세지");
			mv.addObject("messageContent","비밀번호가 틀립니다.");
			mv.setViewName("redirect:/update.do");
		}else {
			int result=service.update(user);
			if(result == 1) {
				
				mv.addObject("messageType","성공메세지");
				mv.addObject("messageContent","회원 정보를 수정하였습니다.");
				mv.addObject("1",result);
				mv.setViewName("login");
			}else if(result == 0){
				mv.addObject("messageType","오류메세지");
				mv.addObject("messageContent","회원정보 수정에 실패하였습니다.");
				mv.addObject("result",result);
				mv.setViewName("redirect:/update.do");
			}else {
				mv.addObject("messageType","오류메세지");
				mv.addObject("messageContent","회원정보 수정에 실패하였습니다.");
				mv.addObject("result",result);
				mv.setViewName("redirect:/update.do");
			}
		}
		return mv;
	}
	//프로필 jsp로 전환
	@RequestMapping("/profileUpdate.do")
	public ModelAndView profileUpdate(ModelAndView mv, String userID) {
		User user=service.selectUser(userID);
		mv.addObject("user",user);
		mv.setViewName("profileUpdate");
		return mv;
	}
	
	//프로필 업데이트 등록
	@RequestMapping("/profileUpdateEnd.do")
	public ModelAndView profileUpdateEnd(String userID,ModelAndView mv,
			@RequestParam(value="profile")MultipartFile profile,HttpSession session){
		System.out.println("profile:"+profile.getOriginalFilename());
		User user=new User();
		if(profile !=null) {
			String extOrigi=profile.getOriginalFilename();
			String ext=extOrigi.substring(extOrigi.lastIndexOf(".")+1);
			if(ext.equals("jpg")|| ext.equals("png")||ext.equals("gif")) {
				String path=session.getServletContext().getRealPath("/resources/upload/profile");
				File dir=new File(path);
				if(!dir.exists()) dir.mkdirs();
				String originalFileName=profile.getOriginalFilename();
				String extension=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				int rnd=(int)(Math.random()*10000);
				String rename=sdf.format(System.currentTimeMillis())+"_"+rnd+"."+extension;
				System.out.println("rename:"+rename);
					try {
						profile.transferTo(new File(path+"/"+rename));
					}catch(IOException e) {
						e.printStackTrace();
					}
					user.setUserID(userID);
					user.setProfile(originalFileName);
					user.setReProfile(rename);
					
					int result=service.profileUpdate(user);
					if(result ==1) {
						mv.addObject("userID",userID);
						mv.addObject("messageType","성공 메시지");
						mv.addObject("messageContent","프로필이 변경되었습니다.");
						mv.setViewName("profileUpdate");
					}else {
						mv.addObject("userID",userID);
						mv.addObject("messageType","오류 메시지");
						mv.addObject("messageContent","입력에 실패하였습니다.");
						mv.setViewName("profileUpdate");
					}
			}else {
				User user1=service.selectOneMember(userID);
				String path=session.getServletContext().getRealPath("/resources/upload/profile");
				String reProFile=user1.getReProfile();
				File refile=new File(path+"/"+reProFile);
				
				if(refile.exists()) {//파일 존재여부확인
					if(refile.delete())//파일 삭제
						System.out.println("파일 삭제 성공");
					else System.out.println("파일 삭제 실패 ");
				}
			}
		}
	
		return mv;
	}
}
