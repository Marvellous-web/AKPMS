package com.idsargus.akpmsadminservice.batchutilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JobProgressMessage  {

	private String status;
	private double  writeCount;
	private double percentageComplete;
	private String fileName;
}
