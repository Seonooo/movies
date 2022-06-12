package com.example.serviceImpl;

import java.util.Date;
import java.util.List;

import com.example.entity.RecordEntity;
import com.example.entity.ScheduleView;
import com.example.entity.TicketEntity;
import com.example.repository.MemberRepository;
import com.example.repository.MovieRepository;
import com.example.repository.RecordRepository;
import com.example.repository.ScheduleRepository;
import com.example.repository.ScheduleViewRepository;
import com.example.repository.TheaterRepository;
import com.example.repository.TicketRepository;
import com.example.repository.TicketStateRepository;
import com.example.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    ScheduleViewRepository scheduleViewRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketStateRepository ticketStateRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RecordRepository recordRepository;

    // 예매하기
    @Override
    public int insertTicket(TicketEntity ticketEntity, Long mcode, Long thcode, String mid) {
        try {
            // 멤버 영화 상영관 상태( 기본값 : 1) 설정
            ticketEntity.setMemberEntity(memberRepository.findById(mid).orElse(null));
            ticketEntity.setMovieEntity(movieRepository.findById(mcode).orElse(null));
            ticketEntity.setTheaterEntity(theaterRepository.findById(thcode).orElse(null));
            ticketEntity.setTicketStateEntity(ticketStateRepository.findById(1L).orElse(null));
            ticketRepository.save(ticketEntity);

            // 기록
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(1L).orElse(null));
            recordRepository.save(recordEntity);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 결제 대기 1 - 관리자 권한으로 수정가능
    @Override
    public int stayTicket(TicketEntity ticketEntity) {
        try {
            TicketEntity tEntity = ticketRepository.findById(ticketEntity.getTno()).orElse(null);
            tEntity.setTicketStateEntity(ticketStateRepository.findById(1L).orElse(null));
            ticketRepository.save(tEntity);

            // 기록 - 누적
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(1L).orElse(null));
            recordRepository.save(recordEntity);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 티켓결제완료 2) 티켓결제완료시, 스케쥴 등록후 취소하면 다시 결제완료로 돌아옴
    @Override
    public int buyTicket(TicketEntity ticketEntity) {
        try {
            TicketEntity tEntity = ticketRepository.findById(ticketEntity.getTno()).orElse(null);
            // 티켓 결제완료시 - 결제완료로 상태변경
            tEntity.setTicketStateEntity(ticketStateRepository.findById(2L).orElse(null));
            ticketRepository.save(tEntity);
            // 기록 - 누적
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(2L).orElse(null));
            recordRepository.save(recordEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 티켓사용완료 3) - 스케쥴 등록완료 => 사용완료로 자동업데이트되게 수정
    @Override
    public int useTicket(TicketEntity ticketEntity) {
        try {
            TicketEntity tEntity = ticketRepository.findById(ticketEntity.getTno()).orElse(null);
            tEntity.setTicketStateEntity(ticketStateRepository.findById(3L).orElse(null));
            ticketRepository.save(tEntity);

            // 기록 - 누적
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(3L).orElse(null));
            recordRepository.save(recordEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 예매취소 0 =
    @Override
    public int deleteTicket(TicketEntity ticketEntity) {
        try {
            TicketEntity tEntity = ticketRepository.findById(ticketEntity.getTno()).orElse(null);
            tEntity.setTicketStateEntity(ticketStateRepository.findById(0L).orElse(null));
            ticketRepository.save(tEntity);

            // 기록 - 누적
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(0L).orElse(null));
            recordRepository.save(recordEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 기간만료 4) - 스케쥴 등록안하고 기간이 지난경우
    @Override
    public int endTicket(TicketEntity ticketEntity) {
        try {
            TicketEntity tEntity = ticketRepository.findById(ticketEntity.getTno()).orElse(null);
            tEntity.setTicketStateEntity(ticketStateRepository.findById(4L).orElse(null));
            ticketRepository.save(tEntity);

            // 기록 - 누적
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setTicketEntity(ticketEntity);
            recordEntity.setTicketStateEntity(ticketStateRepository.findById(4L).orElse(null));
            recordRepository.save(recordEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 예매상태별 티켓 리스트 - 고객
    @Override
    public Page<TicketEntity> selectListTicketStateCustomer(String mid, int size, int page, Long tscode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findByTicketStateEntity_TscodeAndMemberEntity_Mid(tscode, mid, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 티켓 1개 조회 - 고객
    @Override
    public TicketEntity selectTicketCustomer(String mid, Long tno) {
        try {
            return ticketRepository.findByTnoAndMemberEntity_Mid(tno, mid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 티켓리스트 - 고객, 관리자
    @Override
    public Page<TicketEntity> selectListTicketCustomer(String mid, int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findByMemberEntity_Mid(mid, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 티켓 1개 조회 - 관리자
    @Override
    public TicketEntity selectTicket(Long tno) {
        try {
            return ticketRepository.findById(tno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 티켓리트스 - 관리자
    @Override
    public Page<TicketEntity> selectListTicketAdmin(int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 예매상태별 티켓 리스트 - 관리자
    @Override
    public Page<TicketEntity> selectListTicketStateAdmin(int size, int page, Long tscode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findByTicketStateEntity_Tscode(tscode, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 상영관별 티켓 리스트 - 관리자
    @Override
    public Page<TicketEntity> selectListTicketTheaterAdmin(int size, int page, Long thcode) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findByTheaterEntity_Thcode(thcode, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 영화별 티켓 리스트 - 관리자
    @Override
    public Page<TicketEntity> selectListTicketMovieAdmin(int size, int page, String mtitle) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("tno").descending());
            return ticketRepository.findByMovieEntity_Mtitle(mtitle, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 티켓 1개 조회 - 관리자
    @Override
    public TicketEntity selectTicketAdmin(Long tno) {
        try {
            return ticketRepository.findById(tno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 기록 조회 - 관리자
    @Override
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid, String mtitle) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return recordRepository.findByTicketEntity_MovieEntity_MtitleAndTicketEntity_MemberEntity_Mid(mtitle, mid,
                    pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insertVisitorTicket(TicketEntity ticket) {
        try {
            ticketRepository.save(ticket);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public TicketEntity findLastTicket() {
        try {
            return ticketRepository.findFirstByOrderByTnoDesc();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return recordRepository.findByTicketEntity_MemberEntity_Mid(mid, pageable);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return recordRepository.findAll(pageable);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ScheduleView> selectSchedulesAfterToday(Long thcode, Date start, Date end) {
        try {

            return scheduleViewRepository.findByThcodeAndTdateBetween(thcode, start, end);

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Page<RecordEntity> selectTicketRecordAdmin(int size, int page, String mid, Long mcode) {

        return null;
    }

    @Override
    public List<TicketEntity> selectListTicketState(Long tscode) {
        try {
            return ticketRepository.findByTicketStateEntity_Tscode(tscode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
