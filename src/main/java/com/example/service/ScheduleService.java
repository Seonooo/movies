package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.entity.ScheduleEntity;
import com.example.entity.ScheduleView;

import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {

    // 스케쥴 등록
    public int insertSchedule(Long tno);

    // 스케쥴 삭제
    public int deleteSchedule(ScheduleEntity scheduleEntity);

    // 스케쥴 리스트
    public List<ScheduleEntity> selectSchedules();

    // 스케쥴 한개 찾기
    public ScheduleEntity selectSchedule(Long sno);

    // 오늘이후 스케줄 리스트
    public List<ScheduleEntity> selectTodayAfterSchedules(Date date);

    // 티켓번호로 스케쥴 한개 찾기
    public ScheduleEntity selectScheduleOfTno(Long tno);

    // 상영 스케줄
    public List<ScheduleView> selectSchedulesList(Long thcode);

}
