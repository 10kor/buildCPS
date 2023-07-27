package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ReplyDto {
	
  private int rno;  
  private int qno;  
  private String id;  
  private String rcontent;  
  private Date rdate;  
  private Date rupdate;  
  private Date rdeldate;
  
  private int startRow;  
  private int endRow;
  
}
