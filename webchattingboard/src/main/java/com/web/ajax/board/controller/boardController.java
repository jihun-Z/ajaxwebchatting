package com.web.ajax.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.web.ajax.board.model.service.BoardService;
import com.web.ajax.board.model.vo.Attachment;
import com.web.ajax.board.model.vo.Board;

@Controller
public class boardController {
		
	@Autowired
	BoardService service;
	
	@RequestMapping("/boardView.do")
	public String boardView() {
		return "board/boardView";
		
	}
	@RequestMapping("/boardWrite.do")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	@RequestMapping("/boardWriteEnllo.do")
	public ModelAndView boardWriteEnllo(Board board,
			@RequestParam(value="file")MultipartFile[] boardFile,HttpSession session,
			ModelAndView mv) {
		if(board.getBoardTitle()==null|| board.getBoardContent().equals("")||
				board.getBoardContent() == null|| board.getBoardTitle().equals("")) {
			mv.addObject("messageType","오류 메세지");
			mv.addObject("messageContent","모든 내용을 입력하세요.");
			mv.setViewName("board/boardWrite");
		}
		List<Attachment> files=new ArrayList();
		if(boardFile != null) {
			String path=session.getServletContext().getRealPath("/resources/upload/board");
			File dir=new File(path);
			if(!dir.exists()) dir.mkdirs();
				for(MultipartFile f: boardFile) {
					
					String extOrigi=f.getOriginalFilename();
					String ext=extOrigi.substring(extOrigi.lastIndexOf(".")+1);
					if(ext.equals("jpg") ||ext.equals("png") ||ext.equals("gif")) {
						
						String originalName=f.getOriginalFilename();
						String extension=originalName.substring(originalName.lastIndexOf(".")+1);
						SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HHmmSSsss");
						int rnd=(int)(Math.random()*10000);
						String rename=sdf.format(System.currentTimeMillis())+ "_"+rnd+"."+extension;
						try {
							f.transferTo(new File(path+"/"+rename));
							
						}catch(Exception e) {
							e.printStackTrace();
						}
						Attachment a=Attachment.builder().boardFile(originalName)
								.boardReFile(rename).build();
						files.add(a);
				}
					
				}
				
		}
			
		int result=service.boardWriteEnllo(board,files);
			if(result == 1) {
				mv.addObject("messageType","성공 메세지");
				mv.addObject("messageContent","성공적으로 등록되었습니다.");
				mv.setViewName("board/boardWrite");
			}else {
				mv.addObject("messageType","오류 메세지");
				mv.addObject("messageContent","등록에 실패하였습니다.");
				mv.setViewName("board/boardWrite");
			}
		return mv;
	}
	
	@RequestMapping("/boardGetBoard.do")
	public ModelAndView getBoard(String userID,ModelAndView mv) {
		
		List<Board> list=service.getBoard(userID);
		mv.addObject("list",list);
		mv.setViewName("board/boardList");
		return mv;
	}
}
