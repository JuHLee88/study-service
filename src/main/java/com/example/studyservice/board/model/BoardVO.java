package com.example.studyservice.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO { //board db row 전체
    private int idx;
    private String gubun;
    private String title;
    private String content;
    private String writeuser;
    private String qid;
    private String writedt;
    private String modifyuser;
    private String modifydt;

}
