package com.example.studyservice.exam.mapper;

import com.example.studyservice.exam.model.QuestionVO;
import com.example.studyservice.exam.model.StudyQueVO;
import com.example.studyservice.exam.model.StudyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface ExamMapper {
    ArrayList<HashMap<String, String>> dailyAuthList();

    int insertStudy(StudyVO studyVO);

    int insertStudyQue(StudyQueVO studyQueVO);

    ArrayList<HashMap<String, String>> totRandomDailyQue(String auth);

    QuestionVO studyQue(StudyQueVO studyQueVO);

    ArrayList<HashMap<String, String>> studyQueItem(StudyQueVO studyQueVO);

    void updateStudyQue(StudyQueVO studyQueVO);

    String studyAns(StudyQueVO studyQueVO);

    ArrayList<HashMap<String, Object>> studyQueList(StudyQueVO studyQueVO);

    void updateStudy(StudyVO studyVO);

    String getScore(StudyQueVO studyQueVO);

    ArrayList<HashMap<String, String>> evalExamSelectBox(String gubun);

    ArrayList<HashMap<String, String>> totRandomEvalQue(String auth);
}
