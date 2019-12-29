package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.SchemeGroup;
import com.pisces.platform.integration.dao.SchemeGroupDao;
import com.pisces.platform.integration.helper.ExportHelper;
import com.pisces.platform.integration.helper.ImportHelper;
import com.pisces.platform.integration.service.SchemeGroupService;
import org.springframework.stereotype.Service;

@Service
class SchemeGroupServiceImpl extends EntityServiceImpl<SchemeGroup, SchemeGroupDao> implements SchemeGroupService {

	@Override
	public void execute(SchemeGroup schemeGroup) {
		if (schemeGroup == null || schemeGroup.getType() == null) {
			return;
		}
		switch (schemeGroup.getType()) {
		case Import:
			executeImport(schemeGroup);
			break;
		case Export:
			executeExport(schemeGroup);
			break;
		default:
			break;
		}
	}
	
	private void executeImport(SchemeGroup schemeGroup) {
		ImportHelper helper = new ImportHelper();
		helper.execute(schemeGroup);
	}
	
	private void executeExport(SchemeGroup schemeGroup) {
		ExportHelper helper = new ExportHelper();
		helper.execute(schemeGroup);
	}
}
