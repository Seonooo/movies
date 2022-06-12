package com.example.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface BoardProjection {

    Long getBno();

    Long getBtype();

    String getBtitle();

    String getBcontent();

    Long getBhit();

    Long getBlike();

    Date getBregdate();

    @Value("#{target.memberEntity.mid}")
    String getMemberEntityMid();

}