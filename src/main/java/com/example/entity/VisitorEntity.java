package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "VISITOR")
public class VisitorEntity {
    // 비회원 연락처
    @Id
    @Column(length = 30)
    private String vphone;
    // 비회원 이름
    @Column(length = 30)
    private String vname;
    // 비회원 암호
    @Column(length = 100)
    private String vpw;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate tregdate;

    // 비회원예매
    // @JsonBackReference
    // @OneToMany(mappedBy = "visitorEntity")
    // private List<VisitorTicketEntity> visitorTicketEntityList = new
    // ArrayList<>();
}
