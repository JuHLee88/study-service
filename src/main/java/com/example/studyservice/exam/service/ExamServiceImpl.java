package com.example.studyservice.exam.service;

import com.example.studyservice.exam.mapper.ExamMapper;
import com.example.studyservice.exam.model.QuestionVO;
import com.example.studyservice.exam.model.StudyQueVO;
import com.example.studyservice.exam.model.StudyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamMapper examMapper;

    @Autowired
    public ExamServiceImpl(ExamMapper examMapper){
        this.examMapper = examMapper;
    }

    @Override
    public ArrayList<HashMap<String, String>> dailyAuthList() {
        return examMapper.dailyAuthList();
    }

    @Override
    public int insertStudy(StudyVO studyVO) {
        return examMapper.insertStudy(studyVO);
    }

    @Override
    public int insertStudyQue(StudyQueVO studyQueVO) {
        return examMapper.insertStudyQue(studyQueVO);
    }

    @Override
    public ArrayList<HashMap<String, String>> totRandomDailyQue(String auth) {
        return examMapper.totRandomDailyQue(auth);
    }

    @Override
    public QuestionVO studyQue(StudyQueVO studyQueVO) {
        return examMapper.studyQue(studyQueVO);
    }

    @Override
    public ArrayList<HashMap<String, String>> studyQueItem(StudyQueVO studyQueVO) {
        return examMapper.studyQueItem(studyQueVO);
    }

    @Override
    public void updateStudyQue(StudyQueVO studyQueVO) {
        examMapper.updateStudyQue(studyQueVO);
    }

    @Override
    public String studyAns(StudyQueVO studyQueVO) {
        return examMapper.studyAns(studyQueVO);
    }

    @Override
    public ArrayList<HashMap<String, Object>> studyQueList(StudyQueVO studyQueVO) {
        return examMapper.studyQueList(studyQueVO);
    }

    @Override
    public void updateStudy(StudyVO studyVO) {
        examMapper.updateStudy(studyVO);
    }

    @Override
    public String getScore(StudyQueVO studyQueVO) {
        return examMapper.getScore(studyQueVO);
    }

    @Override
    public ArrayList<HashMap<String, String>> evalExamSelectBox(String gubun) {
        return examMapper.evalExamSelectBox(gubun);
    }

    @Override
    public ArrayList<HashMap<String, String>> totRandomEvalQue(String auth) {
        return examMapper.totRandomEvalQue(auth);
    }

}
