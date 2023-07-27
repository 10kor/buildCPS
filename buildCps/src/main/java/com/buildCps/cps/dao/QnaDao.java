package com.buildCps.cps.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.buildCps.cps.dto.QnaDto;
import com.buildCps.cps.dto.ReplyDto;

@Repository
public class QnaDao {
	
  @Autowired
  private SqlSessionTemplate sst;
  
	public int getQnaTotal(QnaDto qd) {
	    return ((Integer)sst.selectOne("qnaXML.getQnaTotal", qd));
	}
	public List<QnaDto> qnaList(QnaDto qd) {
	    return sst.selectList("qnaXML.qnaList", qd);
	}
	public int getCountReply(int qno) {
	    return ((Integer)sst.selectOne("replyXML.getCountReply", qno));
	}
	public int qnaAdd(QnaDto qd) {
	    return sst.insert("qnaXML.qnaAdd", qd);
	}
	public int qnaCountPlus(int qno) {
	    return sst.update("qnaXML.qnaCountPlus",qno);
	}
	public List<QnaDto> qnaView(int bno) {
	    return sst.selectList("qnaXML.qnaView", bno);
	}
	public QnaDto qnaUpdateSelect(int qno) {
	    return (QnaDto)sst.selectOne("qnaXML.qnaUpdateSelect", qno);
	}
	public int qnaUpdate(QnaDto qd) {
	    return sst.update("qnaXML.qnaUpdate", qd);
	}
	public int qnaDelete(int qno) {
	    return sst.update("qnaXML.qnaDelete", qno);
	}
	 public String qnaPasswdCk(int qno) {
	    return (String)sst.selectOne("qnaXML.qnaPasswdCk", qno);
	}
	public int ReplyAdd(ReplyDto rd) {
	    return sst.insert("replyXML.ReplyAdd", rd);
	}
	public String getReplyId(int rno) {
	    return (String)sst.selectOne("replyXML.getReplyId", rno);
	}
	public List<ReplyDto> ReplyList(int qno) {
	    return sst.selectList("replyXML.ReplyList", qno);
	}
	public int ReplyUpdate(ReplyDto rd) {
	    return sst.update("replyXML.ReplyUpdate", rd);
	}
	public int ReplyDelete(int rno) {
	    return sst.update("replyXML.ReplyDelete", rno);
	}
  
}
