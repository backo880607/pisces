package com.pisces.platform.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.DsSql;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SchemeGroup;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.enums.SCHEME_TYPE;
import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.integration.service.DsLocaleFileService;
import com.pisces.integration.service.DsSqlService;
import com.pisces.integration.service.SchemeGroupService;
import com.pisces.integration.service.SchemeService;
import com.pisces.platform.PiscesBaseTest;
import com.pisces.user.bean.Department;
import com.pisces.user.service.DepartmentService;

public class ExportTest extends PiscesBaseTest {
	
	@Autowired
	private DsLocaleFileService localeFileService;
	
	@Autowired
	private DsSqlService sqlService;
	
	@Autowired
	private SchemeGroupService schemeGroupService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	private void exportDepartment(SchemeGroup group) {
		if (departmentService.get() == null) {
			for (int i = 0; i < 10; ++i) {
				Department department = departmentService.create();
				department.setCode("Department" + i);
				departmentService.insert(department);
			}
		}
		
		Scheme scheme = schemeService.get((Scheme temp)->{
			return temp.getSchemeGroup() == group && temp.getInName().equals(Department.class.getSimpleName());
		});
		if (scheme == null) {
			scheme = schemeService.create();
			scheme.setSchemeGroup(group);
			scheme.setInName("Department");
			scheme.setOutName("Department");
			schemeService.insert(scheme);
		}
	}
	
	private void execute(DataSource dataSource) {
		SchemeGroup group = schemeGroupService.get((SchemeGroup temp)->{
			return temp.getType() == SCHEME_TYPE.Export && temp.getDataSource() == dataSource;
		});
		if (group == null) {
			group = schemeGroupService.create();
			group.setCode("导出数据");
			group.setType(SCHEME_TYPE.Export);
			group.setDataSource(dataSource);
			schemeGroupService.insert(group);
		}
		exportDepartment(group);
		schemeGroupService.execute(group);
	}
	
	// @Test
	public void excelTest() {
		DsLocaleFile excel = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.XLS;
		});
		if (excel == null) {
			excel = localeFileService.create();
			excel.setCode("本地excel");
			excel.setHost("D:\\Export\\部门.xlsx");
			excel.setType(LOCALE_FILE_TYPE.XLSX);
			localeFileService.insert(excel);
		}
		execute(excel);
	}
	
	// @Test
	public void csvTest() {
		DsLocaleFile csv = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.CSV;
		});
		if (csv == null) {
			csv = localeFileService.create();
			csv.setCode("本地csv");
			csv.setHost("D:\\Export");
			csv.setType(LOCALE_FILE_TYPE.CSV);
			localeFileService.insert(csv);
		}
		
		execute(csv);
	}
	
	// @Test
	public void jsonFileTest() {
		DsLocaleFile jsonFile = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.JSON;
		});
		if (jsonFile == null) {
			jsonFile = localeFileService.create();
			jsonFile.setCode("本地Json文件");
			jsonFile.setHost("D:\\Export");
			jsonFile.setType(LOCALE_FILE_TYPE.JSON);
			localeFileService.insert(jsonFile);
		}
		execute(jsonFile);
	}
	
	// @Test
	public void xmlFileTest() {
		DsLocaleFile xmlFile = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.XML;
		});
		if (xmlFile == null) {
			xmlFile = localeFileService.create();
			xmlFile.setCode("本地xml文件");
			xmlFile.setHost("D:\\Export");
			xmlFile.setType(LOCALE_FILE_TYPE.XML);
			localeFileService.insert(xmlFile);
		}
		
		execute(xmlFile);
	}
	
	// @Test
	public void mysqlTest() {
		DsSql mySql = sqlService.get((DsSql temp) -> {
			return temp.getType() == SQL_TYPE.MYSQL;
		});
		if (mySql == null) {
			mySql = sqlService.create();
			mySql.setHost("127.0.0.1");
			mySql.setPort(3306);
			mySql.setDataBase("estsh");
			mySql.setUsername("niuhaitao");
			mySql.setPassword("880607");
			sqlService.insert(mySql);
		}
		
		execute(mySql);
	}
	
	@Test
	public void sqlServerTest() {
		DsSql sqlServer = sqlService.get((DsSql temp) -> {
			return temp.getType() == SQL_TYPE.SQLSERVER;
		});
		if (sqlServer == null) {
			sqlServer = sqlService.create();
			sqlServer.setHost("127.0.0.1");
			sqlServer.setPort(1433);
			sqlServer.setDataBase("estsh");
			sqlServer.setUsername("niuhaitao");
			sqlServer.setPassword("880607");
			sqlServer.setType(SQL_TYPE.SQLSERVER);
			sqlService.insert(sqlServer);
		}
		execute(sqlServer);
	}
	
	// @Test
	public void sqliteTest() {
		DsSql sqlite = sqlService.get((DsSql temp) -> {
			return temp.getType() == SQL_TYPE.SQLITE;
		});
		if (sqlite == null) {
			sqlite = sqlService.create();
			sqlite.setHost("127.0.0.1");
			sqlite.setPort(1433);
			sqlite.setDataBase("estsh");
			sqlite.setUsername("niuhaitao");
			sqlite.setPassword("880607");
			sqlite.setType(SQL_TYPE.SQLITE);
			sqlService.insert(sqlite);
		}
		execute(sqlite);
	}
	
	// @Test
	public void oracleTest() {
		DsSql oracle = sqlService.get((DsSql temp) -> {
			return temp.getType() == SQL_TYPE.ORACLE;
		});
		if (oracle == null) {
			oracle = sqlService.create();
			oracle.setHost("127.0.0.1");
			oracle.setPort(1433);
			oracle.setDataBase("estsh");
			oracle.setUsername("niuhaitao");
			oracle.setPassword("880607");
			oracle.setType(SQL_TYPE.ORACLE);
			sqlService.insert(oracle);
		}
		execute(oracle);
	}
}
