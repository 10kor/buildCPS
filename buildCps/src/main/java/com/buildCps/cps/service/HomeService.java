package com.buildCps.cps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildCps.cps.dao.HomeDao;
import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Service
public class HomeService {
	
	@Autowired
	private HomeDao dao;
  
	public int idcheck(String id) {
	    return dao.idcheck(id);
	}
	public int memberSign(MemberDto md) {
	    return dao.memberSign(md);
	}
	public MemberDto memberLogin(String id) {
	    return dao.memberLogin(id);
	}
	public PayDto pays(String id) {
		return dao.pays(id);
	}
	public List<PcDto> pcs(String id) {
	    return dao.pcs(id);
	}
	public String passwdCheck(String id) {
	    return dao.passwdCheck(id);
	}
	public int memberUpdate(MemberDto md) {
	    return dao.memberUpdate(md);
	}
	public int pcCancel(PcDto pd) {
	    return dao.pcCancel(pd);
	}
	public int memberDrop(MemberDto md) {
	    return dao.memberDrop(md);
	}
}
