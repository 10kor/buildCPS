package com.buildCps.cps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.QnaDto;
import com.buildCps.cps.dto.ReplyDto;
import com.buildCps.cps.service.PagingPgm;
import com.buildCps.cps.service.QnaService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class QnaController {
	
	@Autowired
	private QnaService service;
	
	// Get IP
	private String getRemoteIp(HttpServletRequest request) {
		String remoteIp = "";
		try {
			String[] headersToCheck = { "X-Forwarded-For", "Forwarded", "Proxy-Client-IP", 
					"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = headersToCheck).length, b = 0; b < i; ) {
				String header = arrayOfString1[b];
				remoteIp = request.getHeader(header);
				if (remoteIp != null && remoteIp.length() > 0 && !"unknown".equalsIgnoreCase(remoteIp)) {
					break; 
				}
				b++;
			} 
			if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
				remoteIp = request.getRemoteAddr(); 
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} 
		return remoteIp;
	}
	// Qna 게시판
	@RequestMapping({"/qna_list"})
	public String qna_list(String pageNum, QnaDto qd, HttpServletRequest request, HttpSession session, 
			Model model) throws Exception {
		MemberDto get = (MemberDto)session.getAttribute("md");
	    if (get != null) {
	    	String id = get.getId();
			log.info("[ Qna게시판 ] ID : " + id + ", IP : " + getRemoteIp(request));
	    } else {
	    	log.info("[ Qna게시판 ] 세션없음, IP : " + getRemoteIp(request));
	    } 
	    // 페이징처리
	    int rowPerPage = 15;
	    if (pageNum == null || pageNum.equals("")) {
	    	pageNum = "1"; 
	    }
	    int currentPage = Integer.parseInt(pageNum);
	    int total = service.getQnaTotal(qd);
	    int startRow = (currentPage - 1) * rowPerPage + 1;
	    int endRow = startRow + rowPerPage - 1;
	    PagingPgm pp = new PagingPgm(total, 15, currentPage);
	    qd.setStartRow(startRow);
	    qd.setEndRow(endRow);
	    int no = total - startRow + 1;
	    // 내용 가져오기
	    List<QnaDto> qnaList = service.qnaList(qd);
	    // 게시글 비밀번호 지우기
	    for (QnaDto qnaDto : qnaList) {
	    	qnaDto.setQpasswd("");
	    	int getCountReply = service.getCountReply(qnaDto.getQno());
	    	qnaDto.setQreply(getCountReply);
	    } 
	    model.addAttribute("qnaList", qnaList);
	    model.addAttribute("no", Integer.valueOf(no));
	    model.addAttribute("pp", pp);
	    return "qna/list";
	}
	// Qna 글쓰기
	@RequestMapping({"/qna_add"})
	public String qna_add(MemberDto md, HttpSession session, HttpServletRequest request, Model model) throws Exception {
	    if (request.getSession(false) == null) {
	    	return "redirect:/qna_list";
	    } 
	    MemberDto get = (MemberDto)session.getAttribute("md");
	    String id = get.getId();
		log.info("[ Qna작성 ]  ID : " + id + ", IP : " + getRemoteIp(request));
	    model.addAttribute("id", id);
	    return "qna/add";
	}
	// Qna 글쓰기 완료
	@RequestMapping({"/qna_add_action"})
	public String qna_add_action(@RequestParam String id, QnaDto qd, HttpSession session, Model model, 
			HttpServletRequest request) throws Exception {
	    if (request.getSession(false) == null) {
	    	return "redirect:/qna_list";
	    } 
	    int result = 0;
	    result = service.qnaAdd(qd);
	    if (result == 1) {
			log.info("[ Qna작성 ] 작성 성공 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } else {
			log.info("[ Qna작성 ] 작성 실패 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return "qna/add_action";
	}
	// Qna 게시글 보기
	@RequestMapping({"/qna_view"})
	public String qna_view(String pageNum, int qno, QnaDto qd, HttpServletResponse response, HttpSession session, 
			Model model, HttpServletRequest request) throws Exception {
		MemberDto get = (MemberDto)session.getAttribute("md");
	    if (get != null) {
	    	String id = get.getId();
	    	model.addAttribute("id", id);
			log.info("[ Qna글보기 ] "+qno+"번 게시글 보기 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    }
	    // 쿠키 확인 후 Count+1
	    boolean check = true;
	    Cookie[] cookies = request.getCookies();
	    String qnoParam = request.getParameter("qno");
	    if (cookies != null) {
	    	byte b;
	    	int i;
	    	Cookie[] arrayOfCookie;
	    	for (i = (arrayOfCookie = cookies).length, b = 0; b < i; ) {
	    		Cookie cookie = arrayOfCookie[b];
	    		if (cookie.getName().equals("visit_cookie")) {
	    			check = false;
	    			String cookieValue = cookie.getValue();
	    			if (cookieValue != null && qnoParam != null && !cookieValue.contains(qnoParam)) {
	    				cookie.setValue(String.valueOf(cookie.getValue()) + "_" + request.getParameter("qno"));
	    				cookie.setMaxAge(7200);
	    				response.addCookie(cookie);
	    				service.qnaCountPlus(qno);
	    			} 
	    			break;
	        } 
	        b++;
	    	} 
	    } 
	    if (check) {
	      Cookie newCookie = new Cookie("visit_cookie", request.getParameter("qno"));
	      newCookie.setMaxAge(7200);
	      response.addCookie(newCookie);
	      service.qnaCountPlus(qno);
	    } 
	    // 내용 가져오기
	    List<QnaDto> qnaview = service.qnaView(qno);
	    for (QnaDto qnaDto : qnaview){
	    	qnaDto.setQpasswd(""); 
	    }
	    model.addAttribute("qnaview", qnaview);
	    List<ReplyDto> ReplyList = service.ReplyList(qno);
	    model.addAttribute("ReplyList", ReplyList);
	    model.addAttribute("pageNum", pageNum);
	    return "qna/view";
	}
	// 게시글 수정
	@RequestMapping({"/qna_update"})
	public String qna_update(@RequestParam int qno, @RequestParam String pageNum, HttpSession session, 
			Model model, HttpServletRequest request) throws Exception {
		if (request.getSession(false) == null) {
			return "redirect:/qna_list";
		} 
	    MemberDto get = (MemberDto)session.getAttribute("md");
	    String id = get.getId();
	    QnaDto list = service.qnaUpdateSelect(qno);
	    list.setQpasswd("");
	    String qid = list.getId();
	    if (!id.equals(qid)) {
			log.info("[ Qna수정 ] 로드 실패 :: 작성자 다름 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    	return "redirect:/qna_list";
	    } 
		log.info("[ Qna수정 ] 로드 성공 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    model.addAttribute("list", list);
	    model.addAttribute("id", id);
	    model.addAttribute("pageNum", pageNum);
	    return "qna/update";
	}
	// 게시글 수정 완료
	@RequestMapping({"/qna_update_action"})
	public String qna_update_action(@RequestParam int qno, @RequestParam String pageNum, @RequestParam String id,
			HttpServletRequest request, QnaDto qd, HttpSession session, Model model) throws Exception {
		if (request.getSession(false) == null) {
			return "redirect:/qna_list";
	    } 
	    int result = 0;
	    String pass1 = qd.getQpasswd();
	    String pass2 = service.qnaPasswdCk(qno);
	    if (!pass1.equals(pass2)) {
	    	model.addAttribute("result", Integer.valueOf(result));
			log.info("[ Qna수정 ] "+qno+"번 수정 실패 :: 비밀번호 틀림 :: ID : " + id + ", IP : " + getRemoteIp(request));
	      return "qna/update_action";
	    } 
	    result = service.qnaUpdate(qd);
	    if (result == 1) {
	    	result = 1;
			log.info("[ Qna수정 ] "+qno+"번 수정 성공 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } else {
	    	result = 2;
			log.info("[ Qna수정 ] "+qno+"번 수정 실패 :: 통신장애 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    model.addAttribute("qno", Integer.valueOf(qno));
	    model.addAttribute("pageNum", pageNum);
	    return "qna/update_action";
	}
	// 게시글 삭제
	@ResponseBody
	@RequestMapping(value = {"/qnaDelete"}, method = {RequestMethod.POST})
	public int qnaDelete(String qno, String qid, String qpasswd, HttpServletRequest request, Model model, 
			HttpSession session) throws Exception {
	    int result = 0;
	    MemberDto get = (MemberDto)session.getAttribute("md");
	    String id = get.getId();
	    String pass = service.qnaPasswdCk(Integer.parseInt(qno));
	    if (!qpasswd.equals(pass)) {
	    	model.addAttribute("result", Integer.valueOf(result));
			log.info("[ QnaDelete ] "+qno+"번 삭제 실패 :: 비밀번호 틀림 :: ID : " + id + ", IP : " + getRemoteIp(request));
	      return result;
	    } 
	    if (qid.equals(id))
	    	result = service.qnaDelete(Integer.parseInt(qno)); 
	    if (result == 1) {
	    	result = 1;
			log.info("[ Qna삭제 ] "+qno+"번 삭제 성공 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } else {
	    	result = 2;
			log.info("[ Qna삭제 ] "+qno+"번 삭제 실패 :: 통신장애 :: ID : " + id + ", IP : " + getRemoteIp(request));
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	// 이미지 업로드
	@ResponseBody
	@RequestMapping(value = {"/imageUpload"}, method = {RequestMethod.POST})
	public ResponseEntity<Map<String, String>> imageUpload(MultipartFile[] uploadFile) {
		// 톰캣 내부 저장경로 (*리눅스), 없을 경우 폴더생성
	    String uploadFolder = "/home/apache-tomcat-9.0.76/webapps/upload";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String datePath = sdf.format(new Date()).replace("-", File.separator);
	    File uploadPath = new File(uploadFolder, datePath);
	    if (!uploadPath.exists()){
	    	uploadPath.mkdirs(); 
	    }
	    Map<String, String> response = new HashMap<>();
	    byte b;
	    int i;
	    MultipartFile[] arrayOfMultipartFile;
	    // 배열에서 한 개씩 꺼내서 저장
	    for (i = (arrayOfMultipartFile = uploadFile).length, b = 0; b < i; ) {
	    	MultipartFile upfile = arrayOfMultipartFile[b];
	    	String originalFileName = upfile.getOriginalFilename();
	    	String uniqueFileName = String.valueOf(UUID.randomUUID().toString()) + "_" + originalFileName;
	    	File saveFile = new File(uploadPath, uniqueFileName);
	    	try {
	    		upfile.transferTo(saveFile);
	        	response.put("datePath", datePath);
	        	response.put("fileName", uniqueFileName);
	    	} catch (IOException e) {
	    		e.printStackTrace();
				log.info("[ 이미지 업로드 ] Fail :: "+originalFileName+" File IOException Error");
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    	} 
	    	b++;
	    } 
	    return ResponseEntity.ok().body(response);
	}
	// 이미지 보기  
	@ResponseBody
	@GetMapping({"/display"})
	public ResponseEntity<byte[]> showImageGET(@RequestParam("fileName") String fileName, 
			@RequestParam("datePath") String datePath) {
		// 톰캣 내부 저장경로 (*리눅스)
		String uploadFolder = "/home/apache-tomcat-9.0.76/webapps/upload";
	    File file = new File(String.valueOf(uploadFolder) + File.separator + datePath + File.separator + fileName);
	    if (file.exists()){
	    	try {
	    		HttpHeaders headers = new HttpHeaders();
	    		headers.add("Content-type", Files.probeContentType(file.toPath()));
	    		byte[] fileBytes = FileCopyUtils.copyToByteArray(file);
	    		return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
	    	} catch (IOException e) {
	    		e.printStackTrace();
				log.info("[ 이미지 보기 ] Fail :: "+fileName+" File IOException Error");
	    	} 
	    } 
	    return ResponseEntity.notFound().build();
	}
	// 댓글 쓰기
	@RequestMapping(value = {"/replyAdd"}, method = {RequestMethod.POST})
	public String reply_add(ReplyDto rd, @RequestParam String pageNum, HttpServletRequest request, 
			HttpSession session) throws Exception {
	    int result = service.ReplyAdd(rd);
	    if (result == 1) {
			log.info("[ 댓글 쓰기 ] 작성 성공 :: ID : "+rd.getId()+", rno : " + rd.getRno() + ", IP : " + getRemoteIp(request));
	    } else {
	    	log.info("[ 댓글 쓰기 ] 작성 실패 :: ID : "+rd.getId()+", rno : " + rd.getRno() + ", IP : " + getRemoteIp(request));
	    } 
	    return "redirect:/qna_view?qno=" + rd.getQno() + "&pageNum=" + pageNum;
	}
	// 댓글 수정
	@ResponseBody
	@RequestMapping(value = {"/ReplyUpdate"}, method = {RequestMethod.POST})
	public int ReplyUpdate(int rno, String id, String text, ReplyDto rd, Model model) throws Exception {
	    int result = 0;
	    String rid = service.getReplyId(rno);
	    if (!rid.equals(id)) {
	    	log.info("[ 댓글 수정 ] "+rno+"번 댓글 수정 실패 :: 작성자 아님, ID : "+ id);
	      	model.addAttribute("result", Integer.valueOf(result));
	      	return result;
	    } 
	    rd.setRno(rno);
	    rd.setRcontent(text);
	    result = service.ReplyUpdate(rd);
	    if (result == 1) {
	    	log.info("[ 댓글 수정 ] "+rno+"번 댓글 수정 성공, ID : "+ id);
	    } else {
	      result = 2;
	    	log.info("[ 댓글 수정 ] "+rno+"번 댓글 수정 실패 :: 통신장애, ID : "+ id);
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	// 댓글 삭제
	@ResponseBody
	@RequestMapping(value = {"/ReplyDelete"}, method = {RequestMethod.POST})
	public int ReplyDelete(int rno, String id, Model model) throws Exception {
	    int result = 0;
	    String rid = service.getReplyId(rno);
	    if (!rid.equals(id)) {
	    	log.info("[ 댓글 삭제 ] "+rno+"번 댓글 삭제 실패 :: 작성자 아님, ID : "+ id);
	    	model.addAttribute("result", Integer.valueOf(result));
	    	return result;
	    } 
	    result = service.ReplyDelete(rno);
	    if (result == 1) {
	    	log.info("[ 댓글 삭제 ] "+rno+"번 댓글 삭제 성공, ID : "+ id);
	    } else {
	    	result = 2;
	    	log.info("[ 댓글 삭제 ] "+rno+"번 댓글 삭제 실패 :: 통신장애, ID : "+ id);
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
}
