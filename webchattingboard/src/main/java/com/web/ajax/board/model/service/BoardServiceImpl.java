package com.web.ajax.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ajax.board.model.dao.BoardDao;
import com.web.ajax.board.model.vo.Attachment;
import com.web.ajax.board.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	private BoardDao dao;
	@Autowired
	SqlSession session;
	
	
	@Override
	public int boardWriteEnllo(Board board,List<Attachment> files) {
		// TODO Auto-generated method stub
		int result=dao.boardWriteEnllo(session,board);
			if(result == 1) {
				if(files !=null) {
					for(Attachment a: files) {
						a.setBoardNo(board.getBoardNo());
						result=dao.insertAttachment(session,a);
					}
				}
			}
		return result;
	}


	@Override
	public List<Board> getBoard(String userID) {
		// TODO Auto-generated method stub
		return dao.getBoard(session,userID);
	}
}
