package com.example.scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.entity.TicketEntity;

import com.example.repository.TicketRepository;

import com.example.service.MovieService;
import com.example.service.ScheduleService;
import com.example.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

@Component
public class TicketSchedule {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService tService;

    @Autowired
    MovieService mService;

    @Autowired
    ScheduleService sService;

    // 실시간 기간만료, 상영완료
    @Scheduled(cron = "* 0 * * * *")
    public void Ticket() {
        try {
            List<TicketEntity> list = tService.selectListTicketState(2L);
            List<TicketEntity> cancleList = tService.selectListTicketState(1L);
            Date now = new Date();
            now.getTime();
            for (TicketEntity item : list) {
                if (item.getTend().before(now)) {
                    tService.useTicket(item);
                }
            }
            Calendar cal = Calendar.getInstance();
            for (TicketEntity item : cancleList) {
                cal.setTime(item.getTregdate());
                cal.add(Calendar.MINUTE, 5);
                if (cal.getTime().before(now)) {
                    sService.deleteSchedule(sService.selectScheduleOfTno(item.getTno()));
                    tService.deleteTicket(item);
                }
            }
            System.out.println(now);
            System.out.println("티켓 업데이트 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
