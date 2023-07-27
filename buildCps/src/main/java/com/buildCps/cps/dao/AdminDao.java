package com.buildCps.cps.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.buildCps.cps.dto.LogDto;
import com.buildCps.cps.dto.MemberDto;
import com.buildCps.cps.dto.NoticeDto;
import com.buildCps.cps.dto.PayDto;
import com.buildCps.cps.dto.PcDto;

@Repository
public class AdminDao {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	public List<MemberDto> getMembers(MemberDto md) {
		return sst.selectList("memberXML.getMembers", md);
	}
	public MemberDto memberLogin(String id) {
		return sst.selectOne("memberXML.memberLogin", id);
	}
	public PayDto pays(String id) {
		return sst.selectOne("payXML.pays", id);
	}
	public int getMemberTotal(MemberDto md) {
		return sst.selectOne("memberXML.getMemberTotal", md);
	}
	public int payInsert(PayDto pd) {
	    return sst.insert("payXML.payInsert", pd);
	}
	public int payUpdate(PayDto pd) {
	    return sst.insert("payXML.payUpdate", pd);
	}
	public int payDelete(String id) {
	    return sst.delete("payXML.payDelete", id);
	}
	public List<LogDto> logAll(LogDto ld) {
		return sst.selectList("logXML.logAll", ld);
	}
	public List<PayDto> getPays() {
		return sst.selectList("payXML.getPays");
	}
	public List<PcDto> getPcs() {
		return sst.selectList("pcXML.getPcs");
	}
	public List<LogDto> getLogs(String id) {
		return sst.selectList("logXML.getLogs", id);
	}
	public int getLogTotal(LogDto ld) {
		return sst.selectOne("logXML.getLogTotal", ld);
	}
	public NoticeDto getNotice() {
	    return sst.selectOne("noticeXML.getNotice");
	}
	public int noticeUpdate(NoticeDto nd) {
	    return sst.update("noticeXML.noticeUpdate", nd);
	}
	public int noticeCountPlus() {
		return sst.update("noticeXML.noticeCountPlus");
	}
}
