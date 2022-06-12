package com.example.service;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.VisitorEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface VisitorService {
    int insertVisitor(VisitorEntity visitor);

    List<VisitorEntity> selectVisitorList(String vphone, LocalDate date, Pageable pageable);

    List<VisitorEntity> selectVisitorListBeforeToday(LocalDate date);

    List<VisitorEntity> selectAll();


    VisitorEntity selectone(String vphone);

    int deleteVisitor(List<VisitorEntity> visitor);

    int findOneVisitor(String vphone);

}
