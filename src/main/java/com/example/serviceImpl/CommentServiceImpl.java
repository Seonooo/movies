package com.example.serviceImpl;

import java.util.List;

import com.example.entity.CommentEntity;
import com.example.entity.CommentProjection;
import com.example.repository.CommentRepository;
import com.example.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository cRepository;

    @Override
    public int insertComment(CommentEntity comment) {
        try {
            cRepository.save(comment);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public List<CommentProjection> selectlistBno(Long bno) {
        try {
            return cRepository.findByBoardEntity_bnoOrderByCnoDesc(bno);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public int deleteComment(Long cno) {
        try {
            cRepository.deleteById(cno);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public CommentProjection selectOnePro(Long cno) {
        try {
            return cRepository.findByCno(cno);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public CommentEntity selectOne(Long cno) {

        return cRepository.findById(cno).orElse(null);
    }

}
