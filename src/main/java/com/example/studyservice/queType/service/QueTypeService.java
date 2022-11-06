package com.example.studyservice.queType.service;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.queType.model.QueListVO;
import com.example.studyservice.queType.model.QueTypeVO;
import java.util.List;

public interface QueTypeService {
    List<QueTypeVO> queList(); //첫 화면에서 나오는 데이터
    List<QueTypeVO> queListDetail(String authcd); //문제분류별 문제보기
    List<QueListVO> queListPrint(String qtypecd, String authcd); //문제분류 별 문제화면 출력
    // ArrayList<HashMap<String, String>> dailyAuthList();
}
