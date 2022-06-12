package com.example.repository;

import com.example.entity.TicketStateEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStateRepository extends JpaRepository<TicketStateEntity, Long> {

}
