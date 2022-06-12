package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.TheaterEntity;
import com.example.jwt.jwtUtil;
import com.example.service.TheaterService;

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
@RequestMapping(value = "/api/theater")
public class TheaterRestController {

    @Autowired
    TheaterService theaterService;

    @Autowired
    jwtUtil jwt;

    // 상영관 등록하기
    // http://127.0.0.1:9090/ROOT/api/theater/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertTheater(@RequestBody TheaterEntity theaterEntity,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // admin 자격 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                // 영화관 등록
                int ret = theaterService.insertTheater(theaterEntity);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return map;
    }

    // 상영관 변경하기
    // http://127.0.0.1:9090/ROOT/api/theater/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateTheater(@RequestBody TheaterEntity theaterEntity,
            @RequestParam(name = "type") Long type,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            int ret = 0;
            // admin 자격 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                // 1번 타입 : 상영관 내용 업데이트
                if (type == 1) {
                    ret = theaterService.updateTheater(theaterEntity);
                }
                // 2번 타입 : 상영가능여부 불가로 변경 ( 삭제 느낌 )
                else if (type == 2 || type == 3) {
                    ret = theaterService.updateTheaterState(theaterEntity.getThcode(), type);
                }
            }
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 상영관 1개조회
    // http://127.0.0.1:9090/ROOT/api/theater/select
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectTheater(
            @RequestParam(name = "thcode") Long thcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            TheaterEntity theaterEntity = theaterService.selectTheater(thcode);
            if (theaterEntity != null) {
                map.put("theater", theaterEntity);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 상영관 리스트
    // http://127.0.0.1:9090/ROOT/api/theater/selects
    @RequestMapping(value = "/selects", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectTheaters(
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            Page<TheaterEntity> tPage = theaterService.selectListTheater(page - 1, size);
            map.put("theaters", tPage.getContent());
            map.put("total", tPage.getTotalElements());
            map.put("pages", tPage.getTotalPages());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 상영관 리스트
    // http://127.0.0.1:9090/ROOT/api/theater/theaterschedule
    @RequestMapping(value = "/theaterschedule", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> theaterschedule() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
