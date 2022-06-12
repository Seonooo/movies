package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "SCHEDULE_VIEW")
public class ScheduleView {

    @Id
    Long sno;

    Long tno;

    Date tregdate;

    Long mcode;

    String mid;

    Long thcode;

    Long tscode;

    Date tdate;

    Date tend;

    Date tstart;


    String mtitle;


}
