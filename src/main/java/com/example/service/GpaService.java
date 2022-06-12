package com.example.service;

import com.example.entity.GpaEntity;

import org.springframework.stereotype.Service;

@Service
public interface GpaService {

    // 영화코드로 평점 가져오기
    public GpaEntity selectGpaMcode(Long mcode);

    // 리뷰가 등록될 때 gpa 갱신
    public int updateGpa(Long mcode);
}
