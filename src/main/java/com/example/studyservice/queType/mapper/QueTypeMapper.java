package com.example.studyservice.queType.mapper;

import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.queType.model.QueListVO;
import com.example.studyservice.queType.model.QueTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface QueTypeMapper {
    List<QueTypeVO> queList();// 첫 화면에 뿌리는 데이터
    List<QueTypeVO> queListDetail(@Param("authcd") String authcd);// 인증분야별 문제
    List<QueListVO> queListPrint(@Param("qtypecd") String qtypecd,
                                 @Param("authcd") String authcd);// 인증분야별 문제
/*    ArrayList<HashMap<String, String>> dailyAuthList(); */
}
