package com.example.studyservice.queType.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueListVO {
    private String qtypecd;
    private String qtypenm;
    private String qid;
    private String lvl;
    private String anstype;
    private String authcd;
    private String authnm;
    private String createdt;
    private String source;
    private String question;
    private String itemid1;
    private String item1;
    private String itemid2;
    private String item2;
    private String itemid3;
    private String item3;
    private String itemid4;
    private String item4;
    private String answer;
    private String explanation;
}
