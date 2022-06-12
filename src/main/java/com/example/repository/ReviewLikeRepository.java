package com.example.repository;

import com.example.entity.ReviewLikeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLikeEntity, Long> {

    // rcode, mid를 통해서 entity 한개 찾기
    ReviewLikeEntity findByMemberEntity_MidAndReviewEntity_Rcode(String mid, Long rcode);

    // 좋아요누른 리뷰 리스트 - 회원
    Page<ReviewLikeEntity> findByMemberEntity_Mid(String mid, Pageable pageable);
}
