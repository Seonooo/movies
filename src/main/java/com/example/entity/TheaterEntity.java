package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "THEATER")
@SequenceGenerator(name = "TH_CODE", sequenceName = "SEQ_THEATER_CODE", allocationSize = 1, initialValue = 1)
public class TheaterEntity {

    @Id
    @GeneratedValue(generator = "TH_CODE", strategy = GenerationType.SEQUENCE) // 상영관 코드
    private Long thcode;

    @Column(name = "THMAXIMUM") // 최대 인원수
    private Long thmaximum;

    @Column(name = "THPRICE") // 가격
    private Long thprice;

    // 0 => 상영 종료, 1=> 상영 가능
    @Column(name = "THSTATE") // 상영 가능 여부
    private Long thstate = 1L;

    @Lob
    @Column(name = "THCONTENT") // 상영관 설명
    private String thcontent;

    @Column(name = "THCOUNT") // 관람 인원 수
    private Long thcount;

    @Column(name = "THTYPE", length = 50) // 상영관 타입(nomal, couple, family)
    private String thtype;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss.sss")
    @CreationTimestamp // CURRENT_DATE
    private Date thregdate;
}