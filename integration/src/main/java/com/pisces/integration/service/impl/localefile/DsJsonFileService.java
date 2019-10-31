package com.pisces.integration.service.impl.localefile;

import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.service.impl.JsonDataSourceService;

public class DsJsonFileService extends JsonDataSourceService<LOCALE_FILE_TYPE> {

	@Override
	public LOCALE_FILE_TYPE getType() {
		return LOCALE_FILE_TYPE.JSON;
	}

}
