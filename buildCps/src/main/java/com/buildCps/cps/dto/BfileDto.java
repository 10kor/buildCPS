package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class BfileDto {
	
  private int bno;
  private String id;
  private String bname;
  private byte[] bfile;
  private Date bdate;
  private String bsize;
  
  private int startRow;
  private int endRow;
  
}
