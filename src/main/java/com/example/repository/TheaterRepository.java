package com.example.repository;

import com.example.entity.TheaterEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, Long> {

}
