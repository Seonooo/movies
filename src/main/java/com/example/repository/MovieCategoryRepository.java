package com.example.repository;

import java.util.List;

import com.example.entity.MovieCategoryEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCategoryRepository extends JpaRepository<MovieCategoryEntity, Long> {

    // 영화 - 장르 리스트
    Page<MovieCategoryEntity> findByCategoryEntity_Ccode(Long ccode, Pageable pageable);

    // 영화코드 기준으로 찾기
    List<MovieCategoryEntity> findByMovieEntity_Mcode(Long mcode);
}