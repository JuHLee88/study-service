package com.example.studyservice.queType.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueTypeVO {
    private String authcd;
    private String a_cd;
    private String authcdnm;
    private String cd;
    private String nm;
    private int quecnt;
}
