package com.example.studyservice.home.mapper;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.exam.model.StudyVO;
import com.example.studyservice.home.model.ansChkVO;
import com.example.studyservice.home.model.goalVO;
import com.example.studyservice.home.model.myStudyDayVO;
import com.example.studyservice.home.model.rateVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface HomeMapper {
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
