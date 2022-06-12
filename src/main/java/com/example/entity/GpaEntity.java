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
@Table(name = "GPA")
@SequenceGenerator(name = "SEQ_GPA", sequenceName = "SEQ_GPA_CODE", allocationSize = 1, initialValue = 1)

public class GpaEntity {
    // 평점 코드
    @Id
    @GeneratedValue(generator = "SEQ_GPA", strategy = GenerationType.SEQUENCE)
    private Long gcode;
    // 평균평점
    private Float ggpa;
    // 영화테이블
    @ManyToOne
    @JoinColumn(name = "mcode")
    private MovieEntity movieEntity;
}