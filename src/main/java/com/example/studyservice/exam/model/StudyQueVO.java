package com.example.studyservice.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyQueVO {
    private String sabun;
    private String startdt;
    private String qid;
    private int seq;
    private String ansyn;
}
