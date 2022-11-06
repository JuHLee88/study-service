package com.example.studyservice.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyVO { //reply
    private int idx;
    private int seq;
    private String content;
    private String writeuser;
    private String writedt;
}