package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.controller.MovieController;
import com.example.entity.GpaEntity;
import com.example.entity.MovieCategoryEntity;
import com.example.entity.MovieEntity;
import com.example.entity.PosterEntity;
import com.example.jwt.jwtUtil;
import com.example.service.GpaService;
import com.example.service.MovieLikeService;
import com.example.service.MovieService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/movie")
public class MovieRestController {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieController movieController;

    @Autowired
    GpaService gpaService;

    @Autowired
    jwtUtil jwt;

    @Autowired
    MovieLikeService movieLikeService;

    // 영화 한개 등록 - 개봉전
    // http://127.0.0.1:9090/ROOT/api/movie/insertOne
    @RequestMapping(value = "/insertOne", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertMovie(
            @RequestBody MovieEntity movie, @RequestParam(name = "ncode") String ncode,
            @RequestParam(name = "fcode") Long fcode,
            @RequestParam(name = "ccode") Long ccode,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println("fcode" + fcode);
        System.out.println("ccode" + ccode);
        System.out.println("ncode" + ncode);
        System.out.println("movie" + movie);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin")) {
                int ret = movieService.insertMovie(movie, ncode, fcode, 0L, ccode);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 한개 수정
    // http://127.0.0.1:9090/ROOT/api/movie/updateOne
    @RequestMapping(value = "/updateOne", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateMovie(
            @RequestBody MovieEntity movie,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin")) {

                int ret = movieService.updateMovie(movie);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 한개 상세페이지
    // http://127.0.0.1:9090/ROOT/api/movie/selectOne
    @RequestMapping(value = "/selectOne", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectMovie(
            @RequestParam(name = "mcode") Long mcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            List<MovieCategoryEntity> movieCategoryEntity = movieService.selectMovieCategories(mcode);
            MovieEntity movie = movieService.selectMovie(mcode);
            if (movie != null) {
                map.put("status", 200);
                map.put("movie", movie);
                map.put("category", movieCategoryEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 리스트
    // http://127.0.0.1:9090/ROOT/api/movie/select
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectMovies(
            // page받아오기, 페이지에 영화5개씩
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "1", required = false) int sort,
            @RequestParam(name = "genre", defaultValue = "0", required = false) long genre,
            @RequestParam(name = "mscode", defaultValue = "1", required = false) long mscode,
            @RequestParam(name = "title", defaultValue = "", required = false) String title) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // genre = 0 : 장르선택x
            // sort == 1 : 장르별
            // sort == 2 : 상태별
            // mscode => 0 : 개봉전, 1 : 상영중, 2 : 상영마감
            if (genre > 0) {
                // 장르별 리스트
                if (sort == 1) {
                    Page<MovieCategoryEntity> genreOfMoviesPage = movieService.selectMovieGenre(page - 1, size, genre);
                    List<MovieCategoryEntity> genreOfMoives = genreOfMoviesPage.getContent();
                    List<MovieEntity> movies = new ArrayList<>();
                    for (MovieCategoryEntity list : genreOfMoives) {
                        movies.add(list.getMovieEntity());
                        System.out.println(list.getMovieEntity().getMcode());
                    }
                    System.out.println("page : " + page);
                    System.out.println("size : " + size);

                    Page<MovieEntity> moviesPage = new PageImpl<>(movies, PageRequest.of(page, size), movies.size());
                    if (moviesPage != null) {

                        map.put("status", 200);
                        map.put("movies", movies);
                        map.put("total", (movies.size() / (size + 1)) + 1);
                        map.put("pages", movies.size());
                    }
                }
            }
            // size 지정하기
            else if (genre == 0) {
                // 상영 상태별 리스트
                if (sort == 2) {
                    Page<MovieEntity> movies = movieService.selectMovieState(page - 1, size, mscode);
                    List<MovieEntity> stateOfMovies = movies.getContent();
                    int total = movies.getTotalPages();
                    if (movies != null) {
                        map.put("status", 200);
                        map.put("movies", stateOfMovies);
                        map.put("total", total);
                        map.put("pages", movies.getTotalElements());
                    }
                }
                // 제목 검색리스트
                else if (title.length() >= 1) {
                    Page<MovieEntity> movies = movieService.selectMovieTitle(page - 1, size, title);
                    List<MovieEntity> titleOfMovies = movies.getContent();
                    int total = movies.getTotalPages();
                    if (movies != null) {
                        map.put("status", 200);
                        map.put("movies", titleOfMovies);
                        map.put("total", total);
                        map.put("pages", movies.getTotalElements());
                    }
                }
                // 전체 리스트
                else {
                    Page<MovieEntity> movies = movieService.selectMovies(page - 1, size);
                    List<MovieEntity> moviesInPage = movies.getContent();
                    int total = movies.getTotalPages();
                    if (moviesInPage != null) {
                        map.put("status", 200);
                        map.put("movies", moviesInPage);
                        map.put("total", total);
                        map.put("pages", movies.getTotalElements());
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 포스터 등록하기
    // http://127.0.0.1:9090/ROOT/api/movie/poster
    @RequestMapping(value = "/poster", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertMoviePosters(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam(name = "mcode", required = true) Long mcode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");

            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                System.out.println("토큰 확인");
                int ret = movieService.insertMoviePoster(files, mcode);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 좋아요 업데이트
    // http://127.0.0.1:9090/ROOT/api/movie/updatelike
    // ret == 3 : 좋아요 삭제 || ret == 2 : 좋아요 추가
    @RequestMapping(value = "/updatelike", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateMovieLike(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam(name = "mcode", required = true) Long mcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");

            int ret = movieLikeService.selectMovieLike(mid, mcode);
            if (!mid.isEmpty()) {
                if (ret == 1) {
                    ret += movieLikeService.deleteMovieLike(mid, mcode);
                    ret += movieService.updateLike(mcode, 0L);
                    if (ret == 3) {
                        map.put("status", 200);
                        map.put("좋아요", "삭제");
                    }
                } else if (ret == 0) {
                    ret += movieLikeService.insertMovieLike(mid, mcode);
                    ret += movieService.updateLike(mcode, 1L);
                    if (ret == 2) {
                        map.put("status", 200);
                        map.put("좋아요", "추가");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 좋아요 유무
    // http://127.0.0.1:9090/ROOT/api/movie/like
    @RequestMapping(value = "/like", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> MovieLike(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam(name = "mcode", required = true) Long mcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");

            int ret = movieLikeService.selectMovieLike(mid, mcode);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 대표 이미지 설정하기(Phead값 변경)
    // http://127.0.0.1:9090/ROOT/api/movie/main_poster
    @RequestMapping(value = "/main_poster", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateMainPoster(
            @RequestHeader(name = "TOKEN") String token, @RequestParam(name = "pcode") Long pcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 권한 확인후 실행
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                int ret = movieService.updateMovieMainPoster(pcode);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 포스터
    // 이미지 가져오기 http:// 127.0.0.1:9090/ROOT/api/movie/get_poster
    @RequestMapping(value = "/get_poster", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> moviePosterGet(
            @RequestParam(name = "mcode") Long mcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(mcode);

            List<PosterEntity> posters = movieService.selectMoviePosters(mcode);
            List<Map<String, Object>> posterlist2 = new ArrayList<>();

            for (PosterEntity post : posters) {
                // url이 있는경우
                Map<String, Object> map2 = new HashMap<>();
                if (post.getPimageUrl() != null) {
                    map2.put("pcode", post.getPcode());
                    map2.put("pimage", post.getPimageUrl());

                }
                // url이 없는경우
                else if (post.getPimageUrl() == null) {
                    movieController.posterGet(post.getPcode());
                    // movieController를 통해서 url만들어서 list에 넣어주기
                    map2.put("pcode", post.getPcode());
                    map2.put("pimage", "/TEAMMOVIE/movie/poster?pcode=" + post.getPcode());
                }
                posterlist2.add(map2);
            }
            map.put("status", 200);

            map.put("posterlist", posterlist2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 메인 포스터 가져오기
    // http:// 127.0.0.1:9090/ROOT/api/movie/get_main_poster
    @RequestMapping(value = "/get_main_poster", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> movieMainPosterGet(
            @RequestParam(name = "mcode") Long mcode) {
        Map<String, Object> map = new HashMap<>();
        try {

            PosterEntity poster = movieService.selectMovieMainPoster(mcode);
            // url이 없는경우
            if (poster.getPimageUrl() == null) {
                movieController.posterGet(poster.getPcode());
            }
            map.put("status", 200);
            map.put("poster", poster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 포스터 삭제하기
    // http://127.0.0.1:9090/ROOT/api/movie/delete_poster
    @RequestMapping(value = "/delete_poster", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteMoviePoster(
            @RequestParam(name = "pcode") Long pcode,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 권한 확인후에 삭제가능
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                int ret = movieService.deleteMoviePoster(pcode);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 포스터 여러개 삭제하기
    // http://127.0.0.1:9090/ROOT/api/movie/delete_posters
    @RequestMapping(value = "/delete_posters", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteMoviePosters(
            @RequestBody Long[] pcode,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 권한 확인후에 삭제가능
            System.out.println(pcode.toString());
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String userrole = jsonObject.getString("role");
            if (userrole.equals("ADMIN") || userrole.equals("admin") && !token.isEmpty()) {
                int ret = movieService.deleteMoviePosters(pcode);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 영화 평점 가져오기
    // http://127.0.0.1:9090/ROOT/api/movie/get_gpa
    @RequestMapping(value = "/get_gpa", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> getGpa(
            @RequestParam(name = "mcode") Long mcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            GpaEntity gpaEntity = gpaService.selectGpaMcode(mcode);
            map.put("gpa", gpaEntity);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}