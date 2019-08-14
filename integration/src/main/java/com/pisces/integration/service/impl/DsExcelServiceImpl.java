package com.pisces.integration.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.pisces.core.utils.StringUtils;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsExcel;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.dao.DsExcelDao;
import com.pisces.integration.service.DsExcelService;

@Service
public class DsExcelServiceImpl extends DataSourceServiceImpl<DsExcel, DsExcelDao> implements DsExcelService {
	private Workbook book;
	private Sheet sheet;
	private Row row;
	private int readNum;
	
	private String getPath(DsExcel excel, String tableName) {
		String path = excel.getPath();
		if (StringUtils.getTail(path, ".").equalsIgnoreCase(excel.getExtension())) {
			return path;
		}
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path + tableName + "." + excel.getExtension();
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		return open(dataSource, tableName);
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof DsExcel)) {
			return false;
		}
		
		DsExcel excel = (DsExcel)dataSource;
		File file = new File(getPath(excel, tableName));
		if (excel.getExtension().equalsIgnoreCase("xlsx")) {
			book = new XSSFWorkbook(new FileInputStream(file));
		} else {
			book = new HSSFWorkbook(new FileInputStream(file));
		}
		sheet = book.getSheet(tableName);
		if (sheet == null) {
			if (file.getName().startsWith(tableName)) {
				sheet = book.getSheetAt(0);
			} else {
				return false;
			}
		}
		
		readNum = sheet.getFirstRowNum();
		return true;
	}

	@Override
	public void close() {
		try {
			if (book != null) {
				book.close();
				book = null;
			}
		} catch (IOException e) {
		}
		
		sheet = null;
		row = null;
	}

	@Override
	public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields)
			throws Exception {
		// skip the header
		step();
		return true;
	}
	
	@Override
	public Collection<FieldInfo> getFields() throws Exception {
		Collection<FieldInfo> result = new ArrayList<FieldInfo>();
		row = sheet.getRow(readNum);
		row.forEach((Cell cell) -> {
			FieldInfo field = new FieldInfo();
			field.setName(cell.getStringCellValue());
			field.setExternName(field.getName());
			result.add(field);
		});
		return result;
	}

	@Override
	public boolean step() throws Exception {
		if (sheet == null || readNum > sheet.getLastRowNum()) {
			return false;
		}
		
		row = sheet.getRow(readNum);
		++readNum;
		return row != null;
	}

	@Override
	public String getData(int index) throws Exception {
		return row.getCell(index).getStringCellValue();
	}

	@Override
	public String getData(Field field) throws Exception {
		return null;
	}

	@Override
	public void write(Field field, String data) throws Exception {
		
	}

}
