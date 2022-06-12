package com.example.restcontroller;

import java.time.LocalDate;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.RecordEntity;
import com.example.entity.TicketEntity;
import com.example.entity.TicketStateEntity;
import com.example.entity.VisitorEntity;
import com.example.jwt.jwtUtil;
import com.example.service.RecordService;
import com.example.service.TicketService;
import com.example.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/vistor")
public class VisitorRestController {

    @Autowired
    VisitorService vService;

    @Autowired
    TicketService tService;

    @Autowired
    RecordService rService;

    @Autowired
    jwtUtil jwt;

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/vistor/sample
    @RequestMapping(value = "/sample", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> sampleGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }


    // 결제이후 예매 상태변경
    // http://127.0.0.1:9090/ROOT/api/vistor/visitorupdate
    @RequestMapping(value = "/visitorupdate", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> visitorupdatePUT(
            @RequestParam(name = "tno") Long tno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            TicketEntity ticket = tService.selectTicket(tno);
            TicketStateEntity state = new TicketStateEntity();

            state.setTscode(2L);
            ticket.setTicketStateEntity(state);

            System.out.println(ticket.toString());
            int ret = tService.insertVisitorTicket(ticket);
            System.out.println(ret);
            if (ret == 1) {
                RecordEntity record = new RecordEntity();
                record.setTicketEntity(ticket);
                record.setTicketStateEntity(ticket.getTicketStateEntity());

                int ret2 = rService.insertRecord(record);
                if (ret2 == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 비회원 예매
    // 영화 번호 , 상영관 번호
    // {"movieEntity":{"mcode":183862},"theaterEntity":{"thcode":3}}
    // http://127.0.0.1:9090/ROOT/api/vistor/visitorticket
    @RequestMapping(value = "/visitorticket", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> visitorticketGET(
            @RequestHeader(name = "VISITOR") String vtoken,
            @RequestBody TicketEntity ticket) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String vphone = jwt.extractUsername(vtoken);

            VisitorEntity visitor = vService.selectone(vphone);
            TicketStateEntity ticketstate = new TicketStateEntity();
            MemberEntity member = new MemberEntity();

            member.setMid("visitor");
            ticketstate.setTscode(1L);

            ticket.setTvisitor(visitor.getVphone());
            ticket.setTvisitorpw(visitor.getVpw());
            ticket.setTicketStateEntity(ticketstate);
            ticket.setMemberEntity(member);

            int ret = tService.insertVisitorTicket(ticket);
            if (ret == 1) {
                ticket = tService.findLastTicket();
                RecordEntity record = new RecordEntity();
                record.setTicketEntity(ticket);
                record.setTicketStateEntity(ticket.getTicketStateEntity());

                int ret2 = rService.insertRecord(record);
                if (ret2 == 1) {
                    map.put("status", 200);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }


    // 이전 방문자 삭제
    // http://127.0.0.1:9090/ROOT/api/vistor/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> visitroDELETE() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            LocalDate date = LocalDate.now();
            List<VisitorEntity> list = vService.selectVisitorListBeforeToday(date);
            System.out.println(list.toString());
            int ret = vService.deleteVisitor(list);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 검색 + 페이지네이션
    // http://127.0.0.1:9090/ROOT/api/vistor/select
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGET(
            @RequestParam(name = "vphone", defaultValue = "") String vphone,
            @RequestParam(name = "page", defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            LocalDate date = LocalDate.now();
            PageRequest pageable = PageRequest.of(page - 1, 5);
            List<VisitorEntity> list = vService.selectVisitorList(vphone, date, pageable);
            System.out.println(vphone);
            System.out.println(date.toString());
            System.out.println(page);
            map.put("list", list);

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 비회원
    // http://127.0.0.1:9090/ROOT/api/vistor/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestBody VisitorEntity visitor) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            visitor.setVpw(bcpe.encode(visitor.getVpw()));
            System.out.println(visitor.toString());
            LocalDate date = LocalDate.now();
            visitor.setTregdate(date);
            int ret = vService.insertVisitor(visitor);
            if (ret == 1) {
                String vphone = jwt.generatorvisitorToken(visitor.getVphone());
                map.put("status", 200);
                map.put("visitor", vphone);


            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }
}
