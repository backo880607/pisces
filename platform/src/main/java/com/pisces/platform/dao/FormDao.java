package com.pisces.platform.dao;

import org.springframework.stereotype.Component;

import com.pisces.nosql.dao.RedisDao;
import com.pisces.platform.bean.Form;

@Component
public class FormDao extends RedisDao<Form> {

}
