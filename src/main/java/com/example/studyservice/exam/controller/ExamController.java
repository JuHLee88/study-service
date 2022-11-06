package com.example.studyservice.exam.controller;

import com.example.studyservice.exam.model.QuestionItemVO;
import com.example.studyservice.exam.model.QuestionVO;
import com.example.studyservice.exam.model.StudyQueVO;
import com.example.studyservice.exam.service.ExamService;
import com.example.studyservice.exam.model.StudyVO;
import com.example.studyservice.home.model.UserVO;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/study-service")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/dailyExam")
    @Timed(value = "study.dailyExam",longTask = true)
    public ModelAndView dailyExamList(Model model, HttpServletRequest request, Authentication authentication ){
        ModelAndView mav = new ModelAndView("exam/dailyExam");
        model.addAttribute("menu_nm","dailyExam");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //일일학습 학습항목리스트
        ArrayList<HashMap<String,String>> dailyAuthList = examService.dailyAuthList();
        System.out.println(dailyAuthList.toString());
        model.addAttribute("dailyAuthList",dailyAuthList);

        return mav;
    }

    @PostMapping(value = "/dailyExam/dailyExamModal/{direction}")
    @ResponseBody
    public HashMap<String, Object> dailyExamListNext(@PathVariable String direction,StudyQueVO studyQueVO, Model model, Authentication authentication ){
        System.out.println("***********************"+direction);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        studyQueVO.setSabun(userVo.getUserId());

        //현재문제 정답저장
        if(studyQueVO.getAnsyn() != null){
            examService.updateStudyQue(studyQueVO);
        }

        //다음문제정보 조회파라미터 셋팅
        if(direction.equals("next")){
            studyQueVO.setSeq(studyQueVO.getSeq()+1);
        }if(direction.equals("pre")){
            studyQueVO.setSeq(studyQueVO.getSeq()-1);
        }if(direction.equals("complete")){
            Date st = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String enddt = dtFormat.format(st);
            String score = examService.getScore(studyQueVO);

            StudyVO studyVO = new StudyVO(userVo.getUserId(),studyQueVO.getStartdt(),"","",enddt,score,"");
            examService.updateStudy(studyVO);
        }else {
            studyQueVO.setSeq(studyQueVO.getSeq());
        }

        //다음문제정보 조회
        QuestionVO question = examService.studyQue(studyQueVO);
        ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
        System.out.println("######################### "+question);
        System.out.println("######################### "+questionItem);

        HashMap<String, Object> resultList = new HashMap<>();
        resultList.put("question", question);
        resultList.put("questionItem", questionItem);
        resultList.put("seqQue",studyQueVO.getSeq());
        resultList.put("startdt",studyQueVO.getStartdt());
        //다음문제 선택한 정답조회
        String ans = examService.studyAns(studyQueVO);
        resultList.put("ansyn",ans);
        //문제리스트 갱신
        ArrayList<HashMap<String,Object>> questionList = examService.studyQueList(studyQueVO);
        resultList.put("questionList",questionList);

        return resultList;
    }

    @GetMapping("/dailyExam/dailyExamModal")
    public ModelAndView dailyExamList(@RequestParam String auth,@RequestParam String authNm, Model model, Authentication authentication ){
        ModelAndView mav = new ModelAndView("exam/popup/dailyExamModal");
        model.addAttribute("authNm",authNm);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //출제문제 개인 학습이력에 추가 (study, studyQue, studyQueItem)
        //1. study table insert
        Date st = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeCurrent = dtFormat.format(st);
        StudyVO studyVO = new StudyVO(userVo.getUserId(),timeCurrent, "D", auth, "", "","");
        System.out.println(studyVO);
        int resultIns = examService.insertStudy(studyVO);
        System.out.println(resultIns);

        //2. studyque table insert
        // 랜덤번호 추출
        ArrayList<HashMap<String,String>> totRandumDailyQue = examService.totRandomDailyQue(auth);
        ArrayList<StudyQueVO> studyQueList = new ArrayList<>();

        for (var i=0; i < totRandumDailyQue.size(); i++ ){
            // 랜덤번호로 studyQue insert
            StudyQueVO studyQueVO = new StudyQueVO();
            int seq = i+1;
            System.out.println(totRandumDailyQue.get(i).get("qid"));
            studyQueVO.setSabun(userVo.getUserId());
            studyQueVO.setStartdt(timeCurrent);
            studyQueVO.setQid(totRandumDailyQue.get(i).get("qid"));
            studyQueVO.setSeq(seq);
            resultIns = examService.insertStudyQue(studyQueVO);
            System.out.println("*****************************"+studyQueVO);

            if(seq == 1){
                //3. 화면에 표시할 첫번째 문제 Model에 담기
                QuestionVO question = examService.studyQue(studyQueVO);
                ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
                System.out.println(question);
                System.out.println(questionItem);
                model.addAttribute("question",question);
                model.addAttribute("questionItem",questionItem);
            }

            //4. 문제목록 list만들기
            System.out.println("*****************************"+studyQueVO);
            studyQueList.add(studyQueVO);
            System.out.println("*****************************"+studyQueList);
        }
        //4-2. 문제목록 Model에 담기
        model.addAttribute("studyQueList",studyQueList);

        //5. 시작시간데이터, 현재문항 Model에 담기
        model.addAttribute("timeCurrent",timeCurrent);
        model.addAttribute("seqQue",1);
        model.addAttribute("totQue",20);

        return mav;
    }

    @GetMapping("/realExam")
    @Timed(value = "study.realExam",longTask = true)
    public ModelAndView realExamList(Model model, HttpServletRequest request, Authentication authentication){
        ModelAndView mav = new ModelAndView("exam/realExam");
        model.addAttribute("menu_nm","realExam");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //평가학습 학습항목리스트
        ArrayList<HashMap<String,String>> realAuthList = examService.evalExamSelectBox("G");
        System.out.println(realAuthList.toString());
        model.addAttribute("realAuthList",realAuthList);

        return mav;
    }

    @GetMapping("/realExam/realExamModal")
    public ModelAndView realExamList(@RequestParam String auth,@RequestParam String authNm, Model model, Authentication authentication ){
        ModelAndView mav = new ModelAndView("exam/popup/realExamModal");
        model.addAttribute("authNm",authNm);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //출제문제 개인 학습이력에 추가 (study, studyQue, studyQueItem)
        //1. study table insert
        Date st = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeCurrent = dtFormat.format(st);
        StudyVO studyVO = new StudyVO(userVo.getUserId(),timeCurrent, "G", auth, "", "","");
        System.out.println(studyVO);
        int resultIns = examService.insertStudy(studyVO);
        System.out.println(resultIns);

        //2. studyque table insert
        // 랜덤번호 추출
        ArrayList<HashMap<String,String>> totRandomEvalQue = examService.totRandomEvalQue(auth);
        ArrayList<StudyQueVO> studyQueList = new ArrayList<>();

        for (var i=0; i < totRandomEvalQue.size(); i++ ){
            // 랜덤번호로 studyQue insert
            StudyQueVO studyQueVO = new StudyQueVO();
            int seq = i+1;
            System.out.println(totRandomEvalQue.get(i).get("qid"));
            studyQueVO.setSabun(userVo.getUserId());
            studyQueVO.setStartdt(timeCurrent);
            studyQueVO.setQid(totRandomEvalQue.get(i).get("qid"));
            studyQueVO.setSeq(seq);
            resultIns = examService.insertStudyQue(studyQueVO);
            System.out.println("*****************************"+studyQueVO);

            if(seq == 1){
                //3. 화면에 표시할 첫번째 문제 Model에 담기
                QuestionVO question = examService.studyQue(studyQueVO);
                ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
                System.out.println(question);
                System.out.println(questionItem);
                model.addAttribute("question",question);
                model.addAttribute("questionItem",questionItem);
            }

            //4. 문제목록 list만들기
            System.out.println("*****************************"+studyQueVO);
            studyQueList.add(studyQueVO);
            System.out.println("*****************************"+studyQueList);
        }
        //4-2. 문제목록 Model에 담기
        model.addAttribute("studyQueList",studyQueList);

        //5. 시작시간데이터, 현재문항 Model에 담기
        model.addAttribute("timeCurrent",timeCurrent);
        model.addAttribute("seqQue",1);
        model.addAttribute("totQue",40);

        return mav;
    }

    @PostMapping(value = "/realExam/realExamModal/{direction}")
    @ResponseBody
    public HashMap<String, Object> realExamListNext(@PathVariable String direction,StudyQueVO studyQueVO, Model model, Authentication authentication ){
        System.out.println("***********************"+direction);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        studyQueVO.setSabun(userVo.getUserId());

        //현재문제 정답저장
        if(studyQueVO.getAnsyn() != null){
            examService.updateStudyQue(studyQueVO);
        }

        //다음문제정보 조회파라미터 셋팅
        if(direction.equals("next")){
            studyQueVO.setSeq(studyQueVO.getSeq()+1);
        }if(direction.equals("pre")){
            studyQueVO.setSeq(studyQueVO.getSeq()-1);
        }if(direction.equals("complete")){
            Date st = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String enddt = dtFormat.format(st);
            String score = examService.getScore(studyQueVO);

            StudyVO studyVO = new StudyVO(userVo.getUserId(),studyQueVO.getStartdt(),"","",enddt,score,"");
            examService.updateStudy(studyVO);
        }else {
            studyQueVO.setSeq(studyQueVO.getSeq());
        }

        //다음문제정보 조회
        QuestionVO question = examService.studyQue(studyQueVO);
        ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
        System.out.println("######################### "+question);
        System.out.println("######################### "+questionItem);

        HashMap<String, Object> resultList = new HashMap<>();
        resultList.put("question", question);
        resultList.put("questionItem", questionItem);
        resultList.put("seqQue",studyQueVO.getSeq());
        resultList.put("startdt",studyQueVO.getStartdt());
        //다음문제 선택한 정답조회
        String ans = examService.studyAns(studyQueVO);
        resultList.put("ansyn",ans);
        //문제리스트 갱신
        ArrayList<HashMap<String,Object>> questionList = examService.studyQueList(studyQueVO);
        resultList.put("questionList",questionList);

        return resultList;
    }

    @GetMapping("/evalExam")
    @Timed(value = "study.evalExam",longTask = true)
    public ModelAndView evalExamList(Model model, HttpServletRequest request, Authentication authentication){
        ModelAndView mav = new ModelAndView("exam/evalExam");
        model.addAttribute("menu_nm","evalExam");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //일일학습 학습항목리스트
        ArrayList<HashMap<String,String>> gubunAuthList = examService.evalExamSelectBox("E1");
        System.out.println(gubunAuthList.toString());
        model.addAttribute("gubunAuthList",gubunAuthList);

        return mav;
    }

    @PostMapping("/evalExam/selectBox")
    public ArrayList<HashMap<String,String>> evalExamSelectBox(String gubun, Model model, HttpServletRequest request, Authentication authentication){

        //파라미터
        String param_gubun = gubun;
        System.out.println(param_gubun);

        //일일학습 학습항목리스트
        ArrayList<HashMap<String,String>> gubunAuthList = examService.evalExamSelectBox(param_gubun);
        System.out.println(gubunAuthList.toString());

        return gubunAuthList;
    }


    @GetMapping("/evalExam/evalExamModal")
    public ModelAndView evalExamList(@RequestParam String auth,@RequestParam String authNm, Model model, Authentication authentication ){
        ModelAndView mav = new ModelAndView("exam/popup/evalExamModal");
        model.addAttribute("authNm",authNm);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //출제문제 개인 학습이력에 추가 (study, studyQue, studyQueItem)
        //1. study table insert
        Date st = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeCurrent = dtFormat.format(st);
        StudyVO studyVO = new StudyVO(userVo.getUserId(),timeCurrent, "T", auth, "", "","");
        System.out.println(studyVO);
        int resultIns = examService.insertStudy(studyVO);
        System.out.println(resultIns);

        //2. studyque table insert
        // 랜덤번호 추출
        ArrayList<HashMap<String,String>> totRandomEvalQue = examService.totRandomEvalQue(auth);
        ArrayList<StudyQueVO> studyQueList = new ArrayList<>();

        for (var i=0; i < totRandomEvalQue.size(); i++ ){
            // 랜덤번호로 studyQue insert
            StudyQueVO studyQueVO = new StudyQueVO();
            int seq = i+1;
            System.out.println(totRandomEvalQue.get(i).get("qid"));
            studyQueVO.setSabun(userVo.getUserId());
            studyQueVO.setStartdt(timeCurrent);
            studyQueVO.setQid(totRandomEvalQue.get(i).get("qid"));
            studyQueVO.setSeq(seq);
            resultIns = examService.insertStudyQue(studyQueVO);
            System.out.println("*****************************"+studyQueVO);

            if(seq == 1){
                //3. 화면에 표시할 첫번째 문제 Model에 담기
                QuestionVO question = examService.studyQue(studyQueVO);
                ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
                System.out.println(question);
                System.out.println(questionItem);
                model.addAttribute("question",question);
                model.addAttribute("questionItem",questionItem);
            }

            //4. 문제목록 list만들기
            System.out.println("*****************************"+studyQueVO);
            studyQueList.add(studyQueVO);
            System.out.println("*****************************"+studyQueList);
        }
        //4-2. 문제목록 Model에 담기
        model.addAttribute("studyQueList",studyQueList);

        //5. 시작시간데이터, 현재문항 Model에 담기
        model.addAttribute("timeCurrent",timeCurrent);
        model.addAttribute("seqQue",1);
        model.addAttribute("totQue",40);

        return mav;
    }

    @PostMapping(value = "/evalExam/evalExamModal/{direction}")
    @ResponseBody
    public HashMap<String, Object> evalExamListNext(@PathVariable String direction,StudyQueVO studyQueVO, Model model, Authentication authentication ){
        System.out.println("***********************"+direction);

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        studyQueVO.setSabun(userVo.getUserId());

        //현재문제 정답저장
        if(studyQueVO.getAnsyn() != null){
            examService.updateStudyQue(studyQueVO);
        }

        //다음문제정보 조회파라미터 셋팅
        if(direction.equals("next")){
            studyQueVO.setSeq(studyQueVO.getSeq()+1);
        }if(direction.equals("pre")){
            studyQueVO.setSeq(studyQueVO.getSeq()-1);
        }if(direction.equals("complete")){
            Date st = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String enddt = dtFormat.format(st);
            String score = examService.getScore(studyQueVO);

            StudyVO studyVO = new StudyVO(userVo.getUserId(),studyQueVO.getStartdt(),"","",enddt,score,"");
            examService.updateStudy(studyVO);
        }else {
            studyQueVO.setSeq(studyQueVO.getSeq());
        }

        //다음문제정보 조회
        QuestionVO question = examService.studyQue(studyQueVO);
        ArrayList<HashMap<String,String>> questionItem = examService.studyQueItem(studyQueVO);
        System.out.println("######################### "+question);
        System.out.println("######################### "+questionItem);

        HashMap<String, Object> resultList = new HashMap<>();
        resultList.put("question", question);
        resultList.put("questionItem", questionItem);
        resultList.put("seqQue",studyQueVO.getSeq());
        resultList.put("startdt",studyQueVO.getStartdt());
        //다음문제 선택한 정답조회
        String ans = examService.studyAns(studyQueVO);
        resultList.put("ansyn",ans);
        //문제리스트 갱신
        ArrayList<HashMap<String,Object>> questionList = examService.studyQueList(studyQueVO);
        resultList.put("questionList",questionList);

        return resultList;
    }
}
