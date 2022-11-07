package com.example.studyservice.queType.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.studyservice.queType.model.QueListVO;
import com.example.studyservice.queType.model.QueTypeVO;
import com.example.studyservice.queType.service.QueTypeService;
import com.example.studyservice.home.model.UserVO;
import io.micrometer.core.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/study-service")
public class QueTypeController {

    @Autowired
    private QueTypeService queTypeService;

    @GetMapping("/queTypeListDetail/{authcd}")
    public ModelAndView queTypeListDetail(@PathVariable("authcd") String authcd, Authentication authentication, Model model
            , HttpServletRequest request,HttpSession session) {

        ModelAndView mav = new ModelAndView("queType/queTypeList");
        model.addAttribute("menu_nm", "queTypeList");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디
        System.out.println("user====" + userVo.toString());

        System.out.println("authcd===" + authcd);
        //인증분야 리스트
        List<QueTypeVO> queList = queTypeService.queList();
        System.out.println("queList====" + queList.toString());
        model.addAttribute("queList", queList);

        List<QueTypeVO> queListDetail = queTypeService.queListDetail(authcd);
        System.out.println("queListDetail====" + queListDetail.toString());
        model.addAttribute("queListDetail", queListDetail);

        return mav;
    }

// 문제 출력 호출
    @GetMapping("/queListPrint")
    public ModelAndView queListPrint(@RequestParam String qtypecd,@RequestParam String authcd,
            Authentication authentication, Model model, HttpServletRequest request,HttpSession session) {

        ModelAndView mav = new ModelAndView("queType/popup/queTypeModal");
        System.out.println("queListPrint#############################################");
        System.out.println("qtypecd=="+qtypecd);
        System.out.println("authcd=="+authcd);
        model.addAttribute("menu_nm", "queListPrint");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
        model.addAttribute("userInfo", userVo);      //유저 아이디
        System.out.println("user22====" + userVo.toString());

        System.out.println("qtypecd22===" + qtypecd);
        System.out.println("authcd222===" + authcd);
        //인증분야 리스트
        List<QueTypeVO> queList = queTypeService.queList();
        System.out.println("queList22====" + queList.toString());
        model.addAttribute("queList", queList);

        System.out.println("qtypecd====" + qtypecd);
        System.out.println("authcd222====" + authcd);

        List<QueListVO> queListPrint = queTypeService.queListPrint(qtypecd, authcd);
        System.out.println("queListPrint====" + queListPrint.toString());
        model.addAttribute("queListPrint", queListPrint);

        return mav;
    }

    @GetMapping("/queTypeList")
    @Timed(value = "study.queTypeList",longTask = true)
    public ModelAndView queTypeList(Model model, HttpServletRequest request, Authentication authentication, HttpSession session) {
        ModelAndView mav = new ModelAndView("queType/queTypeList");
        model.addAttribute("menu_nm", "queTypeList");

        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVO userVo = (UserVO)session.getAttribute("userInfo");
//        UserVO userVo = (UserVO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("userInfo", userVo);      //유저 아이디
        System.out.println("user====" + userVo.toString());

        //인증분야 리스트
        List<QueTypeVO> queList = queTypeService.queList();
        System.out.println("queList====" + queList.toString());
        model.addAttribute("queList", queList);

        return mav;
    }

}
