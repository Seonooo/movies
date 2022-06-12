package com.example.repository;

import com.example.entity.GpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GpaRepository extends JpaRepository<GpaEntity, Long> {
    GpaEntity findByMovieEntity_Mcode(Long mcode);
}
