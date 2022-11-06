package com.example.studyservice.board.service;

import com.example.studyservice.board.mapper.BoardMapper;
import com.example.studyservice.board.model.BoardListVO;
import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.board.model.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }

    @Override
    public BoardVO boardDetail(int id) {
        return boardMapper.getboardDetail(id);
    }

    @Override
    public List<BoardListVO> boardList() {
        return boardMapper.getboardList();
    }

    @Override
    public void boardWrite(BoardVO boardVO) {
        boardMapper.getboardWrite(boardVO);
    }

    @Override
    public List<ReplyVO> boardReply(int id) {
        return boardMapper.getboardReply(id);
    }

    @Override
    public void boardReplyWrite(ReplyVO replyVO){
        boardMapper.getboardReplyWrite(replyVO);
    }

    @Override
    public int boardInsert(BoardVO boardVO) {
        return boardMapper.boardInsert(boardVO);
    }

    @Override
    public int boardUpdate(BoardVO boardVO) {
        return boardMapper.boardUpdate(boardVO);
    }

    @Override
    public int boardDelete(int id) {
        return boardMapper.boardDelete(id);
    }
}
