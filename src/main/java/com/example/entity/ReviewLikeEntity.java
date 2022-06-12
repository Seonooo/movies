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
@Table(name = "REVIEWLIKE")
@SequenceGenerator(name = "SEQ_REVIEWLIKE", sequenceName = "SEQ_REVIEWLIKE_NO", allocationSize = 1, initialValue = 1)
public class ReviewLikeEntity {

    // 시퀀스
    @Id
    @GeneratedValue(generator = "SEQ_REVIEWLIKE", strategy = GenerationType.SEQUENCE)
    private Long rlno;

    // 회원
    @ManyToOne
    @JoinColumn(name = "mid")
    private MemberEntity memberEntity;

    // 리뷰
    @ManyToOne
    @JoinColumn(name = "rcode")
    private ReviewEntity reviewEntity;

}
