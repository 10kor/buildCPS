package com.buildCps.cps.dto;

import java.util.Date;
import lombok.Data;

@Data
public class MemberDto {
	
  private String id;
  private String passwd;  
  private String company;  
  private String name;  
  private String mail;  
  private String phone;  
  private Date join_date;  
  private Date drop_date;
  
  private boolean hasPayment;  
  private int pcCount;
  
  private int startRow;  
  private int endRow;
  
}
