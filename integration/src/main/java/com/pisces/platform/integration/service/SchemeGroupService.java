package com.pisces.platform.integration.service;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.integration.bean.SchemeGroup;

public interface SchemeGroupService extends EntityService<SchemeGroup> {
    void execute(SchemeGroup schemeGroup);
}
