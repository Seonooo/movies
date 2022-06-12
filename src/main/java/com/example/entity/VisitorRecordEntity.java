package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "VISITORRECORD")
@SequenceGenerator(name = "SEQ_VISRECORD", sequenceName = "SEQ_VISRECORD_CODE", allocationSize = 1, initialValue = 1)

public class VisitorRecordEntity {
    // 기록코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VISRECORD")
    private Long vrcode;
    // 등록일
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss.sss")
    @CreationTimestamp // CURRENT_DATE
    private Date vrregdate;
    // 비회원 예매
    @ManyToOne
    @JoinColumn(name = "vphone")
    private VisitorTicketEntity visitorTicketEntity;
    // 예매상태
    @ManyToOne
    @JoinColumn(name = "tscode")
    private TicketStateEntity ticketStateEntity;
}
