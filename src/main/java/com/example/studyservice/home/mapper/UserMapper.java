package com.example.studyservice.home.mapper;

import com.example.studyservice.home.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO getUserAccount(String userId);
    int saveUser(UserVO userVO);
}
