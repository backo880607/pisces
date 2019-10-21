package com.pisces.integration.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.helper.AdapterRegister;
import com.pisces.integration.helper.DataConfig;

@Service
class DsExcelCsvService extends AdapterRegister<LOCALE_FILE_TYPE> {
	
	private CsvListReader reader;
	private List<String> lineData;
	private CsvListWriter writer;
	
	@Override
	public LOCALE_FILE_TYPE getType() {
		return LOCALE_FILE_TYPE.CSV;
	}
	
	@Override
	public void modifyConfig(DataConfig config) {
		config.setSepField(",");
		config.setSepEntity("\n");
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
			this.writer = new CsvListWriter(new OutputStreamWriter(new FileOutputStream(file), csv.getCharset()), CsvPreference.STANDARD_PREFERENCE);
		} else {
			this.reader = new CsvListReader(new InputStreamReader(new FileInputStream(file), csv.getCharset()), CsvPreference.STANDARD_PREFERENCE);
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
			this.writer = new CsvListWriter(new OutputStreamWriter(new FileOutputStream(
					getPath(csv, tableName)), "GBK"), CsvPreference.STANDARD_PREFERENCE);
			writer.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
		} else {
			this.reader = new CsvListReader(new InputStreamReader(new FileInputStream(
					getPath(csv, tableName)), csv.getCharset()), CsvPreference.STANDARD_PREFERENCE);
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
		if (this.writer != null) {
			try {
				this.writer.close();
				this.writer = null;
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
		this.lineData = this.reader.read();
		return lineData != null;
	}

	@Override
	public String getData(int index) throws Exception {
		return this.lineData.get(index);
	}

	@Override
	public void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
		this.lineData = new ArrayList<String>();
		for (FieldInfo field : fields) {
			this.lineData.add(field.getExternName());
		}
		
		this.writer.write(this.lineData);
	}
	
	@Override
	public void beforeWriteEntity(EntityObject entity) throws Exception {
	}
	
	@Override
	public void write(int index, String data) throws Exception {
		this.lineData.set(index, data);
	}

	@Override
	public void afterWriteEntity(EntityObject entity) throws Exception {
		this.writer.write(this.lineData);
	}

	@Override
	public void afterWriteTable(Scheme scheme) throws Exception {
		this.writer.flush();
	}
}
