package com.example.restcontroller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ScheduleView;
import com.example.repository.CategoryRepository;
import com.example.repository.FilmRatingRepository;
import com.example.repository.NationRepository;
import com.example.repository.ScheduleRepository;
import com.example.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/select")
public class ListRestController {

    // 국가
    @Autowired
    NationRepository nationRepository;

    // 장르
    @Autowired
    CategoryRepository categoryRepository;

    // 등급
    @Autowired
    FilmRatingRepository filmRatingRepository;

    // 스케쥴
    @Autowired
    ScheduleRepository scheduleRepository;

    // 티켓
    @Autowired
    TicketService ticketService;

    // 국가 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/select/nation
    @RequestMapping(value = "/nation", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> nationList() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            map.put("list", nationRepository.findAll());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 장르 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/select/genre
    @RequestMapping(value = "/genre", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> genreList() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            map.put("list", categoryRepository.findAll());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 등급 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/select/rate
    @RequestMapping(value = "/rate", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> rateList() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            map.put("list", filmRatingRepository.findAll());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 스케쥴 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/select/schedule
    @RequestMapping(value = "/schedule", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> scheduleList() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            map.put("schedules", scheduleRepository.findAll());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 스케쥴 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/select/scheduletoday
    @RequestMapping(value = "/scheduletoday", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> scheduletoday(
            @RequestParam(name = "thcode") Long thcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            Date date = new Date();
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String day = simpleFormatter.format(date);
            Date today = simpleFormatter.parse(day);

            Date start = new Date();
            Date end = new Date();

            start.setDate(today.getDate() - 10);
            end.setDate(today.getDate() + 10);

            System.out.println(thcode);
            List<ScheduleView> schedule = ticketService.selectSchedulesAfterToday(thcode, start, end);

            map.put("schedule", schedule);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
