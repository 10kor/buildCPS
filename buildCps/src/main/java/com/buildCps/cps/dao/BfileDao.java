package com.buildCps.cps.dao;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.buildCps.cps.dto.BfileDto;

@Repository
public class BfileDao {
	
	@Autowired
	private SqlSessionTemplate sst;
	  
	public int getBfileTotal(BfileDto bd) {
	    return sst.selectOne("bfileXML.getBfileTotal", bd);
	}
	  
	public List<BfileDto> fileList(BfileDto bd) {
	    return sst.selectList("bfileXML.fileList", bd);
	}
	  
	public int fileUpload(Map<String, Object> map) {
	    return sst.insert("bfileXML.fileUpload", map);
	}
	  
	public int fileId(Map<String, Object> map) {
	    return sst.update("bfileXML.fileId", map);
	}
	  
	public BfileDto fileDownload(int bno) {
	    return sst.selectOne("bfileXML.fileDownload", bno);
	}
	  
	public int fileDelete(int bno) {
	    return sst.delete("bfileXML.fileDelete", bno);
	}
}
