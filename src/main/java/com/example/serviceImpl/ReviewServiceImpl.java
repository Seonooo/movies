package com.example.serviceImpl;

import com.example.entity.ReviewEntity;
import com.example.entity.TicketEntity;
import com.example.repository.ReviewRepository;
import com.example.repository.TicketRepository;
import com.example.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ReviewRepository reviewRepository;

    // 짧은리뷰 등록
    @Override
    public int insertReview(ReviewEntity reviewEntity, String mid, Long mcode) {
        try {
            // 회원아이디, 영화코드를 받아서 회원이 상영완료한 티켓내역을 가져오기
            TicketEntity ticketEntity = ticketRepository
                    .findFirstByTicketStateEntity_TscodeAndMemberEntity_MidAndMovieEntity_Mcode(3L,
                            mid, mcode);
            // 영화를 봤던 티켓내역을 넣어주기
            reviewEntity.setTicketEntity(ticketEntity);
            // 영화를 봤다면 리뷰 등록 성공
            if (ticketEntity != null) {
                reviewRepository.save(reviewEntity);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 짧은리뷰 삭제
    @Override
    public int deleteReview(Long rcode) {
        try {
            reviewRepository.deleteById(rcode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 짧은리뷰 수정
    @Override
    public int updateReview(ReviewEntity reviewEntity) {
        try {
            ReviewEntity rEntity = reviewRepository.findById(reviewEntity.getRcode()).orElse(null);
            rEntity.setRcontent(reviewEntity.getRcontent());
            // 평점은 없는 경우 이전값 따라가기
            if (reviewEntity.getRgpa() != null) {
                rEntity.setRgpa(reviewEntity.getRgpa());
            }
            reviewRepository.save(rEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 짧은리뷰 리스트 - 최신순
    @Override
    public Page<ReviewEntity> selectReviewList(int page, int size, Long mcode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("rregdate").descending());
            return reviewRepository.findByTicketEntity_MovieEntity_Mcode(mcode, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 짧은리뷰 1개 조회
    @Override
    public ReviewEntity selectReview(Long rcode) {
        try {
            return reviewRepository.findById(rcode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 짧은리뷰 리스트 - 고객
    @Override
    public Page<ReviewEntity> selectReviewListCustomer(int page, int size, String mid) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return reviewRepository.findByTicketEntity_MemberEntity_Mid(mid, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 짧은리뷰 높은 평점순
    @Override
    public Page<ReviewEntity> selectReviewListOfGpaDesc(int page, int size, Long mcode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("rgpa").descending());
            return reviewRepository.findByTicketEntity_MovieEntity_Mcode(mcode, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 짧은리뷰 낮은 평점순
    @Override
    public Page<ReviewEntity> selectReviewListOfGpaAsc(int page, int size, Long mcode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("rgpa").ascending());
            return reviewRepository.findByTicketEntity_MovieEntity_Mcode(mcode, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 리뷰 1개 유무 조회 - 회원아이디, 영화코드 - 영화당 회원이 작성 리뷰1개 제한
    @Override
    public int selectReviewOfMcode(String mid, Long mcode) {
        try {
            ReviewEntity reviewEntity = reviewRepository
                    .findByTicketEntity_MemberEntity_MidAndTicketEntity_MovieEntity_Mcode(mid, mcode);
            if (reviewEntity == null) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
