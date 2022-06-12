package com.example.service;

import com.example.domain.KakaoPayApprovalVO;
import com.example.domain.KakaoPayReadyVO;
import com.example.entity.TicketEntity;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class KakaopayService {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;

    private KakaoPayApprovalVO kakaoPayApprovalVO;

    @Autowired
    TicketService tService;

    public String kakaoPayReady(Long tno) {

        RestTemplate restTemplate = new RestTemplate();
        TicketEntity ticket = tService.selectTicket(tno);
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        // 카카오에서 받은 admin키 넣기
        headers.add("Authorization", "KakaoAK " + "f469e21ed7cd0ed11e6cb5f083952753");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(ticket.getTno()));
        params.add("partner_user_id", ticket.getMemberEntity().getMid());
        params.add("item_code", String.valueOf(ticket.getMovieEntity().getMcode()));
        params.add("item_name",
                ticket.getMovieEntity().getMtitle() + " / 상영관 : " + ticket.getTheaterEntity().getThtype());
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(ticket.getTheaterEntity().getThprice()));
        params.add("tax_free_amount", "1000");
        params.add("approval_url", "http://192.168.0.120:8080/kakaoPaySuccess?tno=" + ticket.getTno());
        params.add("cancel_url", "http://localhost:8080/kakaoPayCancel?tno" + ticket.getTno());
        params.add("fail_url", "http://localhost:8080/kakaoPaySuccessFail?tno" + ticket.getTno());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        System.out.println(body.toString());
        try {

            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
                    KakaoPayReadyVO.class);
            log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "/pay";

    }

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, Long tno) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();
        TicketEntity ticket = tService.selectTicket(tno);
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "f469e21ed7cd0ed11e6cb5f083952753");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", String.valueOf(ticket.getTno()));
        params.add("partner_user_id", ticket.getMemberEntity().getMid());
        params.add("item_code", String.valueOf(ticket.getMovieEntity().getMcode()));
        params.add("pg_token", pg_token);
        params.add("total_amount", String.valueOf(ticket.getTheaterEntity().getThprice()));

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body,
                    KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {

            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
