package com.example.entity;

import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Value;

public interface BoardimgProjection {

    Long getBino();

    @Lob
    byte[] getBiimage();

    String getBiimagetype();

    Long getBiimagesize();

    String getBiimagename();

    @Value("#{target.boardEntity.bno}")
    Long getBoardEntityBno();

}
