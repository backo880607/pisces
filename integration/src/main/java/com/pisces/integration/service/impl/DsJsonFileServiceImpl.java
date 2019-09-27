package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsJsonFile;
import com.pisces.integration.dao.DsJsonFileDao;
import com.pisces.integration.service.DsJsonFileService;

@Service
public class DsJsonFileServiceImpl extends JsonDataSourceServiceImpl<DsJsonFile, DsJsonFileDao> implements DsJsonFileService {

}
