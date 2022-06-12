package com.example.serviceImpl;

import com.example.entity.BoardlikeEntity;
import com.example.repository.BoardlikeRepository;
import com.example.service.BoardlikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardlikeServiceImpl implements BoardlikeService {

    @Autowired
    BoardlikeRepository blRepository;

    @Override
    public int findLike(String mid, Long bno) {
        try {
            int ret = blRepository.countByMemberEntity_midAndBoardEntity_bno(mid, bno);
            if (ret == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateLike(String mid, Long bno) {
        try {

            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int deleteLike(String mid, Long bno) {
        try {
            BoardlikeEntity boardlike = blRepository.findByMemberEntity_midAndBoardEntity_bno(mid, bno);
            blRepository.delete(boardlike);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int insertLike(BoardlikeEntity boardlike) {
        try {
            blRepository.save(boardlike);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

}
