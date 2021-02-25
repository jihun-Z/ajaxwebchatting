package com.web.ajax.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.web.ajax.board.model.vo.Attachment;
import com.web.ajax.board.model.vo.Board;

public interface BoardDao {

	int boardWriteEnllo(SqlSession session, Board board);

	List<Board> getBoard(SqlSession session, String userID);

	int insertAttachment(SqlSession session, Attachment a);

}
