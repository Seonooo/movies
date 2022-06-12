package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.entity.RecordEntity;
import com.example.entity.ScheduleView;
import com.example.entity.TicketEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    // 예매하기 0
    public int insertTicket(TicketEntity ticketEntity, Long mcode, Long thcode, String mid);

    public TicketEntity findLastTicket();

    // 결제대기 1
    public int stayTicket(TicketEntity ticketEntity);

    // 비회원 예매하기
    public int insertVisitorTicket(TicketEntity ticket);

    // 결제완료 2

    public int buyTicket(TicketEntity ticketEntity);

    // 사용완료 3
    public int useTicket(TicketEntity ticketEntity);

    // 예매취소 0
    public int deleteTicket(TicketEntity ticketEntity);

    // 기간만료 4
    public int endTicket(TicketEntity ticketEntity);

    // 티켓 1개
    public TicketEntity selectTicket(Long tno);

    // 예매상태별 리스트 - 고객
    public Page<TicketEntity> selectListTicketStateCustomer(String mid, int size, int page, Long tscode);

    // 티켓 1개 가져오기 - 고객
    public TicketEntity selectTicketCustomer(String mid, Long tno);

    // 티켓 리스트 - 고객, 관리자
    public Page<TicketEntity> selectListTicketCustomer(String mid, int size, int page);

    // 티켓 리스트 - 관리자
    public Page<TicketEntity> selectListTicketAdmin(int size, int page);

    // 예매상태별 리스트 - 관리자
    public Page<TicketEntity> selectListTicketStateAdmin(int size, int page, Long tscode);

    // 예매상태별 리스트 - 스케줄
    public List<TicketEntity> selectListTicketState(Long tscode);

    // 상영관별 리스트 - 관리자
    public Page<TicketEntity> selectListTicketTheaterAdmin(int size, int page, Long thcode);

    // 영화별 리스트 - 관리자
    public Page<TicketEntity> selectListTicketMovieAdmin(int size, int page, String mtitle);

    // 티켓 1개 가져오기 - 관리자
    public TicketEntity selectTicketAdmin(Long tno);

    // 기록 리스트 - 관리자
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid, Long mcode);

    // 기록 리스트 (아이디, 영화코드)- 관리자
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid, String mtitle);

    // 기록 리스트 (아이디) - 관리자
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid);

    // 기록 전체 리스트 - 관리자
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page);

    // 상영 스케줄(오늘날짜기준)
    public List<ScheduleView> selectSchedulesAfterToday(Long thcode, Date start, Date end);

}
