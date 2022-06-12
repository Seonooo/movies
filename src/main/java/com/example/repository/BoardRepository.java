package com.example.repository;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
        public List<BoardEntity> findByBtype(Long type);

        public BoardProjection findByBno(long no);

        public List<BoardProjection> findByBtitleContainingOrderByBnoDesc(String title, Pageable page);

        public List<BoardProjection> findByBtypeAndBtitleContainingOrderByBnoDesc(Long type, String title,
                        Pageable page);

        public Long countByBtypeAndBtitleContaining(Long type, String title);

        public Long countByBtitleContaining(String title);

        public BoardEntity findFirstByOrderByBnoDesc();

        // 다음글
        public BoardProjection findFirstByBnoLessThanAndBtypeOrderByBnoDesc(Long bno, Long btype);

        // 이전글
        public BoardProjection findFirstByBnoGreaterThanAndBtypeOrderByBnoAsc(Long bno, Long btype);

}