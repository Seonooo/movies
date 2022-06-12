package com.example.controller;

import com.example.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping(value = "/crawling")
public class CrawlingController {

    // 크롤링 url
    private final String naverRankUrl = "https://movie.naver.com/movie/running/current.naver?order=reserve";

    @Autowired
    MovieService movieService;

    // 영화 조회
    @GetMapping(value = "/insert")
    public String insertMovieGet() {
        return "/crawling/insert";
    }

    @PostMapping(value = "/insert")
    public String insertMoviePost() {
        try {
            // 크롤링할 url 주기
            movieService.insertMovies(naverRankUrl);
            return "redirect:/crawling/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/home";
        }
    }

    // 영화 1개등록
    @PostMapping(value = "/insertOne")
    public String insertOneMovie() {
        try {
            return "redirect:/crawling/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/home";
        }
    }

    // 영화전체삭제
    @PostMapping(value = "/delete")
    public String deleteMoviesPost() {
        try {
            int ret = movieService.deleteMovies();
            if (ret == 1) {
                return "redirect:/crawling/insert";
            }
            return "redirect:/member/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/home";
        }
    }

    // 장르 등록
    @PostMapping(value = "/genre")
    public String insertGenrePost() {
        try {
            int ret = movieService.insertGenre();
            if (ret == 1) {
                return "redirect:/crawling/insert";
            }
            return "redirect:/member/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member/home";
        }
    }

}
