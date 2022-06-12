package com.example.serviceImpl;

import java.util.List;

import com.example.entity.BoardimgEntity;
import com.example.entity.BoardimgProjection;
import com.example.repository.BoardimgRepository;
import com.example.service.BoardimgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BoardimgServiceImpl implements BoardimgService {

    @Autowired
    BoardimgRepository bRepository;

    @Override
    public int insertBoardimg(BoardimgEntity boardimg) {
        try {
            bRepository.save(boardimg);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public BoardimgEntity selectOneBoardimg(Long no) {

        return bRepository.findById(no).orElse(null);
    }

    @Override
    public List<BoardimgProjection> selectListBoardimg(Long no) {

        return bRepository.findByBoardEntity_Bno(no);
    }

    @Override
    public int deleteBoardimg(Long no) {
        try {
            bRepository.deleteById(no);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public BoardimgProjection selectOneBoardimgPro(Long no) {
        try {
            return bRepository.findByBino(no);
        } catch (Exception e) {
            return null;
        }

    }

}
