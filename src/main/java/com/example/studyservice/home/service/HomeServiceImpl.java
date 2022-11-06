package com.example.studyservice.home.service;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.home.model.ansChkVO;
import com.example.studyservice.home.model.goalVO;
import com.example.studyservice.home.model.myStudyDayVO;
import com.example.studyservice.home.mapper.HomeMapper;
import com.example.studyservice.exam.model.StudyVO;
import com.example.studyservice.home.model.rateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private HomeMapper homeMapper;

    @Autowired
    public HomeServiceImpl(HomeMapper homeMapper){
        this.homeMapper = homeMapper;
    }

    @Override
    public List<StudyVO> dashTopDetail(String userId) {
        return homeMapper.dashTopDetail(userId);
    }

    @Override
    public ArrayList<HashMap<String,String>> studyDay(String userId) {
        return homeMapper.studyDay(userId);
    }

    @Override
    public ArrayList<HashMap<String, Integer>> studyAuth(String userId) {
        return homeMapper.studyAuth(userId);
    }

    @Override
    public List<BoardVO> noticeList() {
        return homeMapper.noticeList();
    }

    @Override
    public List<myStudyDayVO> myStudyDay(String userId) { return homeMapper.myStudyDay(userId); }

    @Override
    public List<myStudyDayVO> myStudyG(String userId) { return homeMapper.myStudyG(userId); }

    @Override
    public List<myStudyDayVO> myStudyTest(String userId) { return homeMapper.myStudyTest(userId); }

    @Override
    public List<goalVO> goal(String userId) { return homeMapper.goal(userId); }

    @Override
    public List<rateVO> ansRate(String userId) {
        return homeMapper.ansRate(userId);
    }

    @Override
    public List<ansChkVO> ansChkList(String authcd, String studyType, String userId) {
        return homeMapper.ansChkList(authcd,studyType,userId);
    }
}
