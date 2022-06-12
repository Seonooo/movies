package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.CategoryEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MembershipEntity;
import com.example.entity.MovieEntity;
import com.example.entity.MovieLikeEntity;
import com.example.jwt.jwtUtil;
import com.example.repository.CategoryRepository;
import com.example.service.MemberService;
import com.example.service.MembercategoryService;
import com.example.service.MemberpointService;
import com.example.service.MovieLikeService;
import com.example.service.UserDetailsServiceImpl;

// backend만 구현함. 화면구현 X, vue.js 또는 react.js 연동

@RestController
@RequestMapping("/api/member")
public class MemberRestController {

    @Autowired
    CategoryRepository cRepository;

    @Autowired
    MemberService mService;

    @Autowired
    MembercategoryService mcService;

    @Autowired
    MemberpointService mpService;

    @Autowired
    jwtUtil jwt;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    MovieLikeService movieLikeService;

    // 회원가입
    // http://127.0.0.1:9090/ROOT/api/member/join
    @RequestMapping(value = "/join", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> joinPost(
            @RequestBody MemberEntity member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);// 정상적이지 않을 때
        try {

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setMpw(bcpe.encode(member.getMpw()));
            System.out.println("MemberRestController =====> " + member.toString());
            MembershipEntity membership = new MembershipEntity();
            membership.setMscode("BVIP");

            member.setMembershipEntity(membership);

            int ret = mService.insertMember(member);
            if (ret == 1) {
                if (!member.getCategoryCode().isEmpty()) {

                    for (Long code : member.getCategoryCode()) {

                        mcService.insertMembercategory(code, member.getMid());

                    }

                }
                mpService.insertMemberpoint(member.getMid());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 장르 리스트 불러오기
    // http://127.0.0.1:9090/ROOT/api/member/category
    @RequestMapping(value = "/category", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> categoryGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            List<CategoryEntity> list = cRepository.findAll();
            map.put("category", list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 로그인
    // http://127.0.0.1:9090/ROOT/api/member/login
    @RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerLogingePost(
            @RequestBody MemberEntity member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);// 정상적이지 않을 때
        try {
            System.out.println("MemberRestController =====> " + member.toString());
            UserDetails user = userDetailsService.loadUserByUsername(member.getMid());

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 암호화가 되지않은 암호와 암호화된 암호를 비교
            if (bcpe.matches(member.getMpw(), user.getPassword())) {

                MemberEntity member2 = mService.getMember(member.getMid());
                String token = jwt.generatorAllToken(member2.getMrole(), member.getMid(), member2.getMnickname());

                map.put("status", 200);
                map.put("token", token);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 토큰 테스트
    // http://127.0.0.1:9090/ROOT/api/member/tokentest
    @RequestMapping(value = "/tokentest", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerLoginPost(
            @RequestBody MemberEntity member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);// 정상적이지 않을 때
        try {
            System.out.println("MemberRestController =====> " + member.toString());
            UserDetails user = userDetailsService.loadUserByUsername(member.getMid());

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 암호화가 되지않은 암호와 암호화된 암호를 비교
            if (bcpe.matches(member.getMpw(), user.getPassword())) {

                MemberEntity member2 = mService.getMember(member.getMid());
                String token = jwt.generatorAllToken(member2.getMrole(), member.getMid(), member2.getMnickname());

                map.put("status", 200);
                map.put("token", token);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 토큰 읽기
    // http://127.0.0.1:9090/ROOT/api/member/readtoken
    @RequestMapping(value = "/readtoken", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> readTokenPost(
            @RequestHeader(name = "TOKEN") String token) {
        // 토큰이 있어야 실행됨
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token);

            if (!token.isEmpty()) {
                map.put("status", 200);
                String username = jwt.extractUsername(token);

                JSONObject jsonObject = new JSONObject(username);
                String role = jsonObject.getString("role");
                String userid = jsonObject.getString("mid");
                String nickname = jsonObject.getString("nickname");
                map.put("userid", userid);
                map.put("nickname", nickname);
                map.put("role", role);

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 토큰 읽기
    // http://127.0.0.1:9090/ROOT/api/member/mtoken
    @RequestMapping(value = "/mtoken", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> memberPost(
            @RequestHeader(name = "TOKEN") String token) {
        // 토큰이 있어야 실행됨
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token);

            if (!token.isEmpty()) {
                map.put("status", 200);
                String username = jwt.extractUsername(token);

                JSONObject jsonObject = new JSONObject(username);
                String role = jsonObject.getString("role");
                String userid = jsonObject.getString("mid");
                String nickname = jsonObject.getString("nickname");
                map.put("userid", userid);
                map.put("nickname", nickname);
                map.put("role", role);

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 마이페이지
    // http://127.0.0.1:9090/ROOT/api/member/mypage
    @RequestMapping(value = "/mypage", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerMypageGet(@RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰으로 아이디 찾기
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);

            String mid = jsonObject.getString("mid");
            System.out.println("memberId ===> " + mid);
            // 토큰에서 추출한 아이디로 member찾기
            MemberEntity member = mService.getMember(mid);
            // member에 member전송하기
            map.put("status", 200);
            map.put("member", member);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 200);
        }
        return map;
    }

    // 아이디 중복 확인
    // http://127.0.0.1:9090/ROOT/api/member/memberid
    @RequestMapping(value = "/memberid", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> memberidPost(
            @RequestBody MemberEntity member) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mService.findMember(member.getMid());
            map.put("status", 0);
            if (ret == 0) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 프로필사진 업데이트
    // http://127.0.0.1:9090/ROOT/api/member/updateprofile
    @RequestMapping(value = "/updateprofile", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateprofilePOST(
            @RequestParam(name = "file", required = true) MultipartFile file,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String mid = jsonObject.getString("mid");
                System.out.println("memberId ===> " + mid);
                map.put("userid", mid);
                MemberEntity member = mService.getMember(mid);
                if (file != null) {
                    if (!file.isEmpty()) {
                        member.setMprofile(file.getBytes());
                        member.setMprofilename(file.getOriginalFilename());
                        member.setMprofilesize(file.getSize());
                        member.setMprofiletype(file.getContentType());
                    }
                }
                int ret = mService.updateprofile(member);

                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 프로필사진 지우기
    // http://127.0.0.1:9090/ROOT/api/member/deleteprofile
    @RequestMapping(value = "/updateprofile", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> deleteprofilePOST(
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String mid = jsonObject.getString("mid");
                map.put("userid", mid);
                MemberEntity member = mService.getMember(mid);
                member.setMprofile(null);
                member.setMprofilename(null);
                member.setMprofilesize(null);
                member.setMprofiletype(null);
                int ret = mService.updateprofile(member);

                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 회원정보수정
    // 암호, 주소, 이메일, 연락처 변경
    // http://127.0.0.1:9090/ROOT/api/member/updateinfo
    @RequestMapping(value = "/updateinfo", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerInfoPut(@RequestHeader(name = "TOKEN") String token, // 토큰받기
            @RequestBody MemberEntity member) {
        System.out.println(member.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 토큰으로 아이디받음
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            member.setMid(mid);
            // 토큰에서 받은 아이디로 user정보 받음
            UserDetails userinfo = userDetailsService.loadUserByUsername(mid);
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            if (bcpe.matches(member.getMpw(), userinfo.getPassword())) {
                // newMember에 업데이트할 정보를 가져옴
                mService.updateMember(member);
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 회원탈퇴
    // 회원 아이디만 남기고 나머지는 null값으로 변경
    // http://127.0.0.1:9090/ROOT/api/member/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerMypagePut(@RequestHeader(name = "TOKEN") String token,
            @RequestBody MemberEntity oldmember) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            UserDetails userinfo = userDetailsService.loadUserByUsername(mid);
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            if (bcpe.matches(oldmember.getMpw(), userinfo.getPassword())) {
                mService.deleteMember(userinfo.getUsername());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    // 회원이 좋아요누른 영화들
    // http://127.0.0.1:9090/ROOT/api/member/movies_like
    @RequestMapping(value = "/movies_like", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> moviesLike(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            Page<MovieLikeEntity> mPage = movieLikeService.selectMoviesLike(mid, size, page - 1);
            List<MovieLikeEntity> likeList = mPage.getContent();
            List<MovieEntity> movieList = new ArrayList<>();
            for (MovieLikeEntity list : likeList) {
                movieList.add(list.getMovieEntity());
            }
            Page<MovieEntity> moviesPage = new PageImpl<>(movieList, PageRequest.of(page, size), movieList.size());
            if (moviesPage != null) {
                map.put("movies", movieList);
                map.put("pages", moviesPage.getTotalPages());
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 아이디 찾기
    // http://127.0.0.1:9090/ROOT/api/member/find_id
    @RequestMapping(value = "/find_id", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> findId(@RequestBody MemberEntity memberEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            MemberEntity FindmemberEntity = mService.findMid(memberEntity.getMname(), memberEntity.getMphone());
            String mid = FindmemberEntity.getMid();
            if (!mid.isEmpty()) {
                map.put("status", 200);
                map.put("mid", mid);
            } else {
                map.put("status", 200);
                map.put("mid", "Not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 비밀번호 찾기
    // http://127.0.0.1:9090/ROOT/api/member/find_pw_chk
    @RequestMapping(value = "/find_pw_chk", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> findPw(
            @RequestBody MemberEntity memberEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            int ret = mService.findPwChk(memberEntity.getMid(), memberEntity.getMname(), memberEntity.getMphone());
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 새 비밀번호로 변경
    // http://127.0.0.1:9090/ROOT/api/member/update_pw
    @RequestMapping(value = "/update_pw", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> UpdatePw(
            @RequestBody MemberEntity memberEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            int ret = mService.findMpw(memberEntity);
            System.out.println(ret);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}