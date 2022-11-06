package com.example.studyservice.home.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ansChkVO {
    String authcd;
    String studytype;
    String qid;
    String ansyn;
    String ans;
    String question;
    String qtypecd;
    String qtypenm;
    String explanation;
    String lvl;
    String item1;
    String item2;
    String item3;
    String item4;
}
