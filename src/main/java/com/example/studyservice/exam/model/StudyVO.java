package com.example.studyservice.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyVO {
    String sabun;
    String startdt;
    String studytype;
    String authcd;
    String enddt;
    String score;
    String middt;
}
