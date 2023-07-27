package com.buildCps.cps.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildCps.cps.dao.BfileDao;
import com.buildCps.cps.dto.BfileDto;

@Service
public class BfileService {
	
	@Autowired
	private BfileDao dao;
  
	public int getBfileTotal(BfileDto bd) {
	    return dao.getBfileTotal(bd);
	}
	  
	public List<BfileDto> fileList(BfileDto bd) {
	    return dao.fileList(bd);
	}
	  
	public int fileUpload(Map<String, Object> map) {
	    return dao.fileUpload(map);
	}
	  
	public int fileId(Map<String, Object> map) {
	    return dao.fileId(map);
	}
	  
	public BfileDto fileDownload(int bno) {
	    return dao.fileDownload(bno);
	}
	  
	public int fileDelete(int bd) {
	    return dao.fileDelete(bd);
	}
}
