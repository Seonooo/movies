package com.example.service;

import com.example.entity.TheaterEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TheaterService {

    // 상영관 1개 등록하기
    public int insertTheater(TheaterEntity theaterEntity);

    // 상영관 1개 변경하기
    public int updateTheater(TheaterEntity theaterEntity);

    // 상영관 마감 | 오픈
    public int updateTheaterState(Long tCode, Long type);

    // 상영관 1개 가져오기
    public TheaterEntity selectTheater(Long tcode);

    // 상영관 리스트 가져오기
    public Page<TheaterEntity> selectListTheater(int page, int size);
}
