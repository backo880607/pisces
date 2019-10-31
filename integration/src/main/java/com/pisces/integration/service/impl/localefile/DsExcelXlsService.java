package com.pisces.integration.service.impl.localefile;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.pisces.core.utils.StringUtils;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;

@Service
public class DsExcelXlsService extends DsExcelService<LOCALE_FILE_TYPE> {
	private static final String EXTENSION = "xls";
	
	@Override
	public LOCALE_FILE_TYPE getType() {
		return LOCALE_FILE_TYPE.XLS;
	}
	
	@Override
	protected String getPath(DsLocaleFile excel, String tableName) {
		String path = excel.getHost();
		if (StringUtils.getTail(path, ".").equalsIgnoreCase(EXTENSION)) {
			multiSheet = true;
			return path;
		}
		multiSheet = false;
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path + tableName + "." + EXTENSION;
	}

	@Override
	protected Workbook createBook() throws Exception {
		return new HSSFWorkbook();
	}

	@Override
	protected Workbook createBook(File file) throws Exception {
		return new HSSFWorkbook(new FileInputStream(file));
	}

}
