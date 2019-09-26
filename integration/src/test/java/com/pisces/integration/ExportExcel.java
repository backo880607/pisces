package com.pisces.integration;

import org.apache.poi.sl.usermodel.ObjectMetaData.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pisces.integration.bean.DsExcel;
import com.pisces.integration.service.DsExcelService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExportExcel {
	@Autowired
	private DsExcelService service;
	
	@Test
	public void execute() {
		if (service != null) {
			DsExcel excel = service.create();
			excel.getClass();
		}
	}
}
