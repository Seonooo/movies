package com.example.serviceImpl;

import com.example.entity.TheaterEntity;
import com.example.repository.TheaterRepository;
import com.example.service.TheaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    TheaterRepository theaterRepository;

    // 상영관 1개 등록
    @Override
    public int insertTheater(TheaterEntity theaterEntity) {
        try {
            theaterRepository.save(theaterEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 상영관 1개 변경
    @Override
    public int updateTheater(TheaterEntity theaterEntity) {
        try {
            TheaterEntity theaterEntity2 = theaterRepository.findById(theaterEntity.getThcode()).orElse(null);

            theaterEntity2.setThprice(theaterEntity.getThprice());
            theaterEntity2.setThcontent(theaterEntity.getThcontent());
            // 상영관 관람인원수
            theaterEntity2.setThcount(theaterEntity.getThcount());
            // 최대인원수
            theaterEntity2.setThmaximum(theaterEntity.getThmaximum());
            theaterEntity2.setThtype(theaterEntity.getThtype());

            // 변경내용 저장
            theaterRepository.save(theaterEntity2);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 상영관 상태 변경
    @Override
    public int updateTheaterState(Long tCode, Long type) {
        try {
            TheaterEntity theaterEntity = theaterRepository.findById(tCode).orElse(null);
            // type == 2 : 마감, type == 3 : 오픈
            if (type == 2) {
                theaterEntity.setThstate(0L);
            } else if (type == 3) {
                theaterEntity.setThstate(1L);
            }
            // 변경내용 저장
            theaterRepository.save(theaterEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 상영관 1개 조회
    @Override
    public TheaterEntity selectTheater(Long tcode) {
        try {
            return theaterRepository.findById(tcode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 상영관 리스트
    @Override
    public Page<TheaterEntity> selectListTheater(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return theaterRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
