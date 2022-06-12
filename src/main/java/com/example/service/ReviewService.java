package com.example.service;

import com.example.entity.ReviewEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    // 짧은리뷰 등록
    public int insertReview(ReviewEntity reviewEntity, String mid, Long mcode);

    // 짧은리뷰 삭제
    public int deleteReview(Long rcode);

    // 짧은리뷰 수정
    public int updateReview(ReviewEntity reviewEntity);

    // 짧은리뷰 리스트 - 최신순
    public Page<ReviewEntity> selectReviewList(int page, int size, Long mcode);

    // 짧은리뷰 리스트 - 높은 평점순
    public Page<ReviewEntity> selectReviewListOfGpaDesc(int page, int size, Long mcode);

    // 짧은리뷰 리스트 - 낮은 평점순
    public Page<ReviewEntity> selectReviewListOfGpaAsc(int page, int size, Long mcode);

    // 짧은리뷰 1개 조회 - 리뷰코드
    public ReviewEntity selectReview(Long rcode);

    // 리뷰 1개 유무 조회 - 회원아이디, 영화코드 - 영화당 회원이 작성 리뷰1개 제한
    public int selectReviewOfMcode(String mid, Long mcode);

    // 짧은리뷰 리스트 - 회원
    public Page<ReviewEntity> selectReviewListCustomer(int page, int size, String mid);
}
