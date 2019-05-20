package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.SchemeGroup;
import com.pisces.integration.dao.SchemeGroupDao;
import com.pisces.integration.helper.ExportHelper;
import com.pisces.integration.helper.ImportHelper;
import com.pisces.integration.service.SchemeGroupService;

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
		helper.execute(schemeGroup.getSchemes());
	}
	
	private void executeExport(SchemeGroup schemeGroup) {
		ExportHelper helper = new ExportHelper();
		helper.execute(schemeGroup.getSchemes());
	}
}
