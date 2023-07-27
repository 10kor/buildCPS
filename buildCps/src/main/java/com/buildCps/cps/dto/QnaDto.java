package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class QnaDto {
	
  private int qno;  
  private String id;  
  private String qsubject;  
  private String qcontent;  
  private String qpasswd;  
  private int qcount;  
  private Date qdate;  
  private Date qupdate;  
  private Date qdeldate;
  
  private int qreply;
  
  private int startRow;  
  private int endRow;
  
}
