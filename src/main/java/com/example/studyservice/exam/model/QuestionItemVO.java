package com.example.studyservice.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionItemVO {
    private String qid;
    private String itemId;
    private String item;
    private String ansCd;

}
