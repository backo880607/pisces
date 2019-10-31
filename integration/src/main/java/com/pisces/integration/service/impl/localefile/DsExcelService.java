package com.pisces.integration.service.impl.localefile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pisces.core.entity.EntityObject;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.helper.AdapterRegister;
import com.pisces.integration.helper.DataConfig;

public abstract class DsExcelService<T extends Enum<T>> extends AdapterRegister<T> {
	private File file;
	private Workbook book;
	private Sheet sheet;
	private Row row;
	private int rowNumber;
	protected boolean multiSheet;
	
	protected abstract String getPath(DsLocaleFile excel, String tableName);
	protected abstract Workbook createBook() throws Exception;
	protected abstract Workbook createBook(File file) throws Exception;
	
	@Override
	public void modifyConfig(DataConfig config) {
	}

	@Override
	public boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception {
		if (!(dataSource instanceof DsLocaleFile)) {
			return false;
		}
		
		boolean bNew = false;
		DsLocaleFile excel = (DsLocaleFile)dataSource;
		this.file = new File(getPath(excel, tableName));
		if (!this.file.exists()) {
			if (!export) {
				return false;
			}
			
			bNew = true;
			this.book = createBook();
		} else {
			this.book = createBook(this.file);
		}
		this.sheet = this.multiSheet ? this.book.getSheet(tableName) : this.book.getNumberOfSheets() > 0 ? this.book.getSheetAt(0) : null;
		if (this.sheet == null) {
			if (!export) {
				return false;
			}
			bNew = true;
			this.sheet = this.multiSheet ? this.book.createSheet(tableName) : this.book.createSheet();
		}
		if (bNew) {
			try (FileOutputStream out = new FileOutputStream(this.file)) {
				this.book.write(out);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return true;
	}

	@Override
	public boolean open(DataSource dataSource, String tableName, boolean export) throws Exception {
		if (!(dataSource instanceof DsLocaleFile)) {
			return false;
		}
		
		DsLocaleFile excel = (DsLocaleFile)dataSource;
		this.file = new File(getPath(excel, tableName));
		this.book = createBook(this.file);
		this.sheet = this.multiSheet ? this.book.getSheet(tableName) : this.book.getSheetAt(0);
		if (this.sheet == null) {
			return false;
		}
		
		this.rowNumber = this.sheet.getFirstRowNum();
		return true;
	}

	@Override
	public void close() {
		try {
			if (this.book != null) {
				this.book.close();
				this.book = null;
			}
		} catch (IOException e) {
		}
		
		this.sheet = null;
		this.row = null;
	}

	@Override
	public boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields)
			throws Exception {
		step();
		return true;
	}
	
	@Override
	public Collection<FieldInfo> getFields() throws Exception {
		Collection<FieldInfo> result = new ArrayList<FieldInfo>();
		this.row = this.sheet.getRow(this.rowNumber);
		this.row.forEach((Cell cell) -> {
			FieldInfo field = new FieldInfo();
			field.setName(cell.getStringCellValue());
			field.setExternName(field.getName());
			result.add(field);
		});
		return result;
	}

	@Override
	public boolean step() throws Exception {
		if (this.sheet == null || this.rowNumber > this.sheet.getLastRowNum()) {
			return false;
		}
		
		this.row = this.sheet.getRow(this.rowNumber);
		++this.rowNumber;
		return this.row != null;
	}

	@Override
	public String getData(int index) throws Exception {
		return this.row.getCell(index).getStringCellValue();
	}
	
	@Override
	public void beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception {
		this.row = this.sheet.createRow(this.rowNumber++);
		int index = 0;
		for (FieldInfo field : fields) {
			this.row.createCell(index++).setCellValue(field.getExternName());
		}
	}
	
	@Override
	public void beforeWriteEntity(EntityObject entity) throws Exception {
		this.row = this.sheet.createRow(this.rowNumber++);
	}

	@Override
	public void write(int index, String data) throws Exception {
		this.row.createCell(index).setCellValue(data);
	}

	@Override
	public void afterWriteEntity(EntityObject entity) throws Exception {
	}

	@Override
	public void afterWriteTable(Scheme scheme) throws Exception {
		try (FileOutputStream out = new FileOutputStream(this.file)) {
			this.book.write(out);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
