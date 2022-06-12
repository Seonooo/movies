package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    // 게시글 등록
    int insertBoard(BoardEntity board);

    // 게시글 1개 가져오기
    BoardEntity selectOneBoard(Long no);

    // 게시글 조회수 1증가
    int updateHit(Long no);

    // 게시글 좋아요수 수정
    int updateLike(Long no, Long type);

    // 게시글 삭제
    int deleteBoard(Long no);

    // 게시글 리스트 가져오기(타입별)
    List<BoardEntity> selectListType(Long no);

    // 게시글 리스트 가져오기(전체)
    List<BoardEntity> selectList();

    BoardProjection selectOneBoardPro(Long no);

    // 게시글 리스트 검색+페이지네이션
    List<BoardProjection> selectListPro(String title, Pageable page);

    // 게시글 리스트 검색+페이지네이션
    List<BoardProjection> selectListTypePro(Long type, String title, Pageable page);

    // 게시글 전체 개수
    Long countList(String title);

    // 게시글 전체 개수(타입별)
    Long countListType(Long type, String title);

    // 가장 최근 게시글 가져오기
    Long selectLastBoard();

    // 이전글
    BoardProjection selectPrev(Long bno, Long btype);

    // 다음글
    BoardProjection selectNext(Long bno, Long btype);
}
