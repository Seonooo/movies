package com.example.serviceImpl;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;
import com.example.repository.BoardRepository;
import com.example.service.BoardService;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository bRepository;

    // 게시판 글쓰기
    @Override
    public int insertBoard(BoardEntity board) {
        try {
            bRepository.save(board);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 게시글 1개 불러오기
    @Override
    public BoardEntity selectOneBoard(Long no) {
        try {
            return bRepository.findById(no).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 게시글 조회수 업데이트
    @Override
    public int updateHit(Long no) {
        try {
            BoardEntity board = bRepository.findById(no).orElse(null);
            board.setBhit(board.getBhit() + 1);
            bRepository.save(board);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 게시글 삭제
    @Override
    public int deleteBoard(Long no) {
        try {
            bRepository.deleteById(no);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<BoardEntity> selectListType(Long no) {
        try {
            return bRepository.findByBtype(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<BoardEntity> selectList() {
        try {
            return bRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // @Override
    // public List<BoardEntity> selectListpagenation(String title, Long start, Long
    // end) {

    // return bRepository.selectBoardList(title, start, end);
    // }

    // @Override
    // public List<BoardEntity> selectListTypepagenation(String title, Long type,
    // Long start, Long end) {

    // return bRepository.selectBoardListType(title, type, start, end);
    // }

    @Override
    public int updateLike(Long no, Long type) {
        try {
            if (type == 1) {
                BoardEntity board = bRepository.findById(no).orElse(null);
                board.setBlike(board.getBlike() + 1);
                bRepository.save(board);

            } else if (type == 0) {
                BoardEntity board = bRepository.findById(no).orElse(null);
                board.setBlike(board.getBlike() - 1);
                bRepository.save(board);

            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public BoardProjection selectOneBoardPro(Long no) {
        try {
            return bRepository.findByBno(no);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<BoardProjection> selectListPro(String title, Pageable page) {

        try {
            return bRepository.findByBtitleContainingOrderByBnoDesc(title, page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<BoardProjection> selectListTypePro(Long type, String title, Pageable page) {

        try {
            return bRepository.findByBtypeAndBtitleContainingOrderByBnoDesc(type, title, page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long countList(String title) {
        try {
            return bRepository.countByBtitleContaining(title);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Long countListType(Long type, String title) {
        return bRepository.countByBtypeAndBtitleContaining(type, title);
    }

    @Override
    public Long selectLastBoard() {
        try {
            return bRepository.findFirstByOrderByBnoDesc().getBno();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public BoardProjection selectPrev(Long bno, Long btype) {
        try {
            return bRepository.findFirstByBnoLessThanAndBtypeOrderByBnoDesc(bno, btype);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public BoardProjection selectNext(Long bno, Long btype) {
        try {
            return bRepository.findFirstByBnoGreaterThanAndBtypeOrderByBnoAsc(bno, btype);
        } catch (Exception e) {
            return null;
        }
    }

}
