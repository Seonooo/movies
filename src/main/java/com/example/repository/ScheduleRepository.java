package com.example.repository;

import com.example.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    ScheduleEntity findByTicketEntity_Tno(Long tno);
}
