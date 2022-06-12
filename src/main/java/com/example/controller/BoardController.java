package com.example.controller;

import java.io.IOException;
import java.io.InputStream;

import com.example.entity.BoardimgProjection;
import com.example.service.BoardimgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    BoardimgService bService;

    @Autowired
    ResourceLoader resLoader;

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> selectoneimageGET(
            @RequestParam(name = "no") long no) throws IOException {
        BoardimgProjection board = bService.selectOneBoardimgPro(no);
        if (board != null) {

            if (board.getBiimagesize() > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (board.getBiimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (board.getBiimagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (board.getBiimagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(board.getBiimage(), headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader.getResource("classpath:/static/img/default.jpeg").getInputStream();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                        headers, HttpStatus.OK);

                return response;
            }

        }
        return null;
    }

}
