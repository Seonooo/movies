package com.example.repository;

import java.util.List;

import com.example.entity.TicketEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    // 멤버 아이디로 티켓리스트 가져오기 -- 관리자
    Page<TicketEntity> findByMemberEntity_Mid(String mid, Pageable pageable);

    // 상영 상태별 티켓리스트 -- 관리자
    Page<TicketEntity> findByTicketStateEntity_Tscode(Long tscode, Pageable pageable);

    // 상영관별 -- 관리자
    Page<TicketEntity> findByTheaterEntity_Thcode(Long thcode, Pageable pageable);

    // 영화별 -- 관리자
    Page<TicketEntity> findByMovieEntity_Mtitle(String mtitle, Pageable pageable);

    // 상영 상태별 티켓리스트 -- 고객
    Page<TicketEntity> findByTicketStateEntity_TscodeAndMemberEntity_Mid(Long tscode, String mid, Pageable pageable);

    // 멤버 아이디로 티켓 1개 가져오기 -- 고객
    TicketEntity findByTnoAndMemberEntity_Mid(Long tno, String mid);

    // 회원 아이디와 일치하고 상영완료상태인 리스트
    TicketEntity findFirstByTicketStateEntity_TscodeAndMemberEntity_MidAndMovieEntity_Mcode(Long tscode, String mid,
            Long mcode);

    TicketEntity findFirstByOrderByTnoDesc();

    List<TicketEntity> findByTicketStateEntity_Tscode(Long tscode);
}
