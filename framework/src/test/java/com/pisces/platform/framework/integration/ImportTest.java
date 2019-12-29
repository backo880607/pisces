package com.pisces.platform.framework.integration;

import com.pisces.platform.framework.PiscesBaseTest;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.DsLocaleFile;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.bean.SchemeGroup;
import com.pisces.platform.integration.enums.IMPORT_TYPT;
import com.pisces.platform.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.platform.integration.enums.SCHEME_TYPE;
import com.pisces.platform.integration.service.DsLocaleFileService;
import com.pisces.platform.integration.service.SchemeGroupService;
import com.pisces.platform.integration.service.SchemeService;
import com.pisces.platform.user.bean.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
	public void piscesTest() {
		DsLocaleFile pisces = localeFileService.get((DsLocaleFile temp) -> {
			return temp.getType() == LOCALE_FILE_TYPE.PISCES;
		});
		if (pisces == null) {
			pisces = localeFileService.create();
			pisces.setCode("本地pisces");
			pisces.setHost("D:\\Export\\部门");
			pisces.setType(LOCALE_FILE_TYPE.PISCES);
			localeFileService.insert(pisces);
		}

		execute(pisces);
	}
	
	// @Test
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
