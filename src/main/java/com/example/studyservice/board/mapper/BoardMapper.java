package com.example.studyservice.board.mapper;

import com.example.studyservice.board.model.BoardListVO;
import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.board.model.ReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardListVO> getboardList();//게시판 첫 화면에 뿌리는 데이터

    void getboardWrite(BoardVO boardVO); //데이터 삽입

    BoardVO getboardDetail(@Param("id") int id); //각 row 누르면 나오는 디테일

    List<ReplyVO> getboardReply(int id); //디테일 화면에 댓글 가져오기

    void getboardReplyWrite(ReplyVO ReplyVO); //댓글 삽입


    int boardInsert(BoardVO boardVO);

    int boardUpdate(BoardVO boardVO);

    int boardDelete(int id);


}
