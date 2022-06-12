package com.example.repository;

import java.util.List;
import com.example.entity.PosterEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<PosterEntity, Long> {
    // mcode로 poster들 찾기
    List<PosterEntity> findByMovieEntity_Mcode(Long mcode);


    // 메인 포스터
    public PosterEntity findFirstByMovieEntity_McodeOrderByPheadDesc(Long mcode);

}
