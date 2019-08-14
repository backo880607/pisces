package com.pisces.integration.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsExcelCsv;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.dao.DsExcelCsvDao;
import com.pisces.integration.service.DsExcelCsvService;

@Service
class DsExcelCsvServiceImpl extends DataSourceServiceImpl<DsExcelCsv, DsExcelCsvDao> implements DsExcelCsvService {
	
	private CSVReader reader;
	private String[] lineData;
	private CSVWriter writer;
	
	private String getPath(DsExcelCsv excel, String tableName) {
		String path = excel.getPath();
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path + tableName + ".csv";
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof DsExcelCsv)) {
			return false;
		}
		
		DsExcelCsv excel = (DsExcelCsv)dataSource;
		reader = new CSVReader(new InputStreamReader(new FileInputStream(
				getPath(excel, tableName)), excel.getCharset()));
		return reader != null;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName) throws Exception {
		if (!(dataSource instanceof DsExcelCsv)) {
			return false;
		}
		
		DsExcelCsv excel = (DsExcelCsv)dataSource;
		this.reader = new CSVReader(new InputStreamReader(new FileInputStream(
					getPath(excel, tableName)), excel.getCharset()));
		return this.reader != null;
	}

	@Override
	public void close() {
		if (this.reader != null) {
			try {
				this.reader.close();
				this.reader = null;
			} catch (IOException e) {
			}
		}
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
		return null;
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
