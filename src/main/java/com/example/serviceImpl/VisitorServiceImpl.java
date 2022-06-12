package com.example.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import com.example.entity.VisitorEntity;
import com.example.repository.VisitorRepository;
import com.example.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    VisitorRepository vRepository;

    @Override
    public int insertVisitor(VisitorEntity visitor) {
        try {
            vRepository.save(visitor);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public List<VisitorEntity> selectVisitorList(String vphone, LocalDate date, Pageable pageable) {
        try {
            return vRepository.findByVphoneContainingAndTregdate(vphone, date, pageable);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<VisitorEntity> selectVisitorListBeforeToday(LocalDate date) {
        try {
            return vRepository.findByTregdateLessThan(date);
        } catch (Exception e) {

            return null;
        }

    }

    @Override
    public int deleteVisitor(List<VisitorEntity> visitor) {
        try {
            vRepository.deleteAllInBatch(visitor);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public int findOneVisitor(String vphone) {
        try {

            return vRepository.countByVphone(vphone);
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public List<VisitorEntity> selectAll() {
        try {
            return vRepository.findAll();

        } catch (Exception e) {
            return null;
        }

    }

    @Override

    public VisitorEntity selectone(String vphone) {
        try {
            return vRepository.findById(vphone).orElse(null);
        } catch (Exception e) {
            return null;
        }

    }

}
