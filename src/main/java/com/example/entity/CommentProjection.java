package com.example.entity;

import java.util.Date;

import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Value;

public interface CommentProjection {
    Long getCno();

    @Lob
    String getCcontent();

    Date getCregdate();

    @Value("#{target.boardEntity.bno}")
    Long getBoardEntityBno();

    @Value("#{target.memberEntity.mid}")
    String getMemberEntityMid();

}
