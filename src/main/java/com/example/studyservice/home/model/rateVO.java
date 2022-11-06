package com.example.studyservice.home.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class rateVO {
    String studytype;
    String authcd;
    String authnm;
    int avg;
}
