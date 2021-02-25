package com.web.ajax.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.web.ajax.board.model.vo.Attachment;
import com.web.ajax.board.model.vo.Board;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public int boardWriteEnllo(SqlSession session, Board board) {
		// TODO Auto-generated method stub
		return session.insert("board.boardWriteEnllo",board);
	}

	@Override
	public List<Board> getBoard(SqlSession session, String userID) {
		// TODO Auto-generated method stub
		return session.selectList("board.getBoard",userID);
	}

	@Override
	public int insertAttachment(SqlSession session, Attachment a) {
		// TODO Auto-generated method stub
		return session.insert("board.insertAttachment",a);
	}


	
}
