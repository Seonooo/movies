package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "MOVIELIKE")
@SequenceGenerator(name = "SEQ_MOVIELIKE", sequenceName = "SEQ_MOVIELIKE_NO", allocationSize = 1, initialValue = 1)
public class MovieLikeEntity {

    // 시퀀스
    @Id
    @GeneratedValue(generator = "SEQ_MOVIELIKE", strategy = GenerationType.SEQUENCE)
    private Long mlno;

    // 회원
    @ManyToOne
    @JoinColumn(name = "mid")
    private MemberEntity memberEntity;

    // 영화
    @ManyToOne
    @JoinColumn(name = "mcode")
    private MovieEntity movieEntity;
}
