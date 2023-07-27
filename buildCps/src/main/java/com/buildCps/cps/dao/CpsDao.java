package com.buildCps.cps.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Repository
public class CpsDao {
  
	@Autowired
	private SqlSessionTemplate sst;
  
	public int log_insert(LogDto ld) {
		return sst.insert("logXML.logInsert", ld);
	}
  
	public List<PayDto> pay_data(String client) {
		return sst.selectList("payXML.paydata", client);
	}
  
	public int pc_isit(PcDto pd) {
		return sst.selectOne("pcXML.pcisit", pd);
	}
  
	public int pc_cancel(PcDto pd) {
		return sst.selectOne("pcXML.pccancel", pd);
	}
  
	public int pc_reg(String client) {
		return sst.selectOne("pcXML.pcreg", client);
	}
  
	public int pc_register(PcDto pd) {
		return sst.insert("pcXML.pcregister", pd);
	}
  
	public int idcheck(String id) {
		return sst.selectOne("memberXML.idcheck", id);
	}
  
}
