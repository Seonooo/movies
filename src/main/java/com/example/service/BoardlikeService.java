package com.example.service;

import com.example.entity.BoardlikeEntity;

import org.springframework.stereotype.Service;

@Service
public interface BoardlikeService {
    int findLike(String mid, Long bno);

    int updateLike(String mid, Long bno);

    int deleteLike(String mid, Long bno);

    int insertLike(BoardlikeEntity boardlike);
}
