package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.VisitorEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<VisitorEntity, String> {
    List<VisitorEntity> findByVphoneContainingAndTregdate(String vphone, LocalDate date, Pageable pageable);

    List<VisitorEntity> findByTregdateLessThan(LocalDate date);

    int countByVphone(String vphone);

}
