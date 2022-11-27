package com.example.examplecollection.utils;

import lombok.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExcelUtils {

	@Getter
	@Setter
	public static class CreateDto {

		private String fileName = "document1"; //파일명
		private String sheetName = "sheet1"; //시트명
		private HashMap<String, List<Object>> dataMaps; //헤더, 로우 정보 (헤더, Row정보 리스트)
	}

	public static void downloadXls(HttpServletResponse response, CreateDto excelCreateDto) throws IOException {
		Workbook wb = new HSSFWorkbook();

		String sheetName = excelCreateDto.sheetName;
		if (sheetName.isEmpty()) {
			sheetName = "sheet1";
		}

		Sheet sheet = wb.createSheet(sheetName);

		Row row = null;
		Cell cell = null;

		int columnIndex = 0;
		for (Map.Entry<String, List<Object>> entry : excelCreateDto.dataMaps.entrySet()) {
			int rowNum = 0;
			String headerName = entry.getKey();
			//헤더
			row = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum++) : sheet.getRow(rowNum++); //행
			cell = row.getCell(columnIndex) == null ? row.createCell(columnIndex) : row.getCell(columnIndex); //열
			cell.setCellValue(headerName); //헤더지정

			//로우
			List<Object> rowList = entry.getValue();
			for (Object rowData : rowList) {
				row = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum++) : sheet.getRow(rowNum++); //행
				cell = row.getCell(columnIndex) == null ? row.createCell(columnIndex) : row.getCell(columnIndex); //열
				cell.setCellValue(String.valueOf(rowData));
			}

			columnIndex++;
		}

		// 컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(excelCreateDto.fileName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1") + ".xls");

		// Excel File Output
		wb.write(response.getOutputStream());
		wb.close();

	}

	public static void downloadXlsx(HttpServletResponse response, CreateDto excelCreateDto) throws IOException {
		Workbook wb = new XSSFWorkbook();

		String sheetName = excelCreateDto.sheetName;
		if (sheetName.isEmpty()) {
			sheetName = "sheet1";
		}

		Sheet sheet = wb.createSheet(sheetName);

		Row row = null;
		Cell cell = null;

		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		int columnIndex = 0;
		for (Map.Entry<String, List<Object>> entry : excelCreateDto.dataMaps.entrySet()) {
			int rowNum = 0;
			String headerName = entry.getKey();
			//헤더
			row = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum++) : sheet.getRow(rowNum++); //행
			cell = row.getCell(columnIndex) == null ? row.createCell(columnIndex) : row.getCell(columnIndex); //열
			cell.setCellValue(headerName); //헤더지정
			cell.setCellStyle(headerStyle);

			//로우
			List<Object> rowList = entry.getValue();
			for (Object rowData : rowList) {
				row = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum++) : sheet.getRow(rowNum++); //행
				cell = row.getCell(columnIndex) == null ? row.createCell(columnIndex) : row.getCell(columnIndex); //열
				cell.setCellValue(rowData == null ? "" : String.valueOf(rowData));
				cell.setCellStyle(cellStyle);
			}

			columnIndex++;
		}

		//셀너비 자동
		for (int i = 0; i < excelCreateDto.dataMaps.size(); i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i) + (short) 1024));
		}

		// 컨텐츠 타입과 파일명 지정
//        response.setContentType("ms-vnd/excel");
//        String fileName = "테스트 한글.xlsx";
//        String outputFileName = new String(fileName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileName + "\"");

		String title = URLEncoder.encode(excelCreateDto.fileName + ".xlsx", StandardCharsets.UTF_8);
		// 컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + title);

		// Excel File Output
		wb.write(response.getOutputStream());
		wb.close();

	}

	//엑셀 타입별 String Reutrn
	public static String getCellToString(Cell cell) {
		if (cell == null) {
			return "";
		}

		String returnValue = "";
//        cell.setCellType(CellType.STRING);
		CellType cellType = cell.getCellType();
		switch (cellType) {
			case _NONE:
			case BLANK:
			case ERROR:
				returnValue = "";
				break;
			case NUMERIC:
				returnValue = NumberToTextConverter.toText(cell.getNumericCellValue()).trim();
				break;
			case STRING:
				returnValue = cell.getStringCellValue().trim();
				break;
		}
		return returnValue;
	}

	//엑셀 타입별 Integer Reutrn
	public static Integer getCellToInt(Cell cell) {
		if (cell == null) {
			return null;
		}

		Integer returnValue = null;
		CellType cellType = cell.getCellType();
		switch (cellType) {
			case _NONE:
			case BLANK:
			case ERROR:
				returnValue = null;
				break;
			case NUMERIC:
				returnValue = (int) cell.getNumericCellValue();
				break;
			case STRING:
				try {
					returnValue = Integer.valueOf(cell.getStringCellValue().trim());
				} catch (NumberFormatException nfe) {
					returnValue = null;
				}
				break;
		}
		return returnValue;
	}

	//엑셀 타입별 Long Reutrn
	public static Long getCellToLong(Cell cell) {
		if (cell == null) {
			return null;
		}

		Long returnValue = null;
		CellType cellType = cell.getCellType();
		switch (cellType) {
			case _NONE:
			case BLANK:
			case ERROR:
				returnValue = null;
				break;
			case NUMERIC:
				returnValue = (long) cell.getNumericCellValue();
				break;
			case STRING:
				try {
					returnValue = Long.valueOf(cell.getStringCellValue().trim());
				} catch (NumberFormatException nfe) {
					returnValue = null;
				}
				break;
		}
		return returnValue;
	}
}
