package com.example.serviceImpl;

import java.util.List;

import com.example.entity.GpaEntity;
import com.example.entity.MovieEntity;
import com.example.entity.ReviewEntity;
import com.example.repository.GpaRepository;
import com.example.repository.MovieRepository;
import com.example.repository.ReviewRepository;
import com.example.service.GpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpaServiceImpl implements GpaService {

    @Autowired
    GpaRepository gpaRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    // 영화 평점찾기
    @Override
    public GpaEntity selectGpaMcode(Long mcode) {
        try {
            return gpaRepository.findByMovieEntity_Mcode(mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateGpa(Long mcode) {
        try {
            Long sumGpa = 0L;
            MovieEntity movieEntity = movieRepository.findById(mcode).orElse(null);
            List<ReviewEntity> reviewEntities = reviewRepository
                    .findByTicketEntity_MovieEntity_Mcode(mcode);

            if (reviewEntities != null) {
                for (ReviewEntity review : reviewEntities) {
                    // 리뷰한개당 평점합계에 평점더해주기
                    // System.out.println("review.getRcode()" + review.getRcode());
                    sumGpa += review.getRgpa();
                }
                // 평균평점
                Float Gpa = sumGpa.floatValue() / reviewEntities.size();
                // 영화에 해당하는 gpa
                GpaEntity gpaEntity = gpaRepository.findByMovieEntity_Mcode(mcode);
                // gpa가 없는 영화라면 - 새로운 gpaEntity생성
                if (gpaEntity == null) {
                    // System.out.println("Gpa" + Gpa);
                    GpaEntity newGpaEntity = new GpaEntity();
                    newGpaEntity.setGgpa(Gpa);
                    newGpaEntity.setMovieEntity(movieEntity);
                    gpaRepository.save(newGpaEntity);
                }
                // gpa가 있는 영화라면 - 평균평점바꿔주기
                else {
                    gpaEntity.setGgpa(Gpa);
                    gpaRepository.save(gpaEntity);
                }
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
