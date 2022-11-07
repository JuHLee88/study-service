package com.example.studyservice.board.controller;

import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.studyservice.board.model.BoardListVO;
import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.board.model.ReplyVO;
import com.example.studyservice.board.service.BoardService;
import com.example.studyservice.home.model.UserVO;
import io.micrometer.core.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/study-service")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    @Timed(value = "study.board",longTask = true)
    public ModelAndView boardList(Authentication authentication, Model model, HttpServletRequest request,HttpSession session){
        ModelAndView mav = new ModelAndView("board/board");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","board");

        //게시판 리스트
        List<BoardListVO> boardList = boardService.boardList();
        System.out.println(boardList.toString());
        model.addAttribute("boardList",boardList);

        return mav;
    }
    //디테일 화면, 댓글 가져오기
    @GetMapping("/board/boardDetail/{idx}")
    public ModelAndView boardDetail(@PathVariable("idx") int idx, Authentication authentication, Model model
            , HttpServletRequest request,HttpSession session){

        ModelAndView mav = new ModelAndView("board/boardDetail");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","board");

        //게시물 상세내용
        BoardVO boardDetail = boardService.boardDetail(idx);
        System.out.println(boardDetail.toString());
        model.addAttribute("boardDetail",boardDetail);

        //댓글 내용
        List<ReplyVO> boardReply = boardService.boardReply(idx);
        System.out.println(boardReply.toString());
        model.addAttribute("boardReply", boardReply);
        return mav;
    }
    //디테일 화면에서 댓글쓰기
    @PostMapping(value="/board/boardDetail")
    public ModelAndView boardReplyWritePost(Authentication authentication, Model model, HttpServletRequest request
            , HttpSession session, int idx, String replyContent){
            //, @ModelAttribute ReplyVO reply){
        ModelAndView mav = new ModelAndView("board/boardDetail");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","board");
        //날짜 구하기
        LocalDate date = LocalDate.now();
        String writedt = date.toString();
        writedt = writedt.replaceAll("-","");

        //*reply 데이터 셋 만들기
        ReplyVO replyVO = new ReplyVO();
        if(replyContent != "NULL"){
            replyVO.setIdx(idx);
            replyVO.setSeq(0);
            replyVO.setContent(replyContent);
            replyVO.setWriteuser(userVo.getUserId());
            replyVO.setWritedt(writedt);
            System.out.println("reply:: "+replyVO);
            boardService.boardReplyWrite(replyVO);
        }

        //게시물 상세내용
        BoardVO boardDetail = boardService.boardDetail(idx);
        System.out.println(boardDetail.toString());
        model.addAttribute("boardDetail",boardDetail);

        //댓글 내용
        List<ReplyVO> boardReply = boardService.boardReply(idx);
        System.out.println(boardReply.toString());
        model.addAttribute("boardReply", boardReply);
        return mav;
    }

    @RequestMapping(value="/board/boardWrite", method={RequestMethod.POST})
    @Timed(value = "study.boardWrite",longTask = true)
    public ModelAndView boardWritePost(Authentication authentication, Model model, HttpServletRequest request
            ,HttpSession session, @ModelAttribute BoardVO write){
        ModelAndView mav = new ModelAndView("board/board");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","board");

        //날짜 구하기
        LocalDate date = LocalDate.now();
        String writedt = date.toString();
        System.out.println(writedt);
        writedt = writedt.replaceAll("-","");
        System.out.println(writedt);
        //boardVO 데이터 셋 만들기
        BoardVO boardVO = new BoardVO();
        if(write.getGubun() != "NULL" && write.getTitle() != "NULL" && write.getContent() != "NULL"){
            boardVO.setGubun(write.getGubun());
            boardVO.setTitle(write.getTitle());
            boardVO.setContent(write.getContent());
            boardVO.setWriteuser(userVo.getUserId());
            boardVO.setQid(write.getQid());
            boardVO.setWritedt(writedt);
            boardVO.setModifyuser(write.getModifyuser());
            boardVO.setModifydt(write.getModifydt());
            boardService.boardWrite(boardVO);
        }
        //게시판 리스트
        List<BoardListVO> boardList = boardService.boardList();
        System.out.println(boardList.toString());
        model.addAttribute("boardList",boardList);

        return mav;
    }
    //게시물 등록 누르면 게시판 리스트로 이동
    @RequestMapping(value="/board/boardWrite", method={RequestMethod.GET})
    public ModelAndView boardWriteGet(Authentication authentication, Model model, HttpServletRequest request
            ,HttpSession session, @ModelAttribute BoardVO write){
        ModelAndView mav = new ModelAndView("board/boardWrite");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","board");

        //게시판 리스트
        List<BoardListVO> boardList = boardService.boardList();
        System.out.println(boardList.toString());
        model.addAttribute("boardList",boardList);
        return mav;
    }
}
