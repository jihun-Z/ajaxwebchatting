package com.web.ajax.board.model.service;

import java.util.List;

import com.web.ajax.board.model.vo.Attachment;
import com.web.ajax.board.model.vo.Board;

public interface BoardService {

	int boardWriteEnllo(Board board, List<Attachment> files);

	List<Board> getBoard(String userID);

}
