package com.example.service;

import com.example.entity.MovieLikeEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface MovieLikeService {

    // 영화 좋아요 유무 찾기
    public int selectMovieLike(String mid, Long mcode);

    // 좋아요 추가
    public int insertMovieLike(String mid, Long mcode);

    // 좋아요 삭제
    public int deleteMovieLike(String mid, Long mcode);

    // 좋아요 누른 영화리트스
    public Page<MovieLikeEntity> selectMoviesLike(String mid, int size, int page);
}
