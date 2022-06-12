package com.example.repository;

import com.example.entity.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    int countByMid(String mid);

    // (이름 연락처)로 아이디 찾기
    MemberEntity findByMnameAndMphone(String mname, String mphone);

    // (아이디 이름 연락처)로 비밀번호 찾기
    MemberEntity findByMidAndMnameAndMphone(String mid, String mname, String mphone);
}
