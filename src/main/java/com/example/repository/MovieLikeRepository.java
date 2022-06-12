package com.example.repository;

import com.example.entity.MovieLikeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLikeRepository extends JpaRepository<MovieLikeEntity, Long> {

    // mcode, mid를 통해서 entity 한개 찾기
    MovieLikeEntity findByMemberEntity_MidAndMovieEntity_Mcode(String mid, Long mcode);

    // 좋아요누른 영화 리스트 - 회원
    Page<MovieLikeEntity> findByMemberEntity_Mid(String mid, Pageable pageable);
}
