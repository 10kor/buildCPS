package com.buildCps.cps.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PayDto {

	private String id;
	private int pc_amount;
	private Date startdate;
	private Date enddate;
	
	private int startRow;
	private int endRow;
	
}
