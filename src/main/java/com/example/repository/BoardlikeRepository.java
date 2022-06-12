package com.example.repository;

import com.example.entity.BoardlikeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardlikeRepository extends JpaRepository<BoardlikeEntity, Long> {
    int countByMemberEntity_midAndBoardEntity_bno(String mid, Long bno);

    BoardlikeEntity findByMemberEntity_midAndBoardEntity_bno(String mid, Long bno);
}
