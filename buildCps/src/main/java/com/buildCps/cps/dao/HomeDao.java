package com.buildCps.cps.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Repository
public class HomeDao {
  
	@Autowired
	private SqlSessionTemplate sst;
  
	public int idcheck(String id) {
	    return ((Integer)sst.selectOne("memberXML.idcheck", id)).intValue();
	}
	public int memberSign(MemberDto md) {
	    return sst.insert("memberXML.memberSign", md);
	}
	public MemberDto memberLogin(String id) {
	    return (MemberDto)sst.selectOne("memberXML.memberLogin", id);
	}
	public PayDto pays(String id) {
	    return (PayDto)sst.selectOne("payXML.pays", id);
	}
	public List<PcDto> pcs(String id) {
	    return sst.selectList("pcXML.pcs", id);
	}
	public String passwdCheck(String id) {
	    return (String)sst.selectOne("memberXML.passwdCheck", id);
	}
	public int memberUpdate(MemberDto md) {
	    return sst.update("memberXML.memberUpdate", md);
	}
	public int pcCancel(PcDto pd) {
	    return sst.update("pcXML.pcCancel", pd);
	}
	public int memberDrop(MemberDto md) {
	    return sst.update("memberXML.memberDrop", md);
	}
}
