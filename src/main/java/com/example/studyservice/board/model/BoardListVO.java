package com.example.studyservice.board.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListVO { //게시판 첫 화면 데이터 형식
    private int idx;
    private String gubun;
    private String title;
    private String replynum;
    private String writeuser;
    private String writedt;
}
