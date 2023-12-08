package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QAWorksheetStaffJsonData {




	private String userName;

	private String count;
	//This should be in QAWorksheet but there is no option so declaring ar status code in this class
	private String arStatusCode;
	
	public QAWorksheetStaffJsonData() {}
	
	public QAWorksheetStaffJsonData( String userName, String arStatusCode, String count) {

		this.userName = userName;
		this.arStatusCode = arStatusCode;
		this.count = count;
	}

	
}
