package com.buildCps.cps;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buildCps.cps.dto.BfileDto;
import com.buildCps.cps.service.BfileService;
import com.buildCps.cps.service.PagingPgm;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BfileController {
	
	@Autowired
	private BfileService service;
	
	// Get IP
	private String getRemoteIp(HttpServletRequest request) {
		String remoteIp = "";
		try {
			String[] headersToCheck = { "X-Forwarded-For", "Forwarded", "Proxy-Client-IP", 
					"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = headersToCheck).length, b = 0; b < i; ) {
				String header = arrayOfString1[b];
				remoteIp = request.getHeader(header);
				if (remoteIp != null && remoteIp.length() > 0 && !"unknown".equalsIgnoreCase(remoteIp)) {
					break; 
				}
				b++;
			} 
			if (remoteIp == null || remoteIp.length() == 0 || "unknown".equalsIgnoreCase(remoteIp)) {
				remoteIp = request.getRemoteAddr(); 
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} 
		return remoteIp;
	}
	// B파일 리스트
	@RequestMapping({"/bfileList"})
	public String bfileList(String pageNum, BfileDto bd, HttpServletRequest request, HttpSession session, Model model) throws Exception {
	    if (session.getAttribute("admin") != "pdi") {
	     	return "redirect:/";
	    } 
	    int rowPerPage = 15;
	    if (pageNum == null || pageNum.equals("")){
	    	pageNum = "1"; 
	    }
	    int currentPage = Integer.parseInt(pageNum);
	    int total = service.getBfileTotal(bd);
	    int startRow = (currentPage - 1) * rowPerPage + 1;
	    int endRow = startRow + rowPerPage - 1;
	    PagingPgm pp = new PagingPgm(total, rowPerPage, currentPage);
	    bd.setStartRow(startRow);
	    bd.setEndRow(endRow);
	    int no = total - startRow + 1;
	    List<BfileDto> fileList = service.fileList(bd);
		log.info("[ B파일 리스트 ] IP : " + getRemoteIp(request));
	    model.addAttribute("fileList", fileList);
	    model.addAttribute("no", Integer.valueOf(no));
	    model.addAttribute("pp", pp);
	    return "admin/admin_bfileList";
	}
	// B파일 업로드
	@ResponseBody
	@RequestMapping(value = {"/bfileUpload"}, method = {RequestMethod.POST})
	public int bfileUpload(MultipartFile bfile, HttpServletRequest request, HttpSession session) throws Exception {
		int result = 0;
	    if (session.getAttribute("admin") != "pdi") {
	      	return result;
	    } 
	    try {
	    	String[] units = { "bytes", "KB", "MB" };
		    long size = bfile.getSize();
		    int unitIndex = (int)(Math.log10(size) / 3.0D);
		    double formattedSize = size / Math.pow(1024.0D, unitIndex);
		    String bsize = String.format("%.2f %s", new Object[] { Double.valueOf(formattedSize), units[unitIndex] });
		    Map<String, Object> map = new HashMap<>();
		    map.put("bname", bfile.getOriginalFilename());
		    map.put("bfile", bfile.getBytes());
		    map.put("bsize", bsize);
		    result = service.fileUpload(map);
		    if (result == 1) {
				log.info("[ B파일 업로드 ] [OK] 파일명 : " + bfile.getOriginalFilename() + ", 용량 : " + bsize + ", IP : " + getRemoteIp(request));
		    } else {
				log.info("[ B파일 업로드 ] [Fail - 통신장애] :: 파일명 :" + bfile.getOriginalFilename() +"IP : " + getRemoteIp(request));
		    } 
	    } catch (Exception e) {
			log.info("[ B파일 업로드 ] [Fail - IOException Error] :: 파일명 : " + bfile.getOriginalFilename() +"IP : " + getRemoteIp(request));
	    	e.printStackTrace();
	    } 
	    return result;
	}
	// B파일 아이디할당  
	@ResponseBody
	@RequestMapping(value = {"/fileIdAdd"}, method = {RequestMethod.POST})
	public int fileIdAdd(@RequestParam("bno") int bno, @RequestParam("id") String id, HttpServletRequest request, HttpSession session) throws Exception {
		int result = 0;
	    if (session.getAttribute("admin") != "pdi") {
	    	return result;
	    } 
	    try {
	    	Map<String, Object> map = new HashMap<>();
	    	map.put("id", id);
	      	map.put("bno", Integer.valueOf(bno));
	      	result = service.fileId(map);
	      	if (result == 1) {
				log.info("[ B파일 아이디할당 ] [OK] " + bno + "번 파일," + id + " 아이디 할당, IP : " + getRemoteIp(request));
	      	} else {
	      		log.info("[ B파일 아이디할당 ] [Fail - 통신장애] " + bno + "번 파일," + id + " 아이디 할당, IP : " + getRemoteIp(request));
	      	} 
	    } catch (Exception e) {
	    	log.info("[ B파일 아이디할당 ] [Fail - IOException Error] " + bno + "번 파일," + id + " 아이디 할당, IP : " + getRemoteIp(request));
	      	e.printStackTrace();
	    } 
	    return result;
	}
	// B파일 다운로드
	@ResponseBody
	@RequestMapping(value = {"/bfileDownload"}, method = {RequestMethod.GET})
	public ResponseEntity<byte[]> bfileDownload(int bno, BfileDto bd, HttpServletRequest request) throws Exception {
		log.info("[ B파일 다운로드 ] " + bno + "번 파일 다운로드, IP : " + getRemoteIp(request));
	    BfileDto file = service.fileDownload(bno);
	    byte[] fileContent = file.getBfile();
	    String fileName = file.getBname();
	    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
	      .filename(fileName, StandardCharsets.UTF_8)
	      .build();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDisposition(contentDisposition);
	    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
	// B파일 삭제
	@ResponseBody
	@RequestMapping(value = {"/bfileDelete"}, method = {RequestMethod.POST})
	public int bfileDelete(int bno, HttpServletRequest request, HttpSession session) throws Exception {
		int result = 0;
		if (session.getAttribute("admin") != "pdi") {
			return result;
	    } 
	    try {
	    	result = service.fileDelete(bno);
	      	if (result == 1) {
				log.info("[ B파일 삭제 ] [OK] " + bno + "번 파일 삭제, IP : " + getRemoteIp(request));
	      	} else {
	      		log.info("[ B파일 아이디할당 ] [Fail - 통신장애] " + bno + "번 파일 삭제, IP : " + getRemoteIp(request));
	      	} 
	    } catch (Exception e) {
	    	log.info("[ B파일 아이디할당 ] [Fail - IOException Error] " + bno + "번 파일 삭제, IP : " + getRemoteIp(request));
	      	e.printStackTrace();
	    } 
	    return result;
	}
}
