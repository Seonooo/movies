package com.example.repository;

import java.util.List;

import com.example.entity.ReviewEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // 영화코드가 일치하는 리뷰리스트 - 페이지네이션
    Page<ReviewEntity> findByTicketEntity_MovieEntity_Mcode(Long mcode, Pageable pageable);

    // 영화코드가 일치하는 리뷰리스트 - 리스트
    List<ReviewEntity> findByTicketEntity_MovieEntity_Mcode(Long mcode);

    // 회원아이디가 일치하는 리뷰리스트
    Page<ReviewEntity> findByTicketEntity_MemberEntity_Mid(String mid, Pageable pageable);

    // 영화, 회원아이디 일치하는 리뷰
    ReviewEntity findByTicketEntity_MemberEntity_MidAndTicketEntity_MovieEntity_Mcode(String mid, Long mcode);

}
