/*package com.pisces.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pisces.core.utils.AppUtils;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsExcel;
import com.pisces.integration.bean.DsExcelCsv;
import com.pisces.integration.bean.DsMySql;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SchemeGroup;
import com.pisces.integration.enums.SchemeType;
import com.pisces.integration.service.DsExcelCsvService;
import com.pisces.integration.service.DsExcelService;
import com.pisces.integration.service.DsMySqlService;
import com.pisces.integration.service.DsOracleService;
import com.pisces.integration.service.DsSQLServerService;
import com.pisces.integration.service.DsSQLiteService;
import com.pisces.integration.service.SchemeGroupService;
import com.pisces.integration.service.SchemeService;
import com.pisces.user.bean.Department;
import com.pisces.user.service.DepartmentService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("dev")
@Transactional
public class ExportTest {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
    protected TestRestTemplate restTemplate;
	
	@Autowired
	private DsExcelService excelService;
	
	@Autowired
	private DsExcelCsvService csvService;
	
	@Autowired
	private DsMySqlService mysqlService;
	
	@Autowired
	private DsSQLServerService sqlserverService;
	
	@Autowired
	private DsSQLiteService sqliteService;
	
	@Autowired
	private DsOracleService oracleService;
	
	@Autowired
	private SchemeGroupService schemeGroupService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Before
	public void login() {
		AppUtils.setContext(ctx);
		AppUtils.login("niuhaitao");
	}
	
	@After 
	public void finish() { 
		AppUtils.syncData(); 
	}
	
	private SchemeGroup obtainGroup() {
		SchemeGroup group = schemeGroupService.get((SchemeGroup temp)->{
			return temp.getType() == SchemeType.Export;
		});
		if (group == null) {
			group = schemeGroupService.create();
			group.setCode("导出数据");
			group.setType(SchemeType.Export);
			schemeGroupService.insert(group);
		}
		return group;
	}
	
	private void exportDepartment(DataSource dataSource, SchemeGroup group) {
		if (departmentService.get() == null) {
			for (int i = 0; i < 10; ++i) {
				Department department = departmentService.create();
				department.setCode("Department" + i);
				departmentService.insert(department);
			}
		}
		
		Scheme scheme = schemeService.get((Scheme temp)->{
			return temp.getDataSource() == dataSource && temp.getSchemeGroup() == group && temp.getInName().equals(Department.class.getSimpleName());
		});
		if (scheme == null) {
			scheme = schemeService.create();
			scheme.setDataSource(dataSource);
			scheme.setSchemeGroup(group);
			scheme.setInName("Department");
			scheme.setOutName("部门");
			schemeService.insert(scheme);
		}
	}
	
	@Test
	public void excelTest() {
		SchemeGroup group = obtainGroup();
		
		DsExcel excel = excelService.get();
		if (excel == null) {
			excel = excelService.create();
			excel.setCode("本地excel");
			excel.setHost("D:\\Export");
			excelService.insert(excel);
		}
		exportDepartment(excel, group);
		
		schemeGroupService.execute(group);
	}
	
	public void csvTest() {
		SchemeGroup group = obtainGroup();
		
		DsExcelCsv csv = csvService.get();
		if (csv == null) {
			csv = csvService.create();
			csv.setCode("本地excel");
			csv.setHost("D:\\Export");
			csvService.insert(csv);
		}
		exportDepartment(csv, group);
		
		schemeGroupService.execute(group);
	}
	
	public void mysqlTest() {
		SchemeGroup group = obtainGroup();
		
		DsMySql mySql = mysqlService.get();
		if (mySql == null) {
			mySql = mysqlService.create();
			mySql.setHost("127.0.0.1");
			mySql.setPort(3306);
			mySql.setDataBase("estsh");
			mySql.setUsername("niuhaitao");
			mySql.setPassword("880607");
			mysqlService.insert(mySql);
		}
		exportDepartment(mySql, group);
		
		schemeGroupService.execute(group);
	}
}*/
