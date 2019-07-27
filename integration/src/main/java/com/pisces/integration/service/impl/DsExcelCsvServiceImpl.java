package com.pisces.integration.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsExcelCsv;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.dao.DsExcelCsvDao;
import com.pisces.integration.service.DsExcelCsvService;

@Service
class DsExcelCsvServiceImpl extends DataSourceServiceImpl<DsExcelCsv, DsExcelCsvDao> implements DsExcelCsvService {
	
	private CSVReader reader;
	private String[] lineData;
	
	private String getPath(DsExcelCsv excel, String tableName) {
		String path = excel.getPath();
		if (!path.endsWith("/")) {
			path += "/";
		}
		return path + tableName + ".csv";
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof DsExcelCsv)) {
			return false;
		}
		
		DsExcelCsv excel = (DsExcelCsv)dataSource;
		try (CSVReader temp = new CSVReader(new InputStreamReader(new FileInputStream(
				getPath(excel, tableName)), excel.getCharset()))) {
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof DsExcelCsv)) {
			return false;
		}
		
		DsExcelCsv excel = (DsExcelCsv)dataSource;
		try {
			this.reader = new CSVReader(new InputStreamReader(new FileInputStream(
					getPath(excel, tableName)), excel.getCharset()));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			close();
			throw e;
		}
		return true;
	}

	@Override
	public void close() {
		if (this.reader == null) {
			return;
		}
		
		try {
			this.reader.close();
		} catch (IOException e) {
		}
		this.reader = null;
	}
	
	@Override
	public boolean checkTableStructure(DataSource dataSource, String tableName, Collection<FieldInfo> fields)
			throws Exception {
		return false;
	}
	
	@Override
	public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields)
			throws Exception {
		return false;
	}

	@Override
	public boolean step() throws Exception {
		this.lineData = this.reader.readNext();
		return lineData != null;
	}

	@Override
	public String getData(int index) throws Exception {
		return this.lineData[index];
	}

	@Override
	public String getData(Field field) throws Exception {
		return null;
	}

	@Override
	public void write(Field field, String data) throws Exception {
		
	}
}
