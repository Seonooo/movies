package com.example.service;

import java.util.List;

import com.example.entity.BoardimgEntity;
import com.example.entity.BoardimgProjection;

import org.springframework.stereotype.Service;

@Service
public interface BoardimgService {
    int insertBoardimg(BoardimgEntity boardimg);

    BoardimgEntity selectOneBoardimg(Long no);

    List<BoardimgProjection> selectListBoardimg(Long no);

    int deleteBoardimg(Long no);

    BoardimgProjection selectOneBoardimgPro(Long no);

}
