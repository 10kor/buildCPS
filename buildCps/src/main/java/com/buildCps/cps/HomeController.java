package com.buildCps.cps;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;
import com.buildCps.cps.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	private HomeService service;
	
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
	// Home Access
	@RequestMapping(value = {"/"}, method = {RequestMethod.GET})
	public String home(HttpSession session, Locale locale, HttpServletRequest request) {
		log.info("[ Home Access ] 지역 : " + locale + ", IP : " + getRemoteIp(request));
		session.invalidate();
		return "home/login";
	}
	// 회원가입 Form
	@RequestMapping({"/join"})
	public String join() {
		return "home/join";
	}
	// ID 중복 확인
	@ResponseBody
	@RequestMapping(value = {"/idcheck"}, method = {RequestMethod.POST})
	public int idcheck(String id, Model model) throws Exception {
		int result = service.idcheck(id);
		model.addAttribute("result", Integer.valueOf(result));
		return result;
	}
	// 회원가입 Result
	@RequestMapping(value = {"/join_action"}, method = {RequestMethod.POST})
	public String join_action(MemberDto md, HttpServletRequest request, Model model) throws Exception {
		log.info("[ 가입양식제출 ] " + md);
		int result = service.memberSign(md);
		model.addAttribute("result", Integer.valueOf(result));
		if (result == 1) {
			log.info("[ 회원가입 성공 ] form : " + md);
		} else {
			log.info("[ 회원가입 실패 ] form : " + md);
		} 
		return "home/join_action";
	}
	// 회원 로그인
	@RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
	public String admin_login_action(@RequestParam("id") String id, @RequestParam("passwd") String passwd,
			HttpSession session, Model model, HttpServletRequest request) throws Exception {
		if (request.getSession(false) == null) {
			return "redirect:/";
		} 
	    if (id.equals("admin") && passwd.equals("*ednc*")) {
	    	log.info("[ 로그인 ] 관리자 로그인, IP : " + getRemoteIp(request));
	    	MemberDto memberDto = service.memberLogin(id);
	    	session.setAttribute("admin", "pdi");
	    	session.setAttribute("md", memberDto);
	    	return "redirect:/admin_userList";
	    } 
	    int result = 0;
	    MemberDto md = service.memberLogin(id);
	    if (md == null) {
	    	result = 1;
	    	model.addAttribute("result", Integer.valueOf(result));
	    	return "home/login_result";
	    } 
	    if (md.getPasswd().equals(passwd)) {
	    	log.info("[ 로그인 ] ID : "+id+", IP : " + getRemoteIp(request));
	    	md.setPasswd("");
	    	session.setAttribute("md", md);
	    	return "redirect:/mypage";
	    } 
	    result = 2;
	    model.addAttribute("result", Integer.valueOf(result));
	    return "home/login_result";
	  }
	// 마이페이지
	@RequestMapping({"/mypage"})
	public String mypage(MemberDto md, HttpServletRequest request, Model model, HttpSession session) throws Exception {
		if (request.getSession(false) == null) {
			return "redirect:/";
		} 
		MemberDto members = (MemberDto)session.getAttribute("md");
		String id = members.getId();
		PayDto pays = service.pays(id);
		List<PcDto> pcs = service.pcs(id);
		model.addAttribute("members", members);
		model.addAttribute("pays", pays);
		model.addAttribute("pcs", pcs);
		log.info("[ 마이페이지 ] ID : "+id+", IP : " + getRemoteIp(request));
		return "home/mypage";
	}
	// 회원정보 수정
	@RequestMapping({"/memberupdate"})
	public String memberupdate(MemberDto md, HttpServletRequest request, HttpSession session, Model model) throws Exception {
	    if (request.getSession(false) == null) {
	      return "redirect:/";
	    } 
	    MemberDto members = (MemberDto)session.getAttribute("md");
	    model.addAttribute("members", members);
	    return "home/memberupdate";
	}
	// 회원정보 수정 완료
	@RequestMapping(value = {"/update_action"}, method = {RequestMethod.POST})
	public String update_action(MemberDto md, HttpServletRequest request, Model model, HttpSession session) throws Exception {
		if (request.getSession(false) == null) {
			return "redirect:/";
		} 
		int result = 0;
		String id = md.getId();
		String passwd = md.getPasswd();
		String passwdcheck = service.passwdCheck(id);
		if (passwdcheck.equals(passwd)) {
			int memberupdate = service.memberUpdate(md);
			if (memberupdate == 1) {
				log.info("[ 회원정보수정 ] [ OK ] ID : "+id+", IP : " + getRemoteIp(request));
				md = service.memberLogin(id);
				if (md == null) {
					log.info("[ 회원정보수정 ] [ Fail ] ID : "+id+", IP : " + getRemoteIp(request));
					session.invalidate();
					return "/";
				} 
				md.setPasswd("");
				session.setAttribute("md", md);
				return "redirect:/mypage";
			} 
			log.info("[ 회원정보수정 ] [ Fail ] 업데이트 실패 ");
			result = 1;
			model.addAttribute("result", Integer.valueOf(result));
			return "home/update_result";
		} 
		System.out.println("    >> Fail :: 패스워드 틀림");
		result = 2;
		model.addAttribute("result", Integer.valueOf(result));
		return "home/update_result";
	}
	// pc등록 취소
	@ResponseBody
	@RequestMapping(value = {"/pcCancel"}, method = {RequestMethod.POST})
	public int pcCancel(String hostName, String memberId, Model model, PcDto pd) throws Exception {
		pd.setId(memberId);
	    pd.setHost_name(hostName);
	    int result = service.pcCancel(pd);
	    if (result == 1) {
			log.info("[ PC등록 취소 ] [ OK ] 취소 성공 ");
	    } else {
	    	log.info("[ PC등록 취소 ] [ Fail ] 취소 실패 ");
	    } 
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	  }
	// 회원탈퇴
	@ResponseBody
	@RequestMapping(value = {"/memberDrop"}, method = {RequestMethod.POST})
	public int memberDrop(HttpSession session, HttpServletRequest request, String memberId, String passwd, Model model, MemberDto md) throws Exception {
		String id = memberId;
	    int result = 2;
	    String passwdcheck = service.passwdCheck(id);
	    if (passwdcheck.equals(passwd)) {
	    	md.setId(memberId);
	    	md.setPasswd(passwd);
	    	result = service.memberDrop(md);
	    	if (result == 1) {
	    		log.info("[ 회원탈퇴 ] [ OK ] 탈퇴 성공 ");
	    		session.invalidate();
	    		result = 1;
	    		model.addAttribute("result", Integer.valueOf(result));
	    		return result;
	    	} 
	    	result = 0;
	    	log.info("[ 회원탈퇴 ] [ Fail ] 탈퇴 실패 :: MyBatis Error ");
	    	model.addAttribute("result", Integer.valueOf(result));
	    	return result;
	    } 
	    log.info("[ 회원탈퇴 ] [ Fail ] 탈퇴 실패 :: 비밀번호 틀림 ");
	    model.addAttribute("result", Integer.valueOf(result));
	    return result;
	}
	// 로그아웃
	@RequestMapping({"/logout"})
	public String logout(HttpSession session, HttpServletRequest request) throws Exception {
		try {
			if (request.getSession(false) == null) {
				return "redirect:/";
			} 
			if (session.getAttribute("admin") == "pdi") {
		    	log.info("[ 관리자 로그아웃 ] 접속IP : " + getRemoteIp(request));
				session.invalidate();
				return "redirect:/";
			} 
			MemberDto members = (MemberDto)session.getAttribute("md");
			String id = members.getId();
	    	log.info("[ "+id+" 로그아웃 ] 접속IP : " + getRemoteIp(request));
			session.invalidate();
			return "redirect:/";
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/";
		} 
	}
}
