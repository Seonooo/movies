package com.example.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.entity.CategoryEntity;
import com.example.entity.FilmRatingEntity;
import com.example.entity.MovieCategoryEntity;
import com.example.entity.MovieEntity;
import com.example.entity.NationEntity;
import com.example.entity.PosterEntity;
import com.example.repository.CategoryRepository;
import com.example.repository.FilmRatingRepository;
import com.example.repository.GpaRepository;
import com.example.repository.MovieCategoryRepository;
import com.example.repository.MovieRepository;
import com.example.repository.MovieStateRepository;
import com.example.repository.NationRepository;
import com.example.repository.PosterRepository;
import com.example.repository.TicketRepository;
import com.example.service.MovieService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieServiceImpl implements MovieService {

    // 영화
    @Autowired
    MovieRepository movieRepository;

    // 포스터
    @Autowired
    PosterRepository posterRepository;

    // 관람등급
    @Autowired
    FilmRatingRepository filmRatingRepository;

    // 국가
    @Autowired
    NationRepository nationRepository;

    // 장르
    @Autowired
    CategoryRepository categoryRepository;

    // 영화 - 장르
    @Autowired
    MovieCategoryRepository movieCategoryRepository;

    // 영화 - 상태
    @Autowired
    MovieStateRepository movieStateRepository;

    // 평점
    @Autowired
    GpaRepository gpaRepository;

    // 티켓
    @Autowired
    TicketRepository ticketRepository;

    // 크롤링으로 전체추가
    @Override
    public int insertMovies(String naverRankUrl) {
        try {
            // 사이트 전체소스를 jsoup로 naverDoc에 가져옴
            Document naverDoc = Jsoup.connect(naverRankUrl).get();

            // naverRankList에 naverDoc중에서 dt태그의 클래스명이 tit3인것에서 a태그만 가져오기
            Elements naverRankList = naverDoc.select("dt.tit a");

            // 평점리스트
            List<Float> gapList = new ArrayList<>();

            // for문을 통해서 네이버영화 코드를 가져옴
            for (int i = 0; i < 10; i++) {

                // 영화Entity 생성
                MovieEntity mEntity = new MovieEntity();

                // 포스터Entity 생성 -> 여기서는 주소만
                PosterEntity posterEntity = new PosterEntity();

                String naverHref = naverRankList.get(i).attr("href");
                String naverCode = naverHref.substring(naverHref.lastIndexOf("=") + 1);
                // 영화코드
                Long movieCode = Long.parseLong(naverCode);
                mEntity.setMcode(movieCode);

                System.out.println("영화코드 : " + naverCode);

                // 가져온 코드를 통해서 영화한개의 정보가 들어있는 페이지가져오기
                String naverRankUrlOne = "https://movie.naver.com/movie/bi/mi/basic.naver?code=" + naverCode;
                Document naverDocOne = Jsoup.connect(naverRankUrlOne).get();

                // 포스터
                // Elements
                Element moviePoster = naverDocOne.select("div.poster img").last();
                String moviePosterUrl = moviePoster.absUrl("src");
                System.out.println("포스터주소 : " + moviePosterUrl);
                posterEntity.setPimageUrl(moviePosterUrl);

                // 영화제목
                Elements movieTitle = naverDocOne.select("h3.h_movie a");
                System.out.println("제목 : " + movieTitle.first().text());
                mEntity.setMtitle(movieTitle.first().text());

                // 기자 평론가 평점
                Elements movieGpa = naverDocOne.select("div.star_score em");
                String gapsplit[] = movieGpa.text().substring(8, 15).split(" ");
                String gap = "";
                for (String gap2 : gapsplit) {
                    gap += gap2;
                }
                Float l = Float.parseFloat(gap);
                gapList.add(l);
                System.out.println("평론가 평점 : " + gapList.get(i));
                mEntity.setMgpa(gapList.get(i));

                // 배우 3명
                String movieActorUrl = "https://movie.naver.com/movie/bi/mi/detail.naver?code=" + naverCode;
                Document naverActorDoc = Jsoup.connect(movieActorUrl).get();
                Elements movieActor = naverActorDoc.select("a.k_name");
                String movieActorsList = new String();
                for (int o = 0; o < 3; o++) {
                    String movieActors = movieActor.get(o).text();
                    if (o < 2) {
                        movieActorsList += movieActors + ",";
                    } else {
                        movieActorsList += movieActors;
                    }

                }
                System.out.println("배우3명 : " + movieActorsList.toString());
                mEntity.setMactor(movieActorsList.toString());

                // 감독 1명
                String movieDirectorUrl = "https://movie.naver.com/movie/bi/mi/detail.naver?code=" + naverCode;
                Document movieDirectorDoc = Jsoup.connect(movieDirectorUrl).get();
                Elements movieDiretor = movieDirectorDoc.select("div.dir_product a");
                String movieDirector = movieDiretor.first().text();
                System.out.println("감독 1명 : " + movieDirector);
                mEntity.setMdirector(movieDirector);

                // 랭킹
                int rank = i + 1;
                System.out.println("순위 : " + rank);
                mEntity.setMrank(Long.valueOf(rank));

                // 상영시간
                Elements movieTime = naverDocOne.select("dl.info_spec span");
                int movieTimeLength = movieTime.text().indexOf("분");
                String movieTimes = movieTime.text().substring(movieTimeLength - 3,
                        movieTimeLength);

                System.out.println("상영시간 : " + movieTimes);
                mEntity.setMtime(movieTimes);

                // 개봉일, 마감일
                Elements movieRelease = naverDocOne.select("dl.info_spec span");
                int movieReleaseLength = movieRelease.text().indexOf("개봉");
                String movieOneRelease = movieRelease.text().substring(movieReleaseLength - 12,
                        movieReleaseLength);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy .MM.dd ", Locale.KOREA);
                Calendar cal = Calendar.getInstance();

                // 개봉일
                cal.setTime(formatter.parse(movieOneRelease));
                mEntity.setMrelease(cal.getTime());
                System.out.println("개봉일 : =========>" + cal.getTime().toString());

                // 상영상태 : 기본값 상영중
                mEntity.setMovieStateEntity(movieStateRepository.findById(1L).orElse(null));

                // 마감일
                cal.add(Calendar.MONTH, 2);
                mEntity.setMdeadline(cal.getTime());
                System.out.println("마감일 : =========>" + cal.getTime().toString());

                // 짧은 줄거리
                Elements movieShot = naverDocOne.select("h5.h_tx_story ");
                System.out.println("짧은 줄거리 : " + movieShot.text());
                mEntity.setMshot(movieShot.text());

                // 긴 줄거리
                Elements movieLong = naverDocOne.select("p.con_tx");
                System.out.println("긴줄거리 : " + movieLong.first().text());
                mEntity.setMlong(movieLong.text());

                // 등급코드
                String age = naverDocOne.select("p.info_spec span:nth-child(5) a").attr("href");
                String ageCode = age.substring(age.lastIndexOf("=") + 1);
                System.out.println(ageCode);
                FilmRatingEntity filmRating = filmRatingRepository.findById(Long.parseLong(ageCode)).orElse(null);
                mEntity.setFilmratingEntity(filmRating);

                // 국가코드
                String nation = naverDocOne.select("p.info_spec span:nth-child(2) a").attr("href");
                String nationCode = nation.substring(nation.lastIndexOf("=") + 1);
                System.out.println(nationCode);
                NationEntity nationEntity = nationRepository.findById(nationCode).orElse(null);
                mEntity.setNationEntity(nationEntity);

                List<Long> genreList = new ArrayList<>();
                // 장르코드 -> 최대 3개가져오기
                // 장르코드 url
                for (int d = 0; d < 3; d++) {
                    String temp = naverDocOne.select("p.info_spec span:nth-child(1) a:nth-child(" + (d + 1) + ")")
                            .attr("href");
                    String tempCode = temp.substring(temp.lastIndexOf("=") + 1);
                    System.out.println("tempCode ====>" + tempCode + "<======");
                    if (!tempCode.equals("")) {
                        genreList.add(Long.parseLong(tempCode));
                        System.out.println(Long.parseLong(tempCode));
                    }

                }

                // PosterEntity에 movieEntity Join
                posterEntity.setMovieEntity(mEntity);

                // poster, movie, movieGenre save
                movieRepository.save(mEntity);
                posterRepository.save(posterEntity);

                System.out.println(genreList.size());
                for (int g = 0; g < genreList.size(); g++) {
                    MovieCategoryEntity mcEntity = new MovieCategoryEntity();
                    CategoryEntity category = new CategoryEntity();
                    System.out.println(genreList.get(g));
                    category.setCcode(genreList.get(g));
                    mcEntity.setCategoryEntity(category);
                    mcEntity.setMovieEntity(mEntity);
                    movieCategoryRepository.save(mcEntity);
                    System.out.println(mcEntity);
                }

                System.out.println("----------------------------------");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 영화1개추가
    @Override
    public int insertMovie(MovieEntity movie, String nation, Long filmRating, Long mscode, Long ccode) {
        try {
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.setMcode(movie.getMcode());
            movieEntity.setMactor(movie.getMactor());
            movieEntity.setMdirector(movie.getMdirector());
            movieEntity.setMgpa(movie.getMgpa());
            movieEntity.setMtime(movie.getMtime());
            movieEntity.setMtitle(movie.getMtitle());
            movieEntity.setMshot(movie.getMshot());
            movieEntity.setMlong(movie.getMlong());
            Calendar cal = Calendar.getInstance();
            cal.setTime(movie.getMrelease());
            cal.add(Calendar.HOUR_OF_DAY, -9);
            movieEntity.setMrelease(cal.getTime());

            // 마감일 : 개봉일 +2달
            cal.add(Calendar.MONTH, 2);
            movieEntity.setMdeadline(cal.getTime());

            movieEntity.setMrank(movie.getMrank());

            movieEntity.setMovieStateEntity(movieStateRepository.findById(mscode).orElse(null));
            movieEntity.setNationEntity(nationRepository.findById(nation).orElse(null));
            movieEntity.setFilmratingEntity(filmRatingRepository.findById(filmRating).orElse(null));
            movieRepository.save(movieEntity);
            MovieCategoryEntity movieCategoryEntity = new MovieCategoryEntity();
            movieCategoryEntity.setMovieEntity(movieEntity);
            movieCategoryEntity.setCategoryEntity(categoryRepository.findById(ccode).orElse(null));
            movieCategoryRepository.save(movieCategoryEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 영화 전체삭제
    @Override
    public int deleteMovies() {
        try {
            gpaRepository.deleteAll();
            ticketRepository.deleteAll();
            posterRepository.deleteAll();
            movieCategoryRepository.deleteAll();
            movieRepository.deleteAll();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 영화 1개 수정
    @Override
    public int updateMovie(MovieEntity movie) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(movie.getMdeadline());
            cal.add(Calendar.HOUR_OF_DAY, -9);
            MovieEntity movieEntity = movieRepository.findById(movie.getMcode()).orElse(null);
            movieEntity.setMdeadline(cal.getTime());
            movieRepository.save(movieEntity);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    // 영화 1개 삭제
    @Override
    public int deleteMovie(Long mcode) {
        try {
            List<PosterEntity> posters = posterRepository.findByMovieEntity_Mcode(mcode);
            for (PosterEntity post : posters) {
                posterRepository.delete(post);
            }
            List<MovieCategoryEntity> mcs = movieCategoryRepository.findByMovieEntity_Mcode(mcode);
            for (MovieCategoryEntity mc : mcs) {
                movieCategoryRepository.delete(mc);
            }
            movieRepository.deleteById(mcode);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 영화 1개찾기
    @Override
    public MovieEntity selectMovie(Long code) {
        try {
            MovieEntity movieEntity = movieRepository.findById(code).orElse(null);
            // vue에서 h2db와의 시간이 다르게 오는것을 바꿔줌
            Calendar cal = Calendar.getInstance();
            cal.setTime(movieEntity.getMrelease());
            cal.add(Calendar.HOUR_OF_DAY, +9);
            movieEntity.setMrelease(cal.getTime());

            cal.setTime(movieEntity.getMdeadline());
            cal.add(Calendar.HOUR_OF_DAY, +9);
            movieEntity.setMdeadline(cal.getTime());
            return movieRepository.findById(code).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 영화 리스트 랭킹순
    @Override
    public Page<MovieEntity> selectMovies(Integer page, Integer size) {
        try {
            // 랭킹 1위부터
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by("Mrank").ascending());
            Page<MovieEntity> movie = movieRepository.findAll(pageRequest);
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 장르로 영화코드검색
    @Override
    public Page<MovieCategoryEntity> selectMovieGenre(Integer page, Integer size, Long gcode) {
        try {
            // 영화코드검색 카테고리별
            Pageable pageable = PageRequest.of(page, size);
            Page<MovieCategoryEntity> categoryMovies = movieCategoryRepository.findByCategoryEntity_Ccode(gcode,
                    pageable);

            return categoryMovies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 개봉상태로 영화검색
    @Override
    public Page<MovieEntity> selectMovieState(Integer page, Integer size, Long mscode) {
        try {
            // 영화검색 개봉상태별
            Pageable pageable = PageRequest.of(page, size);
            Page<MovieEntity> stateMovies = movieRepository.findByMovieStateEntity_Mscode(mscode, pageable);
            return stateMovies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 제목으로 영화검색
    @Override
    public Page<MovieEntity> selectMovieTitle(Integer page, Integer size, String title) {
        try {
            // 영화검색 제목기준
            Pageable pageable = PageRequest.of(page, size, Sort.by("Mtitle").ascending());
            Page<MovieEntity> titleMovies = movieRepository.findByMtitleContaining(title, pageable);

            return titleMovies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    // 영화 포스터 여러개 넣기
    @Override
    public int insertMoviePoster(MultipartFile[] files, Long mcode) {
        try {
            List<PosterEntity> posterEntities = new ArrayList<>();
            MovieEntity movieEntity = movieRepository.findById(mcode).orElse(null);
            // 파일이 있는경우 : url이 없음
            if (files != null) {
                for (MultipartFile file : files) {
                    PosterEntity posterEntity = new PosterEntity();
                    posterEntity.setMovieEntity(movieEntity);
                    posterEntity.setPimage(file.getBytes());
                    posterEntity.setPimagename(file.getOriginalFilename());
                    posterEntity.setPimagesize(file.getSize());
                    posterEntity.setPimagetype(file.getContentType());
                    posterEntity.setPhead(1L);
                    posterEntities.add(posterEntity);
                }
                posterRepository.saveAll(posterEntities);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 장르 크롤링
    @Override
    public int insertGenre() {
        try {
            String naverGenreUrl = "https://movie.naver.com/movie/sdb/browsing/bmovie_genre.naver";
            Document naverGenres = Jsoup.connect(naverGenreUrl).get();
            // class명으로 elemnts가져오기
            Elements genre = naverGenres.select(".directory_item_other td");
            Elements genreCode = naverGenres.select(".directory_item_other a");
            // 장르 사이즈만큼 반복
            // 장르 카테고리에 code, 장르명 추가
            for (int i = 0; i < genre.size(); i++) {
                CategoryEntity categoryEntity = new CategoryEntity();
                String gen = genre.get(i).text();
                String code = genreCode.get(i).attr("href").toString();
                System.out.println(gen.substring(0, gen.indexOf("(")));
                System.out.println(code.substring(code.lastIndexOf("=") + 1));
                categoryEntity.setCcategory(gen.substring(0, gen.indexOf("(")));
                categoryEntity.setCcode(Long.parseLong(code.substring(code.lastIndexOf("=") + 1)));
                categoryRepository.save(categoryEntity);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 메인포스터 설정
    @Override
    public int updateMovieMainPoster(Long pcode) {
        try {
            // 메인포스터 선택
            PosterEntity posterEntity = posterRepository.findById(pcode).orElse(null);
            Long mcode = posterEntity.getMovieEntity().getMcode();
            // System.out.println("updateMovieMainPoster ===>" + mcode);

            Long posterhead = posterRepository.findFirstByMovieEntity_McodeOrderByPheadDesc(mcode).getPhead();
            System.out.println("updateMovieMainPoster ===>" + posterhead.toString());
            // 메인포스터 였던거에서 대표등록값 가져와서 메인포스터로 만들 포스터에 값 추가해주기
            posterEntity.setPhead(posterhead + 1L);
            posterRepository.save(posterEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 영화에 해당하는 메인포스터
    @Override
    public PosterEntity selectMovieMainPoster(Long mcode) {
        try {
            // 메인포스터
            return posterRepository.findFirstByMovieEntity_McodeOrderByPheadDesc(mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 영화에 해당하는 포스터 list
    @Override
    public List<PosterEntity> selectMoviePosters(Long mcode) {
        try {
            // poster들을 page로 전달
            return posterRepository.findByMovieEntity_Mcode(mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 포스터 1개삭제
    @Override
    public int deleteMoviePoster(Long pcode) {
        try {
            posterRepository.deleteById(pcode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // image포스터 업데이트
    @Override
    public int updateMoviePoster(Long pcode, MultipartFile file) {
        try {
            PosterEntity posterEntity = posterRepository.getById(pcode);
            // 포스터를 image로 받은경우 : 포스터 image를 업그레이드하고 url은 삭제시켜주기
            posterEntity.setPimage(file.getBytes());
            posterEntity.setPimagename(file.getOriginalFilename());
            posterEntity.setPimagesize(file.getSize());
            posterEntity.setPimagetype(file.getContentType());
            posterEntity.setPimageUrl(null);
            posterRepository.save(posterEntity);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // url포스터 업데이트
    @Override
    public int updateMoviePosterUrl(Long pcode, String url) {
        try {
            PosterEntity posterEntity = posterRepository.findById(pcode).orElse(null);
            // 포스터url을 받은경우 : image들 삭제, imageurl 추가해주기
            posterEntity.setPimage(null);
            posterEntity.setPimagename(null);
            posterEntity.setPimagesize(null);
            posterEntity.setPimagetype(null);

            posterEntity.setPimageUrl(url);
            posterRepository.save(posterEntity);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 포스터 일괄삭제
    @Override
    public int deleteMoviePosters(Long[] pcode) {
        try {
            // 반복문으로 pcode에 해당하는 poster 삭제해주기
            for (long code : pcode) {
                posterRepository.deleteById(code);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 이미지 가져오기
    @Override
    public PosterEntity selectMoviePoster(Long pcode) {
        try {
            return posterRepository.findById(pcode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 영화장르 리스트
    @Override
    public List<MovieCategoryEntity> selectMovieCategories(Long mcode) {
        try {
            return movieCategoryRepository.findByMovieEntity_Mcode(mcode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateLike(Long mcode, Long type) {
        try {
            MovieEntity movieEntity = movieRepository.findById(mcode).orElse(null);
            if (type == 1) {
                movieEntity.setMlike(movieEntity.getMlike() + 1);
                movieRepository.save(movieEntity);
                return 1;
            } else if (type == 0) {
                movieEntity.setMlike(movieEntity.getMlike() - 1);
                movieRepository.save(movieEntity);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
