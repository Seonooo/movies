package com.example.restcontroller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;
import com.example.entity.BoardimgEntity;
import com.example.entity.BoardimgProjection;
import com.example.entity.BoardlikeEntity;
import com.example.entity.CommentEntity;
import com.example.entity.CommentProjection;
import com.example.entity.MemberEntity;
import com.example.jwt.jwtUtil;
import com.example.repository.BoardRepository;
import com.example.service.BoardService;
import com.example.service.BoardimgService;
import com.example.service.BoardlikeService;
import com.example.service.CommentService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// backend만 구현함. 화면구현 X, vue.js 또는 react.js 연동

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    jwtUtil jwt;

    @Autowired
    BoardService bService;

    @Autowired
    BoardRepository bRepository;

    @Autowired
    CommentService cService;

    @Autowired
    BoardimgService biService;

    @Autowired
    BoardlikeService blService;

    // 게시판 글쓰기(이미지 포함)
    // http://127.0.0.1:9090/ROOT/api/board/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @ModelAttribute BoardEntity board,
            @RequestParam(name = "file", required = false) MultipartFile file[],
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");

                MemberEntity member = new MemberEntity();
                member.setMid(userid);
                board.setMemberEntity(member);
                int ret = bService.insertBoard(board);
                if (ret == 1) {
                    map.put("게시글", "작성");
                    map.put("status", 200);
                    if (!file[0].isEmpty()) {
                        for (MultipartFile file2 : file) {
                            if (!file2.isEmpty()) {
                                BoardEntity newBoard = new BoardEntity();
                                BoardimgEntity boardimg = new BoardimgEntity();
                                Long bno = bService.selectLastBoard();

                                newBoard.setBno(bno);

                                boardimg.setBiimage(file2.getBytes());
                                boardimg.setBiimagename(file2.getOriginalFilename());
                                boardimg.setBiimagesize(file2.getSize());
                                boardimg.setBiimagetype(file2.getContentType());
                                boardimg.setBoardEntity(newBoard);

                                biService.insertBoardimg(boardimg);
                                map.put("이미지", "첨부완료");
                            } else {
                                map.put("이미지", "누락");
                            }
                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글쓰기(이미지 포함)
    // http://127.0.0.1:9090/ROOT/api/board/update
    @RequestMapping(value = "/update", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePOST(
            @ModelAttribute BoardEntity board,
            @RequestParam(name = "file", required = false) MultipartFile file[],
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");

                MemberEntity member = new MemberEntity();
                member.setMid(userid);
                board.setMemberEntity(member);
                int ret = bService.insertBoard(board);
                if (ret == 1) {
                    map.put("게시글", "작성");
                    map.put("status", 200);
                    if (!file[0].isEmpty()) {
                        for (MultipartFile file2 : file) {
                            if (!file2.isEmpty()) {
                                BoardEntity newBoard = new BoardEntity();
                                BoardimgEntity boardimg = new BoardimgEntity();

                                newBoard.setBno(board.getBno());

                                boardimg.setBiimage(file2.getBytes());
                                boardimg.setBiimagename(file2.getOriginalFilename());
                                boardimg.setBiimagesize(file2.getSize());
                                boardimg.setBiimagetype(file2.getContentType());
                                boardimg.setBoardEntity(newBoard);

                                biService.insertBoardimg(boardimg);
                                map.put("이미지", "첨부완료");
                            } else {
                                map.put("이미지", "누락");
                            }
                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글쓰기(이미지 미포함)
    // http://127.0.0.1:9090/ROOT/api/board/insertnoimg
    @RequestMapping(value = "/insertnoimg", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertNoimgPOST(
            @ModelAttribute BoardEntity board,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");

                MemberEntity member = new MemberEntity();
                member.setMid(userid);
                board.setMemberEntity(member);
                int ret = bService.insertBoard(board);
                if (ret == 1) {
                    map.put("게시글", "작성");
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 이미지 수정
    // http://127.0.0.1:9090/ROOT/api/board/updateimg
    @RequestMapping(value = "/updateimg", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertboardPOST(
            @RequestParam(name = "no") Long no,
            @RequestParam(name = "file", required = true) MultipartFile file,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {

                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");

                BoardimgEntity boardimg = biService.selectOneBoardimg(no);

                if (userid.equals(boardimg.getBoardEntity().getMemberEntity().getMid())) {
                    if (!file.isEmpty()) {

                        boardimg.setBiimage(file.getBytes());
                        boardimg.setBiimagename(file.getOriginalFilename());
                        boardimg.setBiimagesize(file.getSize());
                        boardimg.setBiimagetype(file.getContentType());

                        int ret = biService.insertBoardimg(boardimg);
                        if (ret == 1) {
                            map.put("status", 200);
                            System.out.println("수정완료");
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글 수정
    // http://127.0.0.1:9090/ROOT/api/board/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePOST(
            @RequestParam Long no,
            @RequestBody BoardEntity board,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // System.out.println(token.toString());

            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                // System.out.println(user);
                BoardEntity boardold = bService.selectOneBoard(no);
                // System.out.println(boardold.getMemberEntity().getMId());

                if (userid.equals(boardold.getMemberEntity().getMid())) {
                    boardold.setBtitle(board.getBtitle());

                    boardold.setBcontent(board.getBcontent());
                    System.out.println("boardold.setBcontet" + board.getBcontent());
                    boardold.setBtype(board.getBtype());

                    bService.insertBoard(boardold);
                    map.put("status", 200);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글 수정
    // http://127.0.0.1:9090/ROOT/api/board/admintest
    @RequestMapping(value = "/admintest", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> testPUT(
            @RequestParam Long no,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            System.out.println(no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 관리자 글 삭제
    // http://127.0.0.1:9090/ROOT/api/board/admindelete
    @RequestMapping(value = "/admindelete", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> admindeletePUT(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // System.out.println(token.toString());

            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String role = jsonObject.getString("role");
                BoardEntity boardold = bService.selectOneBoard(no);

                if (role.equals("admin") || role.equals("ADMIN")) {
                    MemberEntity member = new MemberEntity();
                    member.setMid("admin");
                    boardold.setMemberEntity(member);
                    boardold.setBtitle("이 글은 관리자에 의해 삭제된 게시글 입니다.");
                    boardold.setBcontent("이 글은 관리자에 의해 삭제된 게시글 입니다.");

                    bService.insertBoard(boardold);
                    map.put("status", 200);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글 삭제
    // http://127.0.0.1:9090/ROOT/api/board/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePOST(
            @RequestParam Long no,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                BoardEntity boardold = bService.selectOneBoard(no);

                if (userid.equals(boardold.getMemberEntity().getMid())) {

                    int ret = 0;
                    List<BoardimgProjection> boardimg = biService.selectListBoardimg(no);
                    for (BoardimgProjection boardimg2 : boardimg) {
                        ret += biService.deleteBoardimg(boardimg2.getBino());
                    }

                    if (ret == boardimg.size()) {
                        ret = bService.deleteBoard(no);
                        if (ret == 1) {
                            map.put("status", 200);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글 이미지 삭제
    // http://127.0.0.1:9090/ROOT/api/board/deleteoneimg
    @RequestMapping(value = "/deleteoneimg", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteonePOST(
            @RequestParam Long no,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // System.out.println(token.toString());
            BoardimgProjection boardimg = biService.selectOneBoardimgPro(no);

            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                String role = jsonObject.getString("role");
                System.out.println(role);
                BoardProjection board = bService.selectOneBoardPro(boardimg.getBoardEntityBno());
                if (userid.equals(board.getMemberEntityMid()) || role.equals("admin") || role.equals("ADMIN")) {

                    int ret = biService.deleteBoardimg(no);

                    if (ret == 1) {
                        map.put("status", 200);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 글 이미지 전체 삭제
    // http://127.0.0.1:9090/ROOT/api/board/deleteallimg
    @RequestMapping(value = "/deleteallimg", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteimgPOST(
            @RequestParam Long no,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(token.toString());
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                String role = jsonObject.getString("role");
                BoardProjection boardold = bService.selectOneBoardPro(no);
                if (userid.equals(boardold.getMemberEntityMid()) || role.equals("admin") || role.equals("ADMIN")) {

                    int ret = 0;
                    List<BoardimgProjection> boardimg = biService.selectListBoardimg(no);
                    for (BoardimgProjection boardimg2 : boardimg) {
                        ret += biService.deleteBoardimg(boardimg2.getBino());
                    }

                    if (ret == boardimg.size()) {

                        map.put("status", 200);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 조회수 1 증가
    // http://127.0.0.1:9090/ROOT/api/board/updatehit
    @RequestMapping(value = "/updatehit", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatehitPOST(
            @RequestParam Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            int ret = bService.updateHit(no);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 좋아요수 1 증가
    // http://127.0.0.1:9090/ROOT/api/board/updatelike
    @RequestMapping(value = "/updatelike", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatelikePOST(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String mid = jsonObject.getString("mid");

                BoardlikeEntity boardlike = new BoardlikeEntity();
                BoardEntity board = new BoardEntity();
                MemberEntity member = new MemberEntity();

                board.setBno(no);
                member.setMid(mid);
                boardlike.setBoardEntity(board);
                boardlike.setMemberEntity(member);

                int ret = blService.findLike(mid, no);
                System.out.println(ret);
                if (ret == 0) {
                    ret += bService.updateLike(no, 1L);
                    ret += blService.insertLike(boardlike);
                    if (ret == 2) {
                        map.put("status", 200);
                        map.put("좋아요", "추가");
                    }
                } else if (ret == 1) {
                    ret += bService.updateLike(no, 0L);
                    ret += blService.deleteLike(mid, no);
                    if (ret == 3) {
                        map.put("status", 200);
                        map.put("좋아요", "삭제");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/board/like
    @RequestMapping(value = "/like", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> likeGET(
            @RequestHeader(name = "TOKEN") String token,
            @RequestParam(name = "no") Long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String user = jwt.extractUsername(token);
            JSONObject jsonObject = new JSONObject(user);
            String mid = jsonObject.getString("mid");
            int ret = blService.findLike(mid, bno);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 전체 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/board/selectlist
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectListGET(
            @RequestParam(name = "type", required = false) Long type,
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pagecnt", defaultValue = "5") int pagecnt) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, pagecnt);
            List<BoardProjection> boardlist;
            if (type != null) {
                boardlist = bService.selectListTypePro(type, title, pageRequest);
                map.put("boardcnt", bService.countListType(type, title));
            } else {
                boardlist = bService.selectListPro(title, pageRequest);
                map.put("boardcnt", bService.countList(title));
            }
            map.put("boardlist", boardlist);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 타입별 리스트 가져오기
    // http://127.0.0.1:9090/ROOT/api/board/selectlisttype
    @RequestMapping(value = "/selectlisttype", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectListTypeGET(
            @RequestParam(name = "type", defaultValue = "") Long type,
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pagecnt", defaultValue = "5") int pagecnt) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, pagecnt);
            List<BoardProjection> boardlist = bService.selectListTypePro(type, title, pageRequest);
            map.put("boardlisttype", boardlist);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시글 1개 가져오기
    // http://127.0.0.1:9090/ROOT/api/board/selectone
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(
            @RequestParam Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            BoardProjection board = bService.selectOneBoardPro(no);
            List<BoardimgProjection> boardimg = biService.selectListBoardimg(no);
            List<Long> imglist = new ArrayList<>();
            for (int i = 0; i < boardimg.size(); i++) {
                imglist.add(boardimg.get(i).getBino());
            }
            // System.out.println(board.toString());
            map.put("board", board);
            map.put("boardimg", imglist);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 게시판 댓글쓰기
    // http://127.0.0.1:9090/ROOT/api/board/commentinsert
    // {"ccontent":"aaa","boardEntity":{"bno":43}} 아이디는 토큰에서 추출
    @RequestMapping(value = "/commentinsert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> commentinsertGET(
            @RequestBody CommentEntity commentEntity,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                MemberEntity member = new MemberEntity();
                member.setMid(userid);
                commentEntity.setMemberEntity(member);
                System.out.println(commentEntity.toString());
                int ret = cService.insertComment(commentEntity);
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

    // 댓글리스트가져오기
    // http://127.0.0.1:9090/ROOT/api/board/commentlist
    @RequestMapping(value = "/commentlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> commentlistGET(
            @RequestParam(name = "no") Long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            List<CommentProjection> comment = cService.selectlistBno(bno);
            map.put("comments", comment);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 댓글삭제
    // http://127.0.0.1:9090/ROOT/api/board/commentdelete
    @RequestMapping(value = "/commentdelete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> commentdelGET(
            @RequestParam(name = "cno") Long cno,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                CommentProjection comment = cService.selectOnePro(cno);
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                if (userid.equals(comment.getMemberEntityMid())) {
                    int ret = cService.deleteComment(cno);
                    if (ret == 1) {
                        map.put("status", 200);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 댓글수정
    // http://127.0.0.1:9090/ROOT/api/board/commentupdate
    @RequestMapping(value = "/commentupdate", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> commentupdateGET(
            @RequestBody CommentEntity commentnew,
            @RequestHeader(name = "TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            if (!token.isEmpty()) {
                System.out.println(commentnew.toString());
                CommentEntity comment = cService.selectOne(commentnew.getCno());
                comment.setCcontent(commentnew.getCcontent());

                Long datetime = System.currentTimeMillis();
                Timestamp timestamp = new Timestamp(datetime);

                comment.setCregdate(timestamp);
                String user = jwt.extractUsername(token);
                JSONObject jsonObject = new JSONObject(user);
                String userid = jsonObject.getString("mid");
                if (userid.equals(comment.getMemberEntity().getMid())) {
                    int ret = cService.insertComment(comment);
                    if (ret == 1) {
                        map.put("status", 200);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/board/selectPrevNext
    @RequestMapping(value = "/selectPrevNext", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectPrevNextGET(
            @RequestParam Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            BoardEntity board = bService.selectOneBoard(no);

            BoardProjection boardPrev = bService.selectPrev(no, board.getBtype());
            BoardProjection boardNext = bService.selectNext(no, board.getBtype());
            map.put("Prev", boardPrev);
            map.put("Next", boardNext);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 샘플
    // http://127.0.0.1:9090/ROOT/api/board/
    @RequestMapping(value = "/sample", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> sampleGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

}