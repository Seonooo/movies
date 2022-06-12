package com.example.service;

import java.util.List;
import com.example.entity.MovieCategoryEntity;
import com.example.entity.MovieEntity;
import com.example.entity.PosterEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MovieService {
    // 영화 크롤링
    public int insertMovies(String naverRankUrl);

    // 영화 한개 등록
    public int insertMovie(MovieEntity movie, String nation, Long filmRating, Long msCode, Long ccode);

    // 영화 업데이트(마감일)
    public int updateMovie(MovieEntity movie);

    // 영화 한개 삭제
    public int deleteMovie(Long mcode);

    // 영화 전체 삭제
    public int deleteMovies();

    // 영화 한개 상세페이지
    public MovieEntity selectMovie(Long code);

    // 영화리스트(한 페이지)
    public Page<MovieEntity> selectMovies(Integer page, Integer size);

    // 영화장르별 장르 - 영화 조인
    public Page<MovieCategoryEntity> selectMovieGenre(Integer page, Integer size, Long mcCode);

    // 영화 상태별(상영여부)
    public Page<MovieEntity> selectMovieState(Integer page, Integer size, Long msCode);

    // 영화 제목별
    public Page<MovieEntity> selectMovieTitle(Integer page, Integer size, String title);

    // 영화 포스터 등록
    public int insertMoviePoster(MultipartFile[] files, Long mcode);

    // 대표이미지 가져오기
    public PosterEntity selectMovieMainPoster(Long mcode);

    // 이미지 가져오기
    public PosterEntity selectMoviePoster(Long pcode);

    // 이미지 리스트 가져오기
    public List<PosterEntity> selectMoviePosters(Long mcode);

    // 포스터 대표이미지 설정
    public int updateMovieMainPoster(Long pcode);

    // 포스터 1개 삭제
    public int deleteMoviePoster(Long pcode);

    // 포스터 여러개 삭제
    public int deleteMoviePosters(Long[] pcode);

    // 포스터 1개 업데이트(image를 받은경우)
    public int updateMoviePoster(Long pcode, MultipartFile file);

    // 포스터 1개 업데이트(url로 받은경우)
    public int updateMoviePosterUrl(Long pcode, String url);

    // 영화 장르 크롤링
    public int insertGenre();

    // 영화 장르 리스트
    public List<MovieCategoryEntity> selectMovieCategories(Long mcode);

    // 영화 좋아요
    public int updateLike(Long mcode, Long type);

}