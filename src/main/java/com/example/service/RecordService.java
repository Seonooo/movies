package com.example.service;

import com.example.entity.RecordEntity;

import org.springframework.stereotype.Service;

@Service
public interface RecordService {
    int insertRecord(RecordEntity record);
}
