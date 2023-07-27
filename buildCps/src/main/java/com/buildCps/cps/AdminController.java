package com.buildCps.cps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.NoticeDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;
import com.buildCps.cps.service.AdminService;
import com.buildCps.cps.service.PagingPgm;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	
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
	// 관리자 회원리스트
	@RequestMapping({"/admin_userList"})
	public String admin_userList(String pageNum, MemberDto md, PayDto payd, PcDto pcd, HttpSession session, 
			Model model, HttpServletRequest request) throws Exception {
	    if (session.getAttribute("admin") != "pdi") {
	      return "redirect:/";
	    } 
		log.info("[ 회원리스트 ] IP : " + getRemoteIp(request));
	    int rowPerPage = 10;
	    if (pageNum == null || pageNum.equals("")) {
	    	pageNum = "1"; 
	    }
	    int currentPage = Integer.parseInt(pageNum);
	    int startRow = (currentPage - 1) * rowPerPage + 1;
	    int endRow = startRow + rowPerPage - 1;
	    md.setStartRow(startRow);
	    md.setEndRow(endRow);
	    List<MemberDto> members = service.getMembers(md);
	    List<PayDto> pays = service.getPays();
	    List<PcDto> pcs = service.getPcs();
	    for (MemberDto member : members) {
	      boolean hasPayment = false;
	      int regPc = 0;
	      for (PayDto payment : pays) {
	        if (member.getId().equals(payment.getId())) {
	          hasPayment = true;
	          break;
	        } 
	      } 
		      for (PcDto pc : pcs) {
		    	  if (member.getId().equals(pc.getId()))
		    		  regPc++; 
		      } 
		      member.setHasPayment(hasPayment);
		      member.setPcCount(regPc);
		      member.setPasswd("");
	    } 
	    int total = service.getMemberTotal(md);
	    int no = total - startRow + 1;
	    PagingPgm pp = new PagingPgm(total, 10, currentPage);
	    model.addAttribute("members", members);
	    model.addAttribute("no", Integer.valueOf(no));
	    model.addAttribute("pp", pp);
	    return "admin/admin_userList";
	}
	// 관리자 회원정보
	@RequestMapping({"/admin_user"})
	public String admin_user(@RequestParam("id") String id, @RequestParam("has") boolean has, HttpSession session, 
			  Model model, HttpServletRequest request) throws Exception {
		log.info("[ 회원정보 ] ID : "+id+"IP : " + getRemoteIp(request));
	    if (session.getAttribute("admin") != "pdi") {
	    	return "redirect:/";
	    } 
	    MemberDto members = service.memberLogin(id);
	    members.setPasswd("");
	    members.setHasPayment(has);
	    List<LogDto> logs = service.getLogs(id);
	    model.addAttribute("members", members);
	    model.addAttribute("logs", logs);
	    if (has) {
	    	PayDto pays = service.pays(id);
	    	model.addAttribute("pays", pays);
	    } 
	    return "admin/admin_user";
	}
	// 결제정보 등록
	@ResponseBody
	@RequestMapping(value = {"/payInsert"}, method = {RequestMethod.POST})
	public int payInsert(String id, String pay, Model model, PayDto pd) throws Exception {
	    String test = "5,2023-01-01~2024-01-01";
	    String pattern = "^\\d+,[\\d]{4}-[\\d]{2}-[\\d]{2}~[\\d]{4}-[\\d]{2}-[\\d]{2}$";
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(pay);
	    if (!matcher.matches()) {
			log.info("[ 결제정보등록 ] [ Fail - 형식이 잘못됨] test : "+ test + ", pattern : "+ pattern + ", Matched : "+ matcher.matches());
	      return 9;
	    } 
	    String[] as = pay.split(",");
	    int amount = Integer.parseInt(as[0]);
	    String[] ds = as[1].split("~");
	    String startStr = ds[0];
	    String endStr = ds[1];
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date start = dateFormat.parse(startStr);
	    Date end = dateFormat.parse(endStr);
	    pd.setId(id);
	    pd.setPc_amount(amount);
	    pd.setStartdate(start);
	    pd.setEnddate(end);
	    int result = service.payInsert(pd);
	    if (result == 1) {
			log.info("[ 결제정보등록 ] [ OK ]");
	    } else {
	    	log.info("[ 결제정보등록 ] [ Fail ]");
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	// 결제정보 수정
	@ResponseBody
	@RequestMapping(value = {"/payUpdate"}, method = {RequestMethod.POST})
	  public int payUpdate(String id, String pay, Model model, PayDto pd) throws Exception {
	    String test = "5,2023-01-01~2024-01-01";
	    String pattern = "^\\d+,[\\d]{4}-[\\d]{2}-[\\d]{2}~[\\d]{4}-[\\d]{2}-[\\d]{2}$";
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(pay);
	    if (!matcher.matches()) {
			log.info("[ 결제정보수정 ] [ Fail - 형식이 잘못됨] test : "+ test + ", pattern : "+ pattern + ", Matched : "+ matcher.matches());
			return 9;
	    } 
	    String[] as = pay.split(",");
	    int amount = Integer.parseInt(as[0]);
	    String[] ds = as[1].split("~");
	    String startStr = ds[0];
	    String endStr = ds[1];
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date start = dateFormat.parse(startStr);
	    Date end = dateFormat.parse(endStr);
	    pd.setId(id);
	    pd.setPc_amount(amount);
	    pd.setStartdate(start);
	    pd.setEnddate(end);
	    int result = service.payUpdate(pd);
	    if (result == 1) {
	    	log.info("[ 결제정보수정 ] [ OK ]");
	    } else {
	    	log.info("[ 결제정보수정 ] [ Fail ]");
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	// 결제정보 삭제
	@ResponseBody
	@RequestMapping(value = {"/payDelete"}, method = {RequestMethod.POST})
	public int payDelete(String id, Model model) throws Exception {
	    int result = service.payDelete(id);
	    if (result == 1) {
	    	log.info("[ 결제정보삭제 ] [ OK ]");
	    } else {
	    	log.info("[ 결제정보삭제 ] [ Fail ]");
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	  
	// 관리자, CPS LOG 전체기록 리스트
	@RequestMapping({"/admin_logList"})
	public String admin_logList(String pageNum, MemberDto md, PayDto payd, PcDto pcd, LogDto ld, 
			HttpSession session, Model model, HttpServletRequest request) throws Exception {
		if (session.getAttribute("admin") != "pdi") {
			return "redirect:/";
		} 
	    int rowPerPage = 10;
	    if (pageNum == null || pageNum.equals("")) {	    	
	    	pageNum = "1"; 
	    }
	    int currentPage = Integer.parseInt(pageNum);
	    int startRow = (currentPage - 1) * rowPerPage + 1;
	    int endRow = startRow + rowPerPage - 1;
	    ld.setStartRow(startRow);
	    ld.setEndRow(endRow);
	    List<LogDto> logs = service.logAll(ld);
	    List<PayDto> pays = service.getPays();
	    List<PcDto> pcs = service.getPcs();
	    for (LogDto log : logs) {
	    	boolean hasPayment = false;
	    	int regPc = 0;
	    	for (PayDto payment : pays) {
	    		if (log.getId().equals(payment.getId())) {
	    			hasPayment = true;
	    			break;
	    		} 
	    	} 
	    	for (PcDto pc : pcs) {
	    		if (log.getId().equals(pc.getId()))
	    			regPc++; 
	    	} 
	    	log.setHasPayment(hasPayment);
	    	log.setPcCount(regPc);
	    } 
	    int total = service.getLogTotal(ld);
	    int no = total - startRow + 1;
	    PagingPgm pp = new PagingPgm(total, 10, currentPage);
	    model.addAttribute("logs", logs);
	    model.addAttribute("no", Integer.valueOf(no));
	    model.addAttribute("pp", pp);
	    
		log.info("[ admin_cpsLogList ] IP : " + getRemoteIp(request));
		
	    return "admin/admin_logList";
	}
	// 공지글 업데이트
	@RequestMapping({"/admin_noticeUpdate"})
	public String admin_noticeUpdate(NoticeDto nd, HttpSession session, Model model, 
			HttpServletRequest request) throws Exception {
		if (session.getAttribute("admin") != "pdi") {
			return "redirect:/";
		} 
	    NoticeDto list = service.getNotice();
	    model.addAttribute("list", list);
	    return "admin/admin_noticeUpdate";
	}
	// 공지글 업데이트 완료
	@RequestMapping({"admin_noticeUpdate_action"})
	public String admin_noticeUpdate_action(HttpServletRequest request, NoticeDto nd, 
			HttpSession session, Model model) throws Exception {
	    if (session.getAttribute("admin") != "pdi") {
	    	return "redirect:/";
	    } 
	    int result = service.noticeUpdate(nd);
	    if (result == 1) {
	    	result = 1;
			log.info("[ 공지 수정 ] :: 수정 성공, IP : " + getRemoteIp(request));
	    } else {
	    	result = 2;
			log.info("[ 공지 수정 ] :: 수정 실패, IP : " + getRemoteIp(request));
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return "admin/admin_noticeUpdate_action";
	  }
	// 공지글 View
	@RequestMapping({"/noticeView"})
	public String noticeView(@RequestParam(required = false, defaultValue = "99999") int notice, NoticeDto nd, 
			  HttpServletResponse response, HttpSession session, Model model, HttpServletRequest request) throws Exception {
		MemberDto get = (MemberDto)session.getAttribute("md");
	    if (get != null) {
	      String id = get.getId();
	      model.addAttribute("id", id);
	    }
	    boolean check = true;
	    Cookie[] cookies = request.getCookies();
	    String noticeParam = request.getParameter("notice");
	    if (cookies != null) {
	    	byte b;
	    	int i;
	    	Cookie[] arrayOfCookie;
	    	for (i = (arrayOfCookie = cookies).length, b = 0; b < i; ) {
	    		Cookie cookie = arrayOfCookie[b];
	    		if (cookie.getName().equals("visit_cookie")) {
	    			check = false;
	    			String cookieValue = cookie.getValue();
	    			if (cookieValue != null && noticeParam != null && !cookieValue.contains(noticeParam)) {
	    				cookie.setValue(String.valueOf(cookie.getValue()) + "_" + request.getParameter("notice"));
	    				cookie.setMaxAge(1200);
	    				response.addCookie(cookie);
	    				service.noticeCountPlus();
	    			} 
	    			break;
	    		}	    		
	    		b++;
	    	} 
	    } 
	    if (check) {
			log.info("[ 공지 View ] 쿠키 or visit_쿠키 없음" + getRemoteIp(request));
	    	Cookie newCookie = new Cookie("visit_cookie", request.getParameter("notice"));
	    	newCookie.setMaxAge(1200);
	    	response.addCookie(newCookie);
	    	service.noticeCountPlus();
	    }else {
			log.info("[ 공지 View ] 쿠키 확인, IP : " + getRemoteIp(request));
	    }
	    NoticeDto noticeview = service.getNotice();
	    model.addAttribute("noticeview", noticeview);
	    return "home/noticeView";
	}
	
}
