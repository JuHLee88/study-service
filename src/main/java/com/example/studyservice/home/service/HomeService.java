package com.example.studyservice.home.service;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.exam.model.StudyVO;
import com.example.studyservice.home.model.ansChkVO;
import com.example.studyservice.home.model.goalVO;
import com.example.studyservice.home.model.myStudyDayVO;
import com.example.studyservice.home.model.rateVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface HomeService {
    List<StudyVO> dashTopDetail(String userId);
    ArrayList<HashMap<String,String>> studyDay(String userId);
    ArrayList<HashMap<String, Integer>> studyAuth(String userId);
    List<BoardVO> noticeList();
    List<myStudyDayVO> myStudyDay(String userId);
    List<myStudyDayVO> myStudyG(String userId);
    List<myStudyDayVO> myStudyTest(String userId);
    List<goalVO> goal(String userId);
    List<rateVO> ansRate(String userId);

    List<ansChkVO> ansChkList(String authcd, String studyType, String userId);
}
