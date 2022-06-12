package com.example.serviceImpl;

import java.util.Date;
import java.util.List;

import com.example.entity.ScheduleEntity;
import com.example.entity.ScheduleView;
import com.example.repository.MovieRepository;
import com.example.repository.ScheduleRepository;
import com.example.repository.ScheduleViewRepository;
import com.example.repository.TheaterRepository;
import com.example.repository.TicketRepository;
import com.example.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleViewRepository sRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    TicketRepository ticketRepository;

    // 스케쥴 등록
    @Override
    public int insertSchedule(Long tno) {
        try {
            ScheduleEntity scheduleEntity = new ScheduleEntity();
            scheduleEntity.setTicketEntity(ticketRepository.findById(tno).orElse(null));
            scheduleRepository.save(scheduleEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 스케쥴 삭제
    @Override
    public int deleteSchedule(ScheduleEntity scheduleEntity) {
        try {
            scheduleRepository.delete(scheduleEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 스케쥴 리스트
    @Override
    public List<ScheduleEntity> selectSchedules() {
        try {
            return scheduleRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 스케쥴 한개 찾기
    @Override
    public ScheduleEntity selectSchedule(Long sno) {
        try {
            return scheduleRepository.findById(sno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 오늘 이후 스케줄
    @Override
    public List<ScheduleEntity> selectTodayAfterSchedules(Date date) {

        return null;
    }

    // 티켓번호로 스케쥴 찾기
    @Override
    public ScheduleEntity selectScheduleOfTno(Long tno) {
        try {
            return scheduleRepository.findByTicketEntity_Tno(tno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ScheduleView> selectSchedulesList(Long thcode) {
        try {
            return sRepository.findByThcode(thcode);
        } catch (Exception e) {
            return null;
        }

    }

}
