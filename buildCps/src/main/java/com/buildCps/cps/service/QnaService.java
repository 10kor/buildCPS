package com.buildCps.cps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildCps.cps.dao.QnaDao;
import com.buildCps.cps.dto.QnaDto;
import com.buildCps.cps.dto.ReplyDto;

@Service
public class QnaService {
	
	@Autowired
	private QnaDao dao;
  
	public int getQnaTotal(QnaDto qd) {
		return dao.getQnaTotal(qd);
	}
	public List<QnaDto> qnaList(QnaDto qd) {
		return dao.qnaList(qd);
	}
	public int getCountReply(int qno) {
		return dao.getCountReply(qno);
	}
	public int qnaAdd(QnaDto qd) {
		return dao.qnaAdd(qd);
	}
	public int qnaCountPlus(int qno) {
		return dao.qnaCountPlus(qno);
	}
	public List<QnaDto> qnaView(int qno) {
		return dao.qnaView(qno);
	}
	public QnaDto qnaUpdateSelect(int qno) {
		return dao.qnaUpdateSelect(qno);
	}
	public int qnaUpdate(QnaDto qd) {
		return dao.qnaUpdate(qd);
	}
	public int qnaDelete(int qno) {
		return dao.qnaDelete(qno);
	}
	public String qnaPasswdCk(int qno) {
		return dao.qnaPasswdCk(qno);
	}
	public int ReplyAdd(ReplyDto rd) {
		return dao.ReplyAdd(rd);
	}
	public String getReplyId(int rno) {
		return dao.getReplyId(rno);
	}
	public List<ReplyDto> ReplyList(int qno) {
		return dao.ReplyList(qno);
	}
	public int ReplyUpdate(ReplyDto rd) {
		return dao.ReplyUpdate(rd);
	}
	public int ReplyDelete(int rno) {
		return dao.ReplyDelete(rno);
	}
 
}
