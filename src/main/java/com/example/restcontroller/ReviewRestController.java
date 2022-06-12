package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.ReviewEntity;
import com.example.jwt.jwtUtil;
import com.example.service.GpaService;
import com.example.service.ReviewService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewRestController {

    @Autowired
    jwtUtil jwt;

    @Autowired
    ReviewService reviewService;

    @Autowired
    GpaService gpaService;;

    // 짧은리뷰 등록하기
    // http://127.0.0.1:9090/ROOT/api/review/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertReview(@RequestBody ReviewEntity reviewEntity,
            @RequestParam(name = "mcode") Long mcode,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 회원 id 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            int ret = 0;
            if (mid != null) {
                // 리뷰를 작성한적이 있는지 체크
                ret = reviewService.selectReviewOfMcode(mid, mcode);
                map.put("status", 100);
                if (ret == 1) {
                    // 영화를 봤는지 체크해서 등록
                    ret += reviewService.insertReview(reviewEntity, mid, mcode);
                    if (ret == 2) {
                        // 리뷰가 등록되면 평점 갱신
                        ret += gpaService.updateGpa(mcode);
                        if (ret == 3) {
                            map.put("status", 200);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 짧은리뷰 삭제하기
    // http://127.0.0.1:9090/ROOT/api/review/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteReview(@RequestParam(name = "rcode") Long rcode,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 회원 id 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            ReviewEntity reviewEntity = reviewService.selectReview(rcode);
            // 회원 id와 리뷰작성 id 일치하는지 확인
            if (mid.equals(reviewEntity.getTicketEntity().getMemberEntity().getMid())) {
                int ret = reviewService.deleteReview(rcode);
                if (ret == 1) {
                    // 리뷰가 삭제되면 평점갱신
                    ret += gpaService.updateGpa(reviewEntity.getTicketEntity().getMovieEntity().getMcode());
                    if (ret == 2) {
                        map.put("status", 200);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 짧은리뷰 수정하기
    // http://127.0.0.1:9090/ROOT/api/review/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateReview(@RequestBody ReviewEntity reviewEntity,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 회원 id 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            // 회원 id와 리뷰작성 id 일치하는지 확인
            ReviewEntity oldreview = reviewService.selectReview(reviewEntity.getRcode());
            System.out.println(mid);
            if (mid.equals(oldreview.getTicketEntity().getMemberEntity().getMid())) {
                System.out.println(oldreview.getTicketEntity().getMemberEntity().getMid());
                int ret = reviewService.updateReview(reviewEntity);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 짧은리뷰 리스트 - 회원
    // http://127.0.0.1:9090/ROOT/api/review/selectlist_customer
    @RequestMapping(value = "/selectlist_customer", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectReviewListCustomer(
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 회원 id 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            if (mid != null) {
                Page<ReviewEntity> rPage = reviewService.selectReviewListCustomer(page - 1, size, mid);
                map.put("review", rPage.getContent());
                map.put("total", rPage.getTotalElements());
                map.put("pages", rPage.getTotalPages());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 짧은리뷰 리스트
    // type 1 : 최신순, 2: 높은평점순, 3: 낮은평점순
    // http://127.0.0.1:9090/ROOT/api/review/selectlist
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectReviewListAdmin(@RequestParam(name = "mcode") Long mcode,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "type", defaultValue = "1", required = false) Long type) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 기본값 : 최신순
            if (type == 1) {
                Page<ReviewEntity> rPage = reviewService.selectReviewList(page - 1, size, mcode);
                map.put("review", rPage.getContent());
                map.put("total", rPage.getTotalElements());
                map.put("pages", rPage.getTotalPages());
                map.put("status", 200);
            }
            // 높은 평점순
            else if (type == 2) {
                Page<ReviewEntity> rPage = reviewService.selectReviewListOfGpaDesc(page - 1, size, mcode);
                map.put("review", rPage.getContent());
                map.put("total", rPage.getTotalElements());
                map.put("pages", rPage.getTotalPages());
                map.put("status", 200);
            }
            // 낮은 평점순
            else if (type == 3) {
                Page<ReviewEntity> rPage = reviewService.selectReviewListOfGpaAsc(page - 1, size, mcode);
                map.put("review", rPage.getContent());
                map.put("total", rPage.getTotalElements());
                map.put("pages", rPage.getTotalPages());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
