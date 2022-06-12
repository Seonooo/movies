package com.example.repository;

import com.example.entity.RecordEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    // 기록 조회하기 - 영화코드, 회원아이디
    Page<RecordEntity> findByTicketEntity_MovieEntity_MtitleAndTicketEntity_MemberEntity_Mid(String mtitle, String mid,
            Pageable pageable);

    // 기록 조회하기 - 회원아이디
    Page<RecordEntity> findByTicketEntity_MemberEntity_Mid(String mid,
            Pageable pageable);

}
