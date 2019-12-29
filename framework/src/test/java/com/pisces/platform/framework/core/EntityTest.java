package com.pisces.platform.framework.core;

import com.pisces.platform.core.entity.EntityMapper;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.framework.PiscesBaseTest;
import com.pisces.platform.user.bean.Account;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
			Account temp = mapper.readValue(jsonData, Account.class);
			Assert.assertEquals(nht, temp);
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
