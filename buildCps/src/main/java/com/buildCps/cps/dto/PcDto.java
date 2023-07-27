package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PcDto {
	
  private String id;  
  private String host_name;  
  private Date registration;  
  private int status;  
  
  private int startRow;  
  private int endRow;
  
}
