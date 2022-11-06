package com.example.studyservice.home.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class myStudyDayVO {
    int idx;
    String authcd;
    String authnm;
    int correct;
    String score;
    String enddt;
}
