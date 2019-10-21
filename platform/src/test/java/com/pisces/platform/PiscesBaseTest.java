package com.pisces.platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pisces.core.entity.EntityMapper;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;
import com.pisces.user.bean.Account;
import com.pisces.user.bean.Department;
import com.pisces.user.bean.Role;
import com.pisces.user.service.AccountService;
import com.pisces.user.service.DepartmentService;
import com.pisces.user.service.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("dev")
@Transactional
public class PiscesBaseTest {
	@Autowired
	private ApplicationContext ctx;
	@Autowired
    protected TestRestTemplate restTemplate;
	private MockMvc mvc;
	private boolean initData = false;
	
	@Autowired
	protected DepartmentService departmentService;
	
	@Autowired
	protected AccountService accountService;
	
	@Autowired
	protected RoleService roleService;
	
	protected EntityMapper mapper;
	
	@Before
	public void login() {
		AppUtils.setContext(ctx);
		AppUtils.login("niuhaitao");
		mapper = EntityUtils.createEntityMapper();
		mvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext)ctx).build();
		initData();
	}
	
	private void runRequest(MockHttpServletRequestBuilder request, Map<String, Object> args, String content) {
		if (args != null) {
			for (Entry<String, Object> entry : args.entrySet()) {
				request.param(entry.getKey(), entry.getValue().toString());
			}
		}

		try {
			request.contentType(MediaType.APPLICATION_JSON_UTF8);
			request.accept(MediaType.APPLICATION_JSON_UTF8);
			if (!StringUtils.isEmpty(content)) {
				request.content(content);
			}
			
			MvcResult result = mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			MockHttpServletResponse response = result.getResponse();
			if (response.getOutputStream() != null) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void httpGet(String uri) {
		httpGet(uri, null);
	}
	
	public void httpGet(String uri, Map<String, Object> args) {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		runRequest(request, args, null);
	}
	
	public void httpPost(String uri) {
		httpPost(uri, new HashMap<String, Object>());
	}
	
	public void httpPost(String uri, EntityObject entity) {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
		try {
			runRequest(request, null, mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public void httpPost(String uri, Map<String, Object> args) {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri);
		runRequest(request, args, null);
	}
	
	private void initData() {
		if (initData) {
			return;
		}
		
		Department department = departmentService.create();
		department.setCode("部门");
		departmentService.insert(department);
		
		Account nht = accountService.create();
		nht.setUsername("niuhaitao");
		nht.setEmail("backo@163.com");
		nht.setDepartment(department);
		accountService.insert(nht);
		
		Account chen = accountService.create();
		chen.setUsername("chenting");
		chen.setEmail("backo@163.com");
		chen.setDepartment(department);
		accountService.insert(chen);
		
		Role manager = roleService.create();
		manager.setCode("管理员");
		chen.set(Account.roles, manager);
		roleService.insert(manager);
		
		Role operation = roleService.create();
		operation.setCode("操作员");
		nht.set(Account.roles, operation);
		roleService.insert(operation);
		
		initData = true;
	}
}
