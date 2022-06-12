package com.example.repository;

import java.util.Date;
import java.util.List;

import com.example.entity.ScheduleView;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleViewRepository extends JpaRepository<ScheduleView, Long> {

    List<ScheduleView> findByThcodeAndTdateBetween(Long thcode, Date startdate, Date enddate);

    List<ScheduleView> findByThcode(Long thcode);

}
