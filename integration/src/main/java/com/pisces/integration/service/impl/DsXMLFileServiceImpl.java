package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsXMLFile;
import com.pisces.integration.service.DsXMLFileService;
import com.pisces.integration.dao.DsXMLFileDao;

@Service
public class DsXMLFileServiceImpl extends XMLDataSourceServiceImpl<DsXMLFile, DsXMLFileDao> implements DsXMLFileService {

}
