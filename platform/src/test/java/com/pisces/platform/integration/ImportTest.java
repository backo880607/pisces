package com.pisces.platform.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.bean.SchemeGroup;
import com.pisces.integration.enums.IMPORT_TYPT;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.enums.SCHEME_TYPE;
import com.pisces.integration.service.DsLocaleFileService;
import com.pisces.integration.service.SchemeGroupService;
import com.pisces.integration.service.SchemeService;
import com.pisces.platform.PiscesBaseTest;
import com.pisces.user.bean.Department;

public class ImportTest extends PiscesBaseTest {
	@Autowired
	private SchemeGroupService schemeGroupService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private DsLocaleFileService localeFileService;
	
	private void importDepartment(SchemeGroup group) {
		Scheme scheme = schemeService.get((Scheme temp)->{
			return temp.getSchemeGroup() == group && temp.getInName().equals(Department.class.getSimpleName());
		});
		if (scheme == null) {
			scheme = schemeService.create();
			scheme.setSchemeGroup(group);
			scheme.setInName("Department");
			scheme.setOutName("Department");
			scheme.setImportType(IMPORT_TYPT.ReplaceImport);
			schemeService.insert(scheme);
		}
	}
	
	private void execute(DataSource dataSource) {
		SchemeGroup group = schemeGroupService.get((SchemeGroup temp)->{
			return temp.getType() == SCHEME_TYPE.Export && temp.getDataSource() == dataSource;
		});
		if (group == null) {
			group = schemeGroupService.create();
			group.setCode("导入数据");
			group.setType(SCHEME_TYPE.Import);
			group.setDataSource(dataSource);
			schemeGroupService.insert(group);
		}
		importDepartment(group);
		schemeGroupService.execute(group);
	}
	
	@Test
	public void csvTest() {
		DsLocaleFile csv = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.CSV;
		});
		if (csv == null) {
			csv = localeFileService.create();
			csv.setCode("本地csv");
			csv.setHost("D:\\Import");
			csv.setType(LOCALE_FILE_TYPE.CSV);
			localeFileService.insert(csv);
		}
		
		execute(csv);
	}
}
