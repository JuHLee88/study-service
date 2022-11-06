package com.example.studyservice.queType.service;

import com.example.studyservice.board.mapper.BoardMapper;
import com.example.studyservice.board.model.BoardListVO;
import com.example.studyservice.board.model.BoardVO;
import com.example.studyservice.queType.mapper.QueTypeMapper;
import com.example.studyservice.queType.model.QueListVO;
import com.example.studyservice.queType.model.QueTypeVO;
import com.example.studyservice.queType.service.QueTypeService;
import com.example.studyservice.queType.mapper.QueTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QueTypeServiceImpl implements QueTypeService {

    private QueTypeMapper queTypeMapper;

    @Autowired
    public QueTypeServiceImpl(QueTypeMapper queTypeMapper){
        this.queTypeMapper = queTypeMapper;
    }

    @Override
    public List<QueTypeVO> queList() {
        return  queTypeMapper.queList();
    }
    @Override
    public List<QueTypeVO> queListDetail(String authcd) {
        return  queTypeMapper.queListDetail(authcd);
    }

    @Override
    public List<QueListVO> queListPrint(String qtypecd, String authcd) {
        return  queTypeMapper.queListPrint(qtypecd, authcd);
    }

}
