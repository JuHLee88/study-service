package com.example.studyservice.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {
    private String qid;
    private String qtypecd;
    private String question;
    private String anstype;
    private String source;
    private String url;
    private String writer;
    private String modifyuser;
    private String createdt;
    private String modifydt;
    private String requser;
    private String lvl;
    private String useyn;
    private String admnum;
    private String explanation;

}
