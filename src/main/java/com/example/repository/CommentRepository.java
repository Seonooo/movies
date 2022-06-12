package com.example.repository;

import java.util.List;

import com.example.entity.CommentEntity;
import com.example.entity.CommentProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentProjection> findByBoardEntity_bnoOrderByCnoDesc(Long bno);

    CommentProjection findByCno(Long cno);

}
