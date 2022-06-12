package com.example.serviceImpl;

import com.example.entity.RecordEntity;
import com.example.repository.RecordRepository;
import com.example.service.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository rRepository;

    @Override
    public int insertRecord(RecordEntity record) {
        try {
            rRepository.save(record);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
