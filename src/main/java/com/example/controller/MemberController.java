package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.entity.CategoryEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MembercategoryEntity;
import com.example.entity.MemberpointEntity;
import com.example.entity.MembershipEntity;
import com.example.jwt.jwtUtil;
import com.example.repository.CategoryRepository;
import com.example.repository.MemberRepository;
import com.example.repository.MembercategoryRepository;
import com.example.repository.MemberpointRepository;
import com.example.repository.MembershipRepository;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    CategoryRepository cRepository;

    @Autowired
    MemberRepository mRepository;

    @Autowired
    MembershipRepository msRepository;

    @Autowired
    MembercategoryRepository mcRepository;

    @Autowired
    MemberpointRepository mpRepository;

    @Autowired
    MemberService mService;

    @Autowired
    jwtUtil jwt;

    @Autowired
    ResourceLoader resLoader;

    // ?????? ???
    @GetMapping(value = { "/", "/home" })
    public String homeGET(
            Model model,
            @AuthenticationPrincipal User user) {
        MemberEntity member = mRepository.findById("aaa").orElse(null);
        System.out.println(member.getMid());
        model.addAttribute("user", user);
        return "/member/home";
    }

    // ????????? ???
    @GetMapping(value = { "/login" })
    public String loginGET() {

        return "/member/login";
    }

    // ???????????????
    @GetMapping(value = { "/mypage" })
    public String mypageGET() {

        return "/member/mypage";
    }

    // ????????????
    @GetMapping(value = { "/logout" })
    public String logoutGET() {

        return "/member/logout";
    }

    // ???????????? ?????????
    @GetMapping(value = { "/insert" })
    public String insertGET(
            Model model) {
        List<CategoryEntity> list = cRepository.findAll();
        // System.out.println(list);
        model.addAttribute("category", list);
        return "/member/insert";
    }

    // ????????????
    @PostMapping(value = { "/insert" })
    public String insertPOST(
            @ModelAttribute MemberEntity member) throws ParseException {
        // ???????????? HASH
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setMpw(bcpe.encode(member.getMpw()));

        // ???????????? ??????
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String birth = member.getYear() + "-" + member.getMonth() + "-" + member.getDay();
        Date date = formatter.parse(birth);
        member.setMbirth(date);

        // ????????? ??????
        MembershipEntity membership = new MembershipEntity();
        membership.setMscode("BVIP");
        member.setMembershipEntity(membership);

        mRepository.save(member);

        // ?????? ???????????? ??????

        for (Long code : member.getCategoryCode()) {
            MembercategoryEntity membercategory = new MembercategoryEntity();
            CategoryEntity category = new CategoryEntity();
            membercategory.setMember(member);
            category.setCcode(code);
            membercategory.setCategory(category);
            mcRepository.save(membercategory);
            System.out.println(code);
        }

        // ?????? ????????? ??????
        MemberpointEntity memberpoint = new MemberpointEntity();
        memberpoint.setMember(member);
        memberpoint.setMpnowpoint(0L);
        memberpoint.setMpstackpoint(0L);
        memberpoint.setMpusepoint(0L);

        mpRepository.save(memberpoint);

        System.out.println(member.toString());
        return "redirect:/member/insert";
    }

    // ?????? ????????? ????????? ????????????
    // http://127.0.0.1:9090/ROOT/member/memberprofile
    @GetMapping(value = "/memberprofile")
    public ResponseEntity<byte[]> selectoneimageGET(
            @RequestParam(name = "mid") String mid) throws IOException {

        MemberEntity member = mService.getMember(mid);

        if (member != null) {

            if (member.getMprofilesize() != null) {
                HttpHeaders headers = new HttpHeaders();
                if (member.getMprofiletype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (member.getMprofiletype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (member.getMprofiletype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(member.getMprofile(), headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader.getResource("classpath:/static/img/profile.png").getInputStream();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                        headers, HttpStatus.OK);

                return response;
            }
        }
        return null;
    }
}
