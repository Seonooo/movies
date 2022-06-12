package com.example.service;

import java.util.List;

import com.example.entity.CommentEntity;
import com.example.entity.CommentProjection;

import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    int insertComment(CommentEntity comment);

    List<CommentProjection> selectlistBno(Long bno);

    int deleteComment(Long cno);

    CommentProjection selectOnePro(Long cno);

    CommentEntity selectOne(Long cno);

}
