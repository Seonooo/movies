package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.service.KakaopayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kakao")
public class KakaoPayRestController {
    @Autowired
    KakaopayService kakaopay;

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/kakao/kakaoPay
    @RequestMapping(value = "/kakaoPay", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> kakaoPay(
            @RequestParam(name = "tno") Long tno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println("kakaoPay ticket num" + tno);
            map.put("status", 200);
            map.put("url", kakaopay.kakaoPayReady(tno));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/kakao/kakaoPaySuccess
    @RequestMapping(value = "/kakaoPaySuccess", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> kakaoPaySuccess(
            @RequestParam("pg_token") String pg_token,
            @RequestParam(name = "tno") Long tno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            map.put("status", 200);
            map.put("info", kakaopay.kakaoPayInfo(pg_token,tno));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

}
