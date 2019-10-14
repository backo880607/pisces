package com.pisces.integration.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.helper.DataConfig;
import com.pisces.integration.helper.AdapterRegister;

@Service
class DsExcelCsvService extends AdapterRegister<LOCALE_FILE_TYPE> {
	
	private CSVReader reader;
	private String[] lineData;
	private CSVWriter writer;
	
	@Override
	public LOCALE_FILE_TYPE getType() {
		return LOCALE_FILE_TYPE.CSV;
	}
	
	@Override
	public DataConfig getDataConfig() {
		DataConfig config = new DataConfig();
		config.setSepField(",");
		config.setSepEntity("\n");
		return config;
	}
	
	private String getPath(DsLocaleFile excel, String tableName) {
		String path = excel.getHost();
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path + tableName + ".csv";
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception {
		if (!(dataSource instanceof DsLocaleFile)) {
			return false;
		}
		
		DsLocaleFile csv = (DsLocaleFile)dataSource;
		File file = new File(getPath(csv, tableName));
		if (!file.exists()) {
			if (!export) {
				return false;
			}
			
			file.createNewFile();
		}
		if (export) {
			this.writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file), csv.getCharset()));
		} else {
			this.reader = new CSVReader(new InputStreamReader(new FileInputStream(file), csv.getCharset()));
		}
		return this.reader != null || this.writer != null;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName, boolean export) throws Exception {
		if (!(dataSource instanceof DsLocaleFile)) {
			return false;
		}
		
		DsLocaleFile csv = (DsLocaleFile)dataSource;
		if (export) {
			this.writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(
					getPath(csv, tableName)), csv.getCharset()));
		} else {
			this.reader = new CSVReader(new InputStreamReader(new FileInputStream(
					getPath(csv, tableName)), csv.getCharset()));
		}
		return reader != null || writer != null;
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
	public void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
		this.lineData = new String[fields.size()];
		int index = 0;
		for (FieldInfo field : fields) {
			this.lineData[index++] = field.getExternName();
		}
		
		this.writer.writeNext(lineData);
	}
	
	@Override
	public void beforeWriteEntity(EntityObject entity) throws Exception {
	}
	
	@Override
	public void write(int index, String data) throws Exception {
		this.lineData[index] = data;
	}

	@Override
	public void afterWriteEntity(EntityObject entity) throws Exception {
		this.writer.writeNext(lineData);
	}

	@Override
	public void afterWriteTable(Scheme scheme) throws Exception {
		this.writer.flush();
	}
	
	@Override
	public String obtainValue(EntityObject entity, Property property) {
		return null;
	}
}
