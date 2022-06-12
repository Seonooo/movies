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
@Table(name = "BOARDLIKE")
@SequenceGenerator(name = "SEQ_BOARDLIKE", sequenceName = "SEQ_BOARDLIKE_NO", allocationSize = 1, initialValue = 1)
public class BoardlikeEntity {
    // 게시글번호
    @Id
    @GeneratedValue(generator = "SEQ_BOARDLIKE", strategy = GenerationType.SEQUENCE)
    private Long blno;

    // 회원
    @ManyToOne
    @JoinColumn(name = "mid")
    private MemberEntity memberEntity;

    // 게시판
    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;
}
