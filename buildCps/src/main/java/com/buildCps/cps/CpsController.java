package com.buildCps.cps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;
import com.buildCps.cps.service.CpsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CpsController {
	
	@Autowired
	private CpsService service;
	
	// Get IP
	private String getRemoteIp(HttpServletRequest request) {
		String remoteIp = "";
		try {
			String[] headersToCheck = { "X-Forwarded-For", "Forwarded", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
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
	// 사용자 인식, 결제정보, pc등록정보 확인
	@RequestMapping(value = {"/load"}, method = {RequestMethod.GET})
	public String load(@RequestParam("client") String client, @RequestParam("name") String name, @RequestParam("host") String host, LogDto ld, PcDto pd, HttpServletRequest request) throws Exception {
		
		log.info("[ Cps Load ] Client : " + client + ", IP : " + getRemoteIp(request) + ", UserName : " + name +", HostName : " + host);

	    ld.setId(client);
	    ld.setUser_name(name);
	    ld.setHost_name(host);
	    ld.setIp_address(getRemoteIp(request));
	    
	    // 1. 회원존재 확인
	    int idcheck = service.idcheck(client);
	    if (idcheck == 0) {
	      ld.setStatus(1);
	      int i = service.log_insert(ld);
	      log.info("[ Cps Load ] ERROR(1) :: 존재하지 않는 회원 >> log_result : " + i);
	      return "error";
	    } 
	    
	    // 2. 결제기간 확인
	    List<PayDto> pay_data = service.pay_data(client);
	    int payResult = 0;
	    int payAmount = 0;
	    Date currentDate = new Date();
	    for (PayDto payDto : pay_data) {
	      payAmount = payDto.getPc_amount();
	      Date startDate = payDto.getStartdate();
	      Date endDate = payDto.getEnddate();
	      payResult = (currentDate.after(startDate) && currentDate.before(endDate)) ? 1 : 0;
	    } 
	    if (payResult == 0) {
	      ld.setStatus(2);
	      int i = service.log_insert(ld);
	      log.info("[ Cps Load ] ERROR(2) :: 결제기간을 벗어남 >> log_result : " + i);
	      return "error";
	    } 
	    if (payResult == 1) {
	    	log.info("[ Cps Load ] PAY PASS :: pay_result & amount : " + payResult + "(ok) & " + payAmount); 
	    }
	    
	    // 3. 취소이력 확인
	    pd.setId(client);
	    pd.setHost_name(host);
	    int isit = service.pc_isit(pd);
	    if (isit == 1) {
	    	int pc_cancel = service.pc_cancel(pd);
	    	if (pc_cancel == 1) {
		        ld.setStatus(3);
		        int i = service.log_insert(ld);
		        log.info("[ Cps Load ] ERROR(3) :: 취소된 pc >> log_result : " + i);
		        return "error";
	    	} 
	    	if (pc_cancel == 0) {
		        ld.setStatus(0);
		        int i = service.log_insert(ld);
		        log.info("[ Cps Load ] [ OK ] 등록된 pc >> log_result : " + i);
		        String go = String.valueOf(client) + "/onopen";
		        return go;
	    	} 
	    } 
	    
	    // 4. 미등록 PC 확인 후 등록
	    int pc_reg = service.pc_reg(client);
	    if (pc_reg < payAmount) {
	      int pc_register = service.pc_register(pd);
	      ld.setStatus(9);
	      int i = service.log_insert(ld);
	      log.info("[ Cps Load ] [ OK ] 등록 후 진행 >>  pc_register : " + pc_register + " // log_result : " + i);
	      String go = String.valueOf(client) + "/onopen";
	      return go;
	    } 
	    ld.setStatus(4);
	    int log_result = service.log_insert(ld);
	    log.info("[ Cps Load ERROR(4) ] 등록pc수 초과 >> log_result : " + log_result);
	    return "error";
	}
	// 인증 후 Excel Make
	@RequestMapping(value = {"/make"}, method = {RequestMethod.GET})
	public String make(@RequestParam("user") String user, @RequestParam("step") String step) throws Exception {
		if (step.equals("0")) {
			log.info("[ Cps Make ] [ OK ] "+ user + "의 CPS Make 실행");
		}
		return String.valueOf(user) + "/" + step;
	}
	// 테스트 (=value 확인용)
	@RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
	public String test(@RequestParam("what") String what) throws Exception {
		log.info("test what : " + what);
		return "error";
	}
	
	
}
