package com.example.studyservice.board.service;

import com.example.studyservice.board.model.BoardListVO;
import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.board.model.ReplyVO;

import java.util.List;

public interface BoardService {
    BoardVO boardDetail(int id); //각 row 누르면 나오는 데이터(db row 전체)
    List<BoardListVO> boardList(); //게시판 첫 화면에서 나오는 데이터
    void boardWrite(BoardVO boardVO); //작성하기
    List<ReplyVO> boardReply(int id); //댓글가져오기
    void boardReplyWrite(ReplyVO replyVO);

    int boardInsert(BoardVO boardVO);

    int boardUpdate(BoardVO boardVO);
    int boardDelete(int id);

}
