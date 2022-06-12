package com.example.service;

import com.example.entity.MemberEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    // 회원가입
    public int insertMember(MemberEntity member);

    // 회원가입
    public int updateprofile(MemberEntity member);

    // 회원한명 조회
    public MemberEntity getMember(String mid);

    // 회원정보수정
    public int updateMember(MemberEntity member);

    // 회원탈퇴
    public int deleteMember(String mid);

    public int findMember(String mid);

    // 아이디 찾기
    public MemberEntity findMid(String mname, String mphone);

    // 비밀번호 유무 체크
    public int findPwChk(String mid, String mname, String mphone);

    // 새 비밀번호
    public int findMpw(MemberEntity memberEntity);

}
