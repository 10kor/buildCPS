package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class LogDto {
	
  private String id;
  private String user_name;
  private String host_name;
  private String ip_address;
  private Date registration;
  private int status;
  
  private boolean hasPayment;
  private int pcCount;
  
  private int startRow;
  private int endRow;
  
}
