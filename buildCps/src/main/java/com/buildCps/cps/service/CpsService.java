package com.buildCps.cps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildCps.cps.dao.CpsDao;
import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Service
public class CpsService {
	
	@Autowired
	private CpsDao dao;
  
	public int idcheck(String id) {
	return dao.idcheck(id);
	}
	public int log_insert(LogDto ld) {
	    return dao.log_insert(ld);
	}
	public List<PayDto> pay_data(String client) {
		return dao.pay_data(client);
	}
	public int pc_isit(PcDto pd) {
		return dao.pc_isit(pd);
	}
	public int pc_cancel(PcDto pd) {
		return dao.pc_cancel(pd);
	}
	public int pc_reg(String client) {
		return dao.pc_reg(client);
	}	  
	public int pc_register(PcDto pd) {
		return dao.pc_register(pd);
	}
	
}
