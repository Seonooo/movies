package com.example.serviceImpl;

import com.example.entity.MovieLikeEntity;
import com.example.repository.MemberRepository;
import com.example.repository.MovieLikeRepository;
import com.example.repository.MovieRepository;
import com.example.service.MovieLikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieLikeServiceImpl implements MovieLikeService {

    @Autowired
    MovieLikeRepository movieLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MovieRepository movieRepository;

    // 좋아요 유무 체크
    // 있으면 1 없으면 0
    @Override
    public int selectMovieLike(String mid, Long mcode) {
        try {
            MovieLikeEntity movieLikeEntity = movieLikeRepository.findByMemberEntity_MidAndMovieEntity_Mcode(mid,
                    mcode);
            if (movieLikeEntity != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 추가
    @Override
    public int insertMovieLike(String mid, Long mcode) {
        try {
            MovieLikeEntity movieLikeEntity = new MovieLikeEntity();
            movieLikeEntity.setMemberEntity(memberRepository.findById(mid).orElse(null));
            movieLikeEntity.setMovieEntity(movieRepository.findById(mcode).orElse(null));
            movieLikeRepository.save(movieLikeEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 삭제
    @Override
    public int deleteMovieLike(String mid, Long mcode) {
        try {
            movieLikeRepository
                    .deleteById(movieLikeRepository.findByMemberEntity_MidAndMovieEntity_Mcode(mid, mcode).getMlno());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 좋아요 누른 영화 리스트
    @Override
    public Page<MovieLikeEntity> selectMoviesLike(String mid, int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return movieLikeRepository.findByMemberEntity_Mid(mid, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
