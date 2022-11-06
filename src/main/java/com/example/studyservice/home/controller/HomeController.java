package com.example.studyservice.home.controller;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.board.service.BoardService;
import com.example.studyservice.exam.model.StudyVO;
import com.example.studyservice.home.model.*;
import com.example.studyservice.home.service.HomeService;
import com.example.studyservice.home.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study-service")
public class HomeController {

    private final UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private HomeService homeService;

    /**
     * 로그인페이지
     */
    @GetMapping("/login")
    public ModelAndView loginPage(Model model) {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    /**
     * 로그인처리
     */
    @PostMapping("/login")
    public ModelAndView logi(Model model) {
        ModelAndView mav = new ModelAndView("dashboard");
        return mav;
    }


    /**
     * 회원등록양식
     */
    @GetMapping("/signUp")
    public ModelAndView signUpPage(Model model) {
        System.out.println("signup!!!!!!!!!!!!");
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }


    /**
     * 회원등록입력
     */
    @PostMapping("/signUp")
    public ModelAndView signUp(Model model,UserVO userVO) {
        ModelAndView mav = new ModelAndView("/login");
        System.out.println(userVO.toString());
        userService.joinUser(userVO);
        return mav;
    }

    /**
     * 로그인실패
     */
    @GetMapping("/access_denied")
    public ModelAndView userDenied(Model model, Authentication authentication) {
        ModelAndView mav = new ModelAndView("login");
        model.addAttribute("alert","아이디 혹은 비밀번호가 맞지 않습니다.");

        return mav;
    }

    /**
     * 로그인성공
     */
    @GetMapping("/dashboard")
    @Timed(value = "study.dashboard",longTask = true)
    public ModelAndView dashboard(Model model, Authentication authentication) {
        ModelAndView mav = new ModelAndView("dashboard");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","dashboard");

        //대쉬보드 학습이력
//        String userId = userVo.getUserId();
        String userId = "162073";
        List<StudyVO> dashTopDetail = homeService.dashTopDetail(userId);
        System.out.println(dashTopDetail.toString());
        model.addAttribute("dashTopDetail",dashTopDetail);

        //대쉬보드 차트1
        ArrayList<HashMap<String,String>> studyDay = homeService.studyDay(userId);
        System.out.println(studyDay.toString());
        List<String> scoreList = new ArrayList<>();
        List<String> dayList = new ArrayList<>();
        for (HashMap itemList : studyDay ){
            scoreList.add((String) itemList.get("score"));
            dayList.add((String) itemList.get("startdt"));
        }
        System.out.println(scoreList.toString());
        System.out.println(dayList.toString());
        model.addAttribute("dayList",dayList);
        model.addAttribute("scoreList",scoreList);

        //대쉬보드 차트2
        ArrayList<HashMap<String,Integer>> studyAuth = homeService.studyAuth(userId);
        System.out.println(studyAuth.toString());
        List<String> authList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (HashMap itemList : studyAuth ){
            authList.add((String) itemList.get("authcd"));
            countList.add(Integer.parseInt(String.valueOf(itemList.get("cnt"))));
        }
        System.out.println(authList.toString());
        System.out.println(countList.toString());
        model.addAttribute("authList",authList);
        model.addAttribute("countList",countList);


        //공지사항 리스트
        List<BoardVO> noticeList = homeService.noticeList();
        System.out.println("::noticeList:::::::::::::"+noticeList.toString());
        model.addAttribute("noticeList",noticeList);

        return mav;
    }

    /**
     * 메인화면-공지사항팝업
     */
    @GetMapping("/dashboard/notice")
    public ModelAndView dashboardNotice(@RequestParam int idx, Model model, Authentication authentication) {
        ModelAndView mav = new ModelAndView("dashboardModal");

        //게시물 상세내용
        BoardVO boardDetail = boardService.boardDetail(idx);
        System.out.println(boardDetail.toString());
        model.addAttribute("boardDetail",boardDetail);

        return mav;
    }

    /**
     * 마이페이지
     */
    @GetMapping("/mypage")
    @Timed(value = "study.mypage",longTask = true)
    public ModelAndView mypage(Model model, Authentication authentication) {
        ModelAndView mav = new ModelAndView("myPage");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println(userVo.toString());
        model.addAttribute("userInfo", userVo);      //유저 아이디

        //메뉴타입
        model.addAttribute("menu_nm","mypage");

        //대쉬보드 학습이력
        String userId = userVo.getUserId();
        List<StudyVO> dashTopDetail = homeService.dashTopDetail(userId);
        System.out.println(dashTopDetail.toString());
        model.addAttribute("dashTopDetail",dashTopDetail);

        //대쉬보드 차트1
        ArrayList<HashMap<String,String>> studyDay = homeService.studyDay(userId);
        System.out.println(studyDay.toString());
        List<String> scoreList = new ArrayList<>();
        List<String> dayList = new ArrayList<>();
        for (HashMap itemList : studyDay ){
            scoreList.add((String) itemList.get("score"));
            dayList.add((String) itemList.get("startdt"));
        }
        System.out.println(scoreList.toString());
        System.out.println(dayList.toString());
        model.addAttribute("dayList",dayList);
        model.addAttribute("scoreList",scoreList);

        //대쉬보드 차트2
        ArrayList<HashMap<String,Integer>> studyAuth = homeService.studyAuth(userId);
        System.out.println(studyAuth.toString());
        List<String> authList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (HashMap itemList : studyAuth ){
            authList.add((String) itemList.get("authcd"));
            countList.add(Integer.parseInt(String.valueOf(itemList.get("cnt"))));
        }
        System.out.println(authList.toString());
        System.out.println(countList.toString());
        model.addAttribute("authList",authList);
        model.addAttribute("countList",countList);


        //공지사항 리스트
        List<BoardVO> noticeList = homeService.noticeList();
        System.out.println("::noticeList:::::::::::::"+noticeList.toString());
        model.addAttribute("noticeList",noticeList);


        //일일학습 리스트
        List<myStudyDayVO> myStudyDay = homeService.myStudyDay(userId);
        System.out.println("::DayList:::::::::::::"+myStudyDay.toString());
        model.addAttribute("myStudyDay",myStudyDay);

        //평가학습 리스트
        List<myStudyDayVO> myStudyG = homeService.myStudyG(userId);
        System.out.println("::GList:::::::::::::"+myStudyG.toString());
        model.addAttribute("myStudyG",myStudyG);

        //시험응시 리스트
        List<myStudyDayVO> myStudyTest = homeService.myStudyTest(userId);
        System.out.println("::TestList:::::::::::::"+myStudyTest.toString());
        model.addAttribute("myStudyTest",myStudyTest);

        //마이페이지 목표 달성량
        List<goalVO> goal = homeService.goal(userId);
        for(int i = 0; i<goal.size(); i++){
            if(goal.get(i).getGubun().equals("DAY")){
                model.addAttribute("day", goal.get(i));
                System.out.println("goalday::::::::::::::::::"+model.getAttribute("day"));
            }
            else if(goal.get(i).getGubun().equals("MONTH")){
                model.addAttribute("month", goal.get(i));
                System.out.println("goalmonth::::::::::::::::::"+model.getAttribute("month"));
            }
            else if(goal.get(i).getGubun().equals("YEAR")){
                model.addAttribute("year", goal.get(i));
                System.out.println("goalyear::::::::::::::::::"+model.getAttribute("year"));
            }
        }

        //정답률
        List<rateVO> ansRate = homeService.ansRate(userId);
        System.out.println(ansRate);
        List<rateVO> andRateD = new ArrayList<rateVO>();
        List<rateVO> andRateG = new ArrayList<rateVO>();
        List<rateVO> andRateT = new ArrayList<rateVO>();
        for(int i = 0; i<ansRate.size(); i++){
            if(ansRate.get(i).getStudytype().equals("D")){
                andRateD.add(ansRate.get(i));
            }
            else if(ansRate.get(i).getStudytype().equals("G")){
                andRateG.add(ansRate.get(i));
            }
            else if(ansRate.get(i).getStudytype().equals("T")){
                andRateT.add(ansRate.get(i));
            }
        }
        model.addAttribute("DRate", andRateD);
        model.addAttribute("GRate", andRateG);
        model.addAttribute("TRate", andRateT);

        return mav;
    }

    //문제등록 - 문제상세내역
    @GetMapping("/mypagePopup")
    public ModelAndView mypagePopup(@RequestParam String authcd,@RequestParam String studyType, Model model
            , HttpServletRequest request, Authentication authentication) {

        ModelAndView mav = new ModelAndView("mypageModal");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴

        //메뉴타입
        model.addAttribute("menu_nm", "queGen");

        List<ansChkVO> ansChkList = homeService.ansChkList(authcd, studyType, userVo.getUserId());
        System.out.println("ansChkList==" + ansChkList);
        model.addAttribute("ansChkList", ansChkList);

        return mav;
    }
}
