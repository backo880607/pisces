package com.pisces.platform.core;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pisces.core.entity.EntityMapper;
import com.pisces.core.entity.Property;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.EntityUtils;
import com.pisces.platform.PiscesBaseTest;
import com.pisces.user.bean.Account;

public class EntityTest extends PiscesBaseTest {
	@Autowired
	private PropertyService propertyService;
	
	@Test
	public void propertyValueTest() {
		Account nht = accountService.get((Account temp) -> {
			return temp.getUsername().equals("niuhaitao");
		});
		String jsonData;
		try {
			jsonData = mapper.writeValueAsString(nht);
			mapper.readValue(jsonData, Account.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		EntityMapper mapper = EntityUtils.createEntityMapper();
		Property booleanPro = propertyService.get(Account.class, "sex");
		String strBoolean = mapper.getTextValue(nht, booleanPro);
		Assert.assertEquals("true", strBoolean);
		
		Property longPro = propertyService.get(Account.class, "id");
		String strLong = mapper.getTextValue(nht, longPro);
		Assert.assertEquals(nht.getId().toString(), strLong);
		
		Property datePro = propertyService.get(Account.class, "createDate");
		String strDate = mapper.getTextValue(nht, datePro);
		
		Property entityPro = propertyService.get(Account.class, "department");
		String strEntity = mapper.getTextValue(nht, entityPro);
		
		Property strPro = propertyService.get(Account.class, "username");
		String strValue = mapper.getTextValue(nht, strPro);
		Assert.assertEquals("niuhaitao", strValue);
	}
}
