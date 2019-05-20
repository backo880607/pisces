package com.pisces.integration.service;

import com.pisces.core.service.EntityService;
import com.pisces.integration.bean.SchemeGroup;

public interface SchemeGroupService extends EntityService<SchemeGroup> {
	void execute(SchemeGroup schemeGroup);
}
