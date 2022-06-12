package com.example.controller;

import java.io.IOException;

import com.example.entity.PosterEntity;
import com.example.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/poster")
    public ResponseEntity<byte[]> posterGet(
            @RequestParam(name = "pcode") long pcode)
            throws IOException {
        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        PosterEntity posterEntity = movieService.selectMoviePoster(pcode);
        HttpHeaders headers = new HttpHeaders();
        if (posterEntity.getPimagetype().equals("image/jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (posterEntity.getPimagetype().equals("image/png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (posterEntity.getPimagetype().equals("image/gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        }
        ResponseEntity<byte[]> response = new ResponseEntity<>(posterEntity.getPimage(), headers, HttpStatus.OK);
        return response;

    }
}
