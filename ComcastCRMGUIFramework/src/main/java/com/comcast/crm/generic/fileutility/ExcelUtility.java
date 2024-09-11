package com.comcast.crm.generic.fileutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	public String getDataFromExcel(String sheetName, int rowNum, int CelNum) throws Throwable {

		//FileInputStream fis = new FileInputStream("./testData/TestScriptData.xlsx");
		//Workbook wb = WorkbookFactory.create(fis);
		Workbook wb = WorkbookFactory.create(getJavaPhyicalFile("./testData/TestScriptData.xlsx"));
		String data = wb.getSheet(sheetName).getRow(rowNum).getCell(CelNum).getStringCellValue();
		wb.close();
		return data;
	}

	public int getRowCount(String sheetName) throws Throwable {
		FileInputStream fis = new FileInputStream("./testData/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		int rowNum = wb.getSheet(sheetName).getLastRowNum();
		wb.close();
		return rowNum;
	}
	
	public void setDataIntoExcel(String sheetName, int rowNum, int CelNum, String data) throws Throwable {
		FileInputStream fis = new FileInputStream("./testData/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		wb.getSheet(sheetName).getRow(rowNum).createCell(CelNum).getStringCellValue();
		
		FileOutputStream fos= new FileOutputStream("./testData/TestScriptData.xlsx");
		wb.write(fos);
		wb.close();
	}
	
	public FileInputStream getJavaPhyicalFile(String path) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(path);
		return fis;
	}
	
	
	

}
