package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.RecordEntity;
import com.example.entity.ScheduleEntity;
import com.example.entity.TicketEntity;
import com.example.jwt.jwtUtil;
import com.example.service.ScheduleService;
import com.example.service.TicketService;

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
@RequestMapping(value = "/api/ticket")
public class TicketRestController {

    @Autowired
    TicketService ticketService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    jwtUtil jwt;

    // 티켓 예매하기
    // http://127.0.0.1:9090/ROOT/api/ticket/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertTicket(
            @RequestBody TicketEntity ticketEntity,
            @RequestParam(name = "mcode") Long mcode,
            @RequestParam(name = "thcode") Long thcode,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(ticketEntity.toString());
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            if (!token.isEmpty()) {
                int ret = ticketService.insertTicket(ticketEntity, mcode, thcode, mid);
                if (ret == 1) {
                    ret += scheduleService.insertSchedule(ticketEntity.getTno());
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

    // 티켓 예매취소 - 결제대기에서 취소 or 결제완료에서 환불시 => 결제api필요
    @RequestMapping(value = "/delete", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> delelteTicket(
            @RequestParam(name = "tno") Long tno,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            ScheduleEntity scheduleEntity = scheduleService.selectScheduleOfTno(tno);
            TicketEntity ticketEntity = scheduleEntity.getTicketEntity();
            if (ticketEntity.getMemberEntity().getMid().equals(mid)) {
                int ret = scheduleService.deleteSchedule(scheduleEntity);
                if (ret == 1) {
                    ret += ticketService.deleteTicket(ticketEntity);
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

    // 티켓 결제 - 결제api로 완료조건 추가 필요
    // http://127.0.0.1:9090/ROOT/api/ticket/buy_ticket
    @RequestMapping(value = "/buy_ticket", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> buyTicket(
            @RequestParam(name = "tno") Long tno,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            TicketEntity ticket = ticketService.selectTicket(tno);
            if (ticket.getMemberEntity().getMid().equals(mid)) {
                int ret = ticketService.buyTicket(ticket);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 티켓리스트 가져오기 (관리자)
    // 0 : 전체, 1 : 예매 상태, 2 : 상영관, 3 : 영화, 4: 회원별
    // http://127.0.0.1:9090/ROOT/api/ticket/get_admin_ticket
    @RequestMapping(value = "/get_admin_ticket", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> getTicketListAdmin(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "tno", required = false) Long tno,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "type", defaultValue = "0", required = false) Long type,
            @RequestParam(name = "mcode", required = false) String mtitle,
            @RequestParam(name = "tscode", required = false) Long tscode,
            @RequestParam(name = "thcode", required = false) Long thcode,
            @RequestParam(name = "mid", required = false) String mid) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 관리자 권한 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                // 티켓 한개가져오기
                if (tno != null) {
                    map.put("ticket", ticketService.selectTicketAdmin(tno));
                    map.put("status", 200);
                }
                // 전체 리스트
                else if (type == 0L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketAdmin(size, page - 1);
                    System.out.println(tPage.getContent().get(0).getTdate());
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
                // 예매 상태별 리스트
                else if (type == 1L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketStateAdmin(size, page - 1, tscode);
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }

                // 상영관별 리스트
                else if (type == 2L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketTheaterAdmin(size, page - 1, thcode);
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
                // 영화별 리스트
                else if (type == 3L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketMovieAdmin(size, page - 1, mtitle);
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
                // 회원별 리스트
                else if (type == 4L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketCustomer(mid, size, page - 1);
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 티켓리스트 가져오기 (멤버)
    // 0 : 전체, 1 : 예매 상태
    // http://127.0.0.1:9090/ROOT/api/ticket/get_customer_ticket
    @RequestMapping(value = "/get_customer_ticket", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> getTicketListCustomer(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "type", defaultValue = "0", required = false) Long type,
            @RequestParam(name = "tscode", required = false) Long tscode,
            @RequestParam(name = "tno", required = false) Long tno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 토큰으로 멤버 아이디 확인
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");

            if (!token.isEmpty()) {
                // 티켓 한개 가져오기
                if (tno != null) {
                    map.put("ticket", ticketService.selectTicketCustomer(mid, tno));
                    map.put("status", 200);
                }
                // 전체 리스트
                else if (type == 0L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketCustomer(mid, size, page - 1);
                    System.out.println(tPage.getContent().get(0).getTdate());
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
                // 예매 상태별 리스트
                else if (type == 1L) {
                    Page<TicketEntity> tPage = ticketService.selectListTicketStateCustomer(mid, size, page - 1, tscode);
                    map.put("tickets", tPage.getContent());
                    map.put("pages", tPage.getTotalPages());
                    map.put("total", tPage.getTotalElements());
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 기록조회하기
    // http://127.0.0.1:9090/ROOT/api/ticket/select_record
    @RequestMapping(value = "/select_record", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectRecordList(
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "mid", required = false) String mid,
            @RequestParam(name = "mtitle", required = false) String mtitle,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String role = jsonObject.getString("role");
            // 관리자확인
            if (role.equals("ADMIN") || role.equals("admin")) {
                System.out.println("mid======" + mid + ":=========");
                System.out.println("mtitle" + mtitle);
                // 전체리스트
                if (mtitle.isEmpty() && mid.isEmpty()) {
                    Page<RecordEntity> records = ticketService.selectTicketRecordAdmin(size, page - 1);
                    map.put("records", records.getContent());
                    map.put("total", records.getTotalElements());
                    map.put("pages", records.getTotalPages());
                    map.put("status", 200);
                }
                // 회원 기록 리스트
                else if (!mid.isEmpty() && mtitle.isEmpty()) {
                    Page<RecordEntity> records = ticketService.selectTicketRecordAdmin(size, page - 1, mid);
                    map.put("records", records.getContent());
                    map.put("total", records.getTotalElements());
                    map.put("pages", records.getTotalPages());
                    map.put("status", 200);
                }
                // 회원 + 영화 기록 리스트
                else {
                    Page<RecordEntity> records = ticketService.selectTicketRecordAdmin(size, page - 1, mid, mtitle);
                    map.put("records", records.getContent());
                    map.put("total", records.getTotalElements());
                    map.put("pages", records.getTotalPages());
                    map.put("status", 200);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return map;
    }

}
