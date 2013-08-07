package ynugis.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public  class GisTable implements IGisTable {

	protected HSSFWorkbook wb;
	protected FileOutputStream fileOut;
	protected HSSFSheet sheet;
	protected HSSFCellStyle centerStyle;
	protected HSSFFont boldFont;
	
	public void initial(FileOutputStream fos) throws Exception {
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		centerStyle= wb.createCellStyle();
		centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		boldFont=wb.createFont();
		boldFont.setBoldweight((short) 1000);
	}

	public void writeCell(String strCellValue, int row, int column) {
		HSSFRow row1 = sheet.createRow((short) row);
		HSSFCell cell = row1.createCell((short) column);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(strCellValue);
		cell.setCellStyle(centerStyle);

	}

	public void writeCell(String strCellValue, int row, int column,
			HSSFFont font) {
		HSSFRow row1 = sheet.createRow((short) row);
		HSSFCell cell = row1.createCell((short) column);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(strCellValue);
		int height=200;
	    font.setFontHeight((short)height);
		
		cell.getCellStyle().setFont(font);
		
		
		

	}
	public void writeCell(double dbCellValue, int row, int column)
			 {
		HSSFRow row1 = sheet.createRow((short) row);
		HSSFCell cell = row1.createCell((short) column);
		cell.setCellValue(dbCellValue);}

	public void writerow(String[] value, int rowIdx) {
		writerow(value,rowIdx,0);

	}

	public void writerow(String[] value, int rowIdx, int startIdx) {
		int length=value.length;
		for(int i=0;i<length;i++)
		{
			writeCell(value[i],rowIdx,i+startIdx);
		}

	}

	public void save() throws IOException {
		wb.write(fileOut);

	}

	public GisTable(String filePath) throws Exception {
		super();
		fileOut = new FileOutputStream(filePath);
		initial(fileOut);
	}

	
}
