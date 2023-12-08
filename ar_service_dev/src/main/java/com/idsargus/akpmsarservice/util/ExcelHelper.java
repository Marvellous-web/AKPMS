package com.idsargus.akpmsarservice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;

public class ExcelHelper {
	
	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Patient Acc No", "Patient Name", "DOS", "CPT", "Source", "Status code", "AR Database",
			  "Insurance", "Teams", "Doctor", "On" };
	  static String SHEET = "Tutorials";

	  public static ByteArrayInputStream loadExcelRecords(List<ArProductivityEntity> productList) {

	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);

	      // Header
	      Row headerRow = sheet.createRow(0);

	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	      int rowIdx = 1;
	      for (ArProductivityEntity product : productList) {
	        Row row = sheet.createRow(rowIdx++);

	        row.createCell(0).setCellValue(product.getPatientAccountNumber());
	        row.createCell(1).setCellValue(product.getPatientName());
	        row.createCell(1).setCellValue(product.getDos());
	        row.createCell(3).setCellValue(product.getCpt());
	        row.createCell(4).setCellValue(product.getSource());
	        row.createCell(5).setCellValue(product.getStatusCode());
	        row.createCell(6).setCellValue(product.getDatabase().getName());
	        row.createCell(7).setCellValue(product.getInsurance().getName());
//	        if(!product.getTeam().equals(null)) {
//	        row.createCell(8).setCellValue(product.getTeam().getName());
//	        }
	        row.createCell(9).setCellValue(product.getDoctor().getName());
	        row.createCell(10).setCellValue(product.getCreatedOn());
	        
	      }

	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }

}
