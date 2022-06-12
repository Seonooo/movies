package com.example.repository;

import java.util.List;

import com.example.entity.BoardimgEntity;
import com.example.entity.BoardimgProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardimgRepository extends JpaRepository<BoardimgEntity, Long> {

    List<BoardimgProjection> findByBoardEntity_Bno(Long no);

    BoardimgProjection findByBino(Long no);

}

