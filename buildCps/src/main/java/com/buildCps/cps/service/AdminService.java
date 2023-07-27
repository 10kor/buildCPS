package com.buildCps.cps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildCps.cps.dao.AdminDao;
import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.NoticeDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Service
public class AdminService {
	
	@Autowired
	private AdminDao dao;
  
	public List<MemberDto> getMembers(MemberDto md) {
		    return dao.getMembers(md);
	}
	public int getMemberTotal(MemberDto md) {
		    return dao.getMemberTotal(md);
	}
	 public MemberDto memberLogin(String id) {
		    return dao.memberLogin(id);
	}
	public PayDto pays(String id) {
		    return dao.pays(id);
	}
	public int payInsert(PayDto pd) {
		    return dao.payInsert(pd);
	}	  
	public int payUpdate(PayDto pd) {
		    return dao.payUpdate(pd);
	}
	public int payDelete(String id) {
		    return dao.payDelete(id);
	}
	public List<LogDto> logAll(LogDto ld) {
		return dao.logAll(ld);
	}
	public List<PayDto> getPays() {
		return dao.getPays();
	}
	public List<PcDto> getPcs() {
		return dao.getPcs();
	}
	public List<LogDto> getLogs(String id) {
		return dao.getLogs(id);
	}
	public int getLogTotal(LogDto ld) {
		return dao.getLogTotal(ld);
	}
	public NoticeDto getNotice() {
	    return dao.getNotice();
	}
	public int noticeUpdate(NoticeDto nd) {
	    return dao.noticeUpdate(nd);
	}
	public int noticeCountPlus() {
		return dao.noticeCountPlus();
	}
}
